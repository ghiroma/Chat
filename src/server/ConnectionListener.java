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
	String loggedUser, clientIP;
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
					//TODO ejecuto validacion de login + loggedin = true + lanzo Client Handler
					userMeta = (UserMetaData) msg.getCuerpo();
					BanInfo bInfo = dataAccess.checkBan(userMeta.getUser());
					//  TESTEAR.
					/* Check user y pw contra DB */
					if (!dataAccess.checkUser(userMeta)) {
						msg.setId(Mensaje.DENIED);
						msg.setCuerpo("Usuario o contrasenia incorrecta.");
						out.writeObject(msg);
						client.close();
					}

					/* Check contra ban list */
					else if (bInfo.getDias() > 0) {
						msg = new Mensaje(Mensaje.BANNED, bInfo.getMotivo());
						out.writeObject(msg);
						client.close();
					} else // si no esta penalizado
					{
						/* Envia lista de amigos */
						msg.setId(Mensaje.ACCEPTED);
						msg.setCuerpo(dataAccess.getFriends(userMeta));
						out.writeObject(msg);
						in.readObject(); // espero un Mensaje con ID "ready". Como no se procesa, no lo referenciamos.

						/* Lanzamiento Client Handler */
						clientHandler = new ClientHandler(client, loggedUser, in, out);
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
	
	//VIEJO RUN
/*	public void run() { 

		try {
			server = new ServerSocket(serverPort);

			// Espera y manejo de clientes
			while (true) {
				// NICO: generar un thread de accept y separarlo del conexionListener
				// Asi como esta, la conexion se bloquea si un cliente inicia el programa
				client = server.accept(); // La espera traba el flujo de codigo
				in = new ObjectInputStream(client.getInputStream());
				out = new ObjectOutputStream(client.getOutputStream());
				clientIP = client.getInetAddress().toString();
				clientIP = clientIP.replace("/", " ");
				clientIP = clientIP.trim();
				//  CONSOLA: IP connection attempt

				// Handshake
				// Recibo username y password
				msg = (Mensaje) in.readObject();
				userMeta = (UserMetaData) msg.getCuerpo();
				BanInfo bInfo = dataAccess.checkBan(userMeta.getUser());
				//  TESTEAR.
				// chequeo User y PW contra DB
				if (!dataAccess.checkUser(userMeta)) {
					msg.setId(Mensaje.DENIED);
					msg.setCuerpo("Usuario o contrasenia incorrecta.");
					out.writeObject(msg);
					client.close();
				}

				// Chequeo contra el ban list
				else if (bInfo.getDias() > 0) {
					msg = new Mensaje(Mensaje.BANNED, bInfo.getMotivo());
					out.writeObject(msg);
					client.close();
				} else // si no esta penalizado
				{
					// Envio Accept + amigos con estado
					msg.setId(Mensaje.ACCEPTED);
					msg.setCuerpo(dataAccess.getFriends(userMeta));
					out.writeObject(msg);
					in.readObject(); // espero un Mensaje con ID "ready". Como no se procesa, no lo referenciamos.

					// lanzo client handler
					clientHandler = new ClientHandler(client, loggedUser, in, out);
					clientHandler.addEventListener(new ClientEventListener(handlerList, userMeta));
					handlerList.put(userMeta.getUser(), clientHandler);
					new Thread(clientHandler).start();
					// verificar que tan cabeza es hacer esto.
					//(nuevo thread con un ClientHandler adentro que recibe la data del user)

					//  y escribo en consola que se termino la entrada del
					// cliente al server
				}
			}
		} catch (Exception e) {
			//  adecuado manejo de exceptions
			System.err.println("Exception no manejada en ConnectionListener");
			e.printStackTrace();
		}
	}
*/	
}
