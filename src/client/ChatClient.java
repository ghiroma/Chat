package client;

import interfaces.cliente.UserLogin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;

import common.Mensaje;
import common.UserMetaData;

public class ChatClient {
	// Config
	private int port;
	private String serverIP;

	// User
	private String username;

	// Conexion / Auxiliar
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	private Socket socket;
	private static ChatClient chatClientInstance;
	//alive
	private Alive alive;


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
	public boolean login(UserMetaData userData){
		//agregar metodo de verificacion contra la base para realizar el login
		//si la conexion es buena se inicia el alive;
		Mensaje msg = new Mensaje(Mensaje.LOG_IN, userData);
		if (true) {
			// alive.start??
			// TODO: metodo login
			this.enviarAlServer(msg);
			return true;
		} else
			return false;
	}

	synchronized public boolean verificarNombreUsuario(String nombre) {
		Mensaje msg = new Mensaje();
		enviarAlServer(msg);
		try {
			//TODO validar que "nombre" este disponible para una nueva ALTA
			msg = (Mensaje) entrada.readObject();
			//return (Boolean)msg.getCuerpo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void altaNuevoUsuario() {
		//TODO llamar al alta del nuevo usuario 
	}

	public void enviarMensaje(Mensaje msg) {
		enviarAlServer(msg);
	}



	// Thread de escucha de mensajes del server
	class ListenFromServer extends Thread {
		synchronized public void run() {
			while (true) {
				try {
					//TODO aca se cambia por la salida de pantalla
					System.out.println((Mensaje) entrada.readObject());
					System.out.print("> ");
				} catch (IOException e) {
					System.out.println("El servidor ha finalizado la conexión.");
					System.exit(1);
				} catch (ClassNotFoundException e2) {}
			}
		}
	}
}
