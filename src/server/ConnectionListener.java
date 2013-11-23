package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import common.Mensaje;
import common.UserMetaData;

import dataTier.BanInfo;
import dataTier.DataAccess;

public class ConnectionListener extends Thread {

	HashMap<String, ClientHandler> handlerList;
	ClientHandler clientHandler;
	String clientIP;
	UserMetaData userMeta;
	Socket client;
	int serverPort;
	DataAccess dataAccess;
	ObjectInputStream in;
	ObjectOutputStream out;

	// Constructores	
	public ConnectionListener(Socket client, String clientIP, ObjectInputStream in, ObjectOutputStream out) throws IOException {
		dataAccess = DataAccess.getInstance();
		handlerList = ChatServer.getInstance().getHandlerList();
		this.client = client;
		this.clientIP = clientIP;
		this.in = in;
		this.out = out;
	}

	// Metodos 
	public void run(){
		int msgID;
		Mensaje msg;
		boolean loggedIn = false;
		
		try{
			while(client.isConnected() && !loggedIn){
				/* Espero a recibir algun mensaje del guest */
				msg = (Mensaje) in.readObject();
				msgID = msg.getId();
				
				if(msgID == Mensaje.ALTA_USUARIO){
					this.altaUsuario((UserMetaData)msg.getCuerpo());
				} else if (msgID == Mensaje.VERIFICAR_USUARIO) {
					this.verificarUsuario((String)msg.getCuerpo());
				} else if(msgID == Mensaje.LOG_IN){
					//ejecuto validacion de login + loggedin = true + lanzo Client Handler
					userMeta = (UserMetaData) msg.getCuerpo();
					BanInfo bInfo = dataAccess.checkBan(userMeta.getUser());
					Mensaje mensajeLogIn;
					/* Check user y pw contra DB */
					if (!dataAccess.checkUser(userMeta)) {
						mensajeLogIn = new Mensaje(Mensaje.DENIED, "Usuario o contrasenia incorrecta.");
						msg.setCuerpo(mensajeLogIn);
						out.writeObject(msg);
					}
					/* Check si el user esta conectado */
					if(1 == dataAccess.getUserByUsername(userMeta.getUser()).getConectado()){
						msg = new Mensaje(Mensaje.USUARIO_CONECTADO,userMeta.getUser());
						out.writeObject(msg);
					}
					
					/* Check contra ban list */
					else if (bInfo!=null && bInfo.getDias() > 0) {
						msg = new Mensaje(Mensaje.BANNED, bInfo);
						out.writeObject(msg);
					} else {// si no esta penalizado
						/* Envia lista de amigos */
						//TODO loguear en el loglogin, el ingreso.
						mensajeLogIn = new Mensaje(Mensaje.ACCEPTED, dataAccess.getFriends(userMeta));
						dataAccess.insertLogLogin(userMeta);
						msg.setCuerpo(mensajeLogIn);
						out.writeObject(msg);

						/* Lanzamiento Client Handler */
						clientHandler = new ClientHandler(client, userMeta.getUser(), in, out);
						clientHandler.addEventListener(new ClientEventListener(handlerList, userMeta));
						handlerList.put(userMeta.getUser(), clientHandler);
						new Thread(clientHandler).start();
						loggedIn = true;
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private void altaUsuario(UserMetaData user) {
		dataAccess.insertUser(user);
		dataAccess.addUserToPuntaje(user);
	}

	private void verificarUsuario(String nombreUsuario) {
		Mensaje msg;
		try {
			msg = new Mensaje(Mensaje.VERIFICAR_USUARIO, dataAccess.getUserByUsername(nombreUsuario) == null);
			out.writeObject(msg);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
