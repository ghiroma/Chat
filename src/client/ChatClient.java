package client;

import interfaces.cliente.UserLogin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import common.FriendStatus;
import common.Mensaje;
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
	//alive
	private Alive alive;

	// User
	private UserMetaData usuarioLogeado;
	private ArrayList<FriendStatus> amigos;


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
		//interface
		UserLogin frontEnd = new UserLogin();
		frontEnd.mostrar();
		/* Inicializo GUI de login */ //done
		// TODO GUI login //done
		// de algna manera obtengo el socket y username
               
		/* Lanzo Alive */
		// TODO alive.start //done se inicia cuando el login es satisfactorio

		/* Lanzo GUI principal */
		// TODO GUI principal
	}


	//-----------------
	// Metodos privados
	//-----------------
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
		}
		catch (Exception e3) {
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


	//-----------------
	// Metodos publicos para las pantallas del cliente
	//-----------------
	synchronized public boolean login(UserMetaData userData){
		//agregar metodo de verificacion contra la base para realizar el login
		//si la conexion es buena se inicia el alive;
		Mensaje msg = new Mensaje(Mensaje.LOG_IN, userData);
		if (true) {
			// alive.start??
			// TODO: metodo : login
			// this.enviarAlServer(msg);
			amigos = new ArrayList<FriendStatus>();
			amigos.add(new FriendStatus("pepe", 1));
			amigos.add(new FriendStatus("grillo", 0));
			amigos.add(new FriendStatus("la Martha", 2));
			usuarioLogeado = new UserMetaData("pepe", "asd", "Pepe Grillo", "pepe@algo.com", "0810-555-1111", new Date(), new Date(), 1);
			return true;
		} else
			return false;
	}

	synchronized public boolean verificarNombreUsuario(String nombre) {
		Mensaje msg = new Mensaje();
		enviarAlServer(msg);
		try {
			//TODO metodo : validar que "nombre" este disponible para una nueva ALTA
			msg = (Mensaje) entrada.readObject();
			//return (Boolean)msg.getCuerpo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void altaNuevoUsuario() {
		//TODO metodo : llamar al alta del nuevo usuario 
	}

	public void modificarUsuario() {
		//TODO metodo : llamar a la modificacion del usuario 
	}

	synchronized public List<String> buscarAmigoPorTexto(String texto) {
		//TODO metodo : llamar a la busqueda de contactos conectados que ya no sean amigos segun texto ingresado
		List<String> listaFiltrada = new ArrayList<String>();
		listaFiltrada.add("Martha");
		listaFiltrada.add("Wanda");
		return listaFiltrada;
	}

	public void invitarAmigo(String contacto) {
		//TODO metodo : llamar a la invitacion de un amigo
	}

	public void enviarMensajeChat(String amigo, String texto) {
		//TODO metodo : llamar al envio de mensajes de una conversacion
	}



	// Thread de escucha de mensajes del server
	class ListenFromServer extends Thread {
		synchronized public void run() {
			while (true) {
				try {
					//TODO aca se debe se filtrar segun tipo de mensaje recibido
					System.out.println((Mensaje) entrada.readObject());
					System.out.print("> ");
				} catch (IOException e) {
					System.out.println("El servidor ha finalizado la conexión.");
					System.exit(1);
				} catch (ClassNotFoundException e2) {}
			}
		}
	}

	//Getters and Setters
	public UserMetaData getUsuarioLogeado() {
		return usuarioLogeado;
	}
	public ArrayList<FriendStatus> getAmigos() {
		return amigos;
	}

}
