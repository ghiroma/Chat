package client;

import interfaces.cliente.ClienteConversacion;
import interfaces.cliente.ClienteInicial;
import interfaces.cliente.UserLogin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import common.FriendStatus;
import common.Mensaje;
import common.MensajeChat;
import common.MensajeInvitacion;
import common.UserMetaData;

public class ChatClient {
	// Config
	private int port;
	private String serverIP;
	
	// Conexion / Auxiliar
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	private Socket socket;
	private static ChatClient chatClientInstance;
	private Map<Integer, Object> mapMensajes;
	// alive
	//private Alive alive;

	// User
	private UserMetaData usuarioLogeado;
	private ArrayList<FriendStatus> amigos;

	// Front
	private UserLogin frontEndLogIn;
	private ClienteInicial frontEnd;
	private Map<String, ClienteConversacion> mapaConversaciones;

	/* Constructor */
	private ChatClient() {
		try {
			/* Cargo properties */
			loadProperties();
			socket = new Socket(this.serverIP, this.port);
			salida = new ObjectOutputStream(socket.getOutputStream());
			entrada = new ObjectInputStream(socket.getInputStream());
			new ListenFromServer().start();

			chatClientInstance = this;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ChatClient getInstance() {
		if (chatClientInstance == null)
			chatClientInstance = new ChatClient();
		return chatClientInstance;
	}

	public static void main(String args[]) {
		ChatClient.getInstance().go();
	}

	private void go() {
		/* Inicializo GUI de login */
		frontEndLogIn = new UserLogin();
		frontEndLogIn.mostrar();

		/* Lanzo Alive */
		// alive.start //se inicia cuando el login es satisfactorio
	}

	// -----------------
	// Metodos privados
	// -----------------
	private void loadProperties() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("ServerConfig.properties"));
			port = Integer.valueOf(prop.getProperty("port"));
			serverIP = prop.getProperty("ip");
		} catch (FileNotFoundException e1) {
			// Properties no existe => creo uno
			prop.setProperty("port", "16016");
			prop.setProperty("ip", "localhost");
			try {
				prop.store(new FileOutputStream("ServerConfig.properties"), null);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		} catch (Exception e3) {
			e3.printStackTrace();
		}
	}

	private void enviarAlServer(Mensaje msg) {
		try {
			salida.writeObject(msg);
		} catch (IOException e) {
			System.err.println("Error tratando de escribir en el servidor: " + e);
		}
	}

	private void mostrarAlerta(String txtAlerta){
		this.frontEnd.mostrarAlerta(txtAlerta);
	}

	private UserMetaData obtenerUsuario(UserMetaData usuarioVacio) {
		Mensaje msg = new Mensaje(Mensaje.OBTENER_USUARIO, usuarioVacio.getUser());
		try {
			enviarAlServer(msg);
			synchronized(mapMensajes){
				mapMensajes.wait();
				return (UserMetaData)mapMensajes.remove(Mensaje.OBTENER_USUARIO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuarioVacio;
	}

	// -----------------
	// Metodos publicos para las pantallas del cliente
	// -----------------
	@SuppressWarnings("unchecked")
	public ClienteInicial login(UserMetaData userData) {
		// metodo de verificacion contra la base para realizar el login
		// si la conexion es buena se inicia el alive;
		Mensaje msg = new Mensaje(Mensaje.LOG_IN, userData);
		try {
			this.enviarAlServer(msg);
			synchronized(mapMensajes){
				mapMensajes.wait();
				msg=(Mensaje)mapMensajes.remove(Mensaje.LOG_IN);
			}
			if (msg.getId()==Mensaje.ACCEPTED) {
				// alive.start??
				amigos = (ArrayList<FriendStatus>)msg.getCuerpo();
				usuarioLogeado = obtenerUsuario(userData);
	
				this.mapaConversaciones = new HashMap<String ,ClienteConversacion>();
				this.frontEnd = new ClienteInicial();
				return frontEnd;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean verificarNombreUsuario(String nombre) {
		Mensaje msg = new Mensaje(Mensaje.VERIFICAR_USUARIO, nombre);
		try {
			enviarAlServer(msg);
			synchronized(mapMensajes){
				mapMensajes.wait();
				return (Boolean)mapMensajes.remove(Mensaje.VERIFICAR_USUARIO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void altaNuevoUsuario(UserMetaData user) {
		Mensaje msg = new Mensaje(Mensaje.ALTA_USUARIO, user);
		enviarAlServer(msg);
	}

	public void modificarUsuario(String nombre, String email, String tel, String pass) {
		usuarioLogeado.setApyn(nombre);
		usuarioLogeado.setMail(email);
		usuarioLogeado.setTelefono(tel);
		usuarioLogeado.setPassword(pass);
		Mensaje msg = new Mensaje(Mensaje.MODIFICACION_USUARIO, usuarioLogeado);
		enviarAlServer(msg);
	}

	@SuppressWarnings("unchecked")
	public List<String> buscarAmigoPorTexto(String texto) {
		Mensaje msg = new Mensaje(Mensaje.BUSCAR_USUARIO, texto);
		try {
			enviarAlServer(msg);
			synchronized(mapMensajes){
				mapMensajes.wait();
				return (List<String>)mapMensajes.remove(Mensaje.BUSCAR_USUARIO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}

	public void invitarAmigo(String contacto) {
		Mensaje msg = new Mensaje(Mensaje.INVITAR_USUARIO, new MensajeInvitacion(getUsuarioLogeado().getUser(), contacto));
		enviarAlServer(msg);
	}

	// TODO Diego
	public void invitarAmigoAJugar(String contacto) {
		Mensaje msg = new Mensaje(Mensaje.INVITACION_JUEGO, new MensajeInvitacion(getUsuarioLogeado().getUser(), contacto));
		enviarAlServer(msg);
	}

	public void aceptacionInvitacionJuego(Mensaje msg) {
		enviarAlServer(msg);
	}
	//
	public void enviarMensajeChat(String amigo, String texto) {
		Mensaje msg = new Mensaje(Mensaje.ENVIAR_MENSAJE, new MensajeChat(amigo, texto));
		enviarAlServer(msg);
	}

	public void aceptacionInvitacionAmigo(MensajeInvitacion msgInvitacion) {
		Mensaje msg = new Mensaje(Mensaje.ACEPTACION_INVITACION_AMIGO, msgInvitacion);
		enviarAlServer(msg);
	}

	public void close(){
		enviarAlServer(new Mensaje(Mensaje.CERRAR_SESION,null));
		try {
			entrada.close();
			salida.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	// Thread de escucha de mensajes del server
	class ListenFromServer extends Thread {
		public void run() {
			mapMensajes = new HashMap<Integer, Object>();
			while (true) {
				try {
					// Aca se debe se filtrar segun tipo de mensaje recibido
					Mensaje msg = (Mensaje) entrada.readObject();
					if (msg.getId() == Mensaje.ALERTA) {
						mostrarAlerta((String)msg.getCuerpo());
					} else if(msg.getId() == Mensaje.ENVIAR_MENSAJE) {
						MensajeChat msgChat=(MensajeChat)msg.getCuerpo();		
						frontEnd.getNuevaConversacion(msgChat.getDestinatario(),msgChat.getTexto());
					} else if(msg.getId() == Mensaje.INVITAR_USUARIO) {
						MensajeInvitacion msgInvitacion = (MensajeInvitacion)msg.getCuerpo();		
						frontEnd.mostrarPopUpInvitacion(msgInvitacion);
					} else if(msg.getId() == Mensaje.CAMBIO_ESTADO){
						frontEnd.friendStatusChanged(((FriendStatus)msg.getCuerpo()).getUsername(),((FriendStatus)msg.getCuerpo()).getEstado());
					} /* TODO Diego */ else if(msg.getId() == Mensaje.INVITACION_JUEGO){
						frontEnd.mostrarPopUpInvitacionJuego(msg);
					} else {
						synchronized(mapMensajes){
							mapMensajes.put(msg.getId(), msg.getCuerpo());
							mapMensajes.notify();
						}
					}
				} catch (IOException e) {
					System.out.println("El servidor ha finalizado la conexión.");
					System.exit(1);
				} catch (ClassNotFoundException e2) {
				}
			}
		}
	}

	// Getters and Setters
	public UserMetaData getUsuarioLogeado() {
		return usuarioLogeado;
	}
	public ArrayList<FriendStatus> getAmigos() {
		return amigos;
	}
	public Map<String, ClienteConversacion> getMapaConversaciones() {
		return mapaConversaciones;
	}


}
