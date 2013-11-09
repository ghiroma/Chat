package server;

import interfaces.servidor.Principal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class ChatServer {

	private static ChatServer chatServerInstance;
	private HashMap<String, ClientHandler> handlerList;
	private int port;
	private String serverName;
	private Principal frontEnd;

	/* Constructor */
	private ChatServer() {
		handlerList = new HashMap<String, ClientHandler>();
		/* Cargo properties */
		loadProperties();
		chatServerInstance = this;
	}

	public static ChatServer getInstance() {
		if (chatServerInstance == null)
			chatServerInstance = new ChatServer();
		return chatServerInstance;
	}

	/* Main*/
	public static void main(String args[]){
		ChatServer.getInstance().go();
	}


	public void enviarAlerta(String textoAlerta) {
		//TODO llamar metodo de alerta
	}

	public boolean cerrarServer() {
		//TODO llamar metodo de cerrar server
		return true;
	}



	private void go() {
		/*  Lanzamiento de handler de manejo de informacion de GUI */
		frontEnd = new Principal();
		frontEnd.setVisible(true);

		/* Informacion del startup a la consola */
		//TODO console.write();

		/* Thread de espera y manejo de clientes */
		// Thread que espera las conexiones
		new ConnectionListener(port, handlerList).run();
	}

	/* Metodos */
	private void loadProperties(){
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("ServerConfig.properties"));
			port = Integer.valueOf(prop.getProperty("port"));
			serverName = prop.getProperty("name");			
		} catch (FileNotFoundException e1) {
			//Properties no existe => creo uno
			prop.setProperty("port", "16016");
			prop.setProperty("name", "default");
			try{
				prop.store(new FileOutputStream("ServerConfig.properties"),null);
			}
			catch (IOException e2){
				e2.printStackTrace();
			}
			port = 16016;
			serverName = "default";
		} catch (IOException e3) {
			e3.printStackTrace();
		}
	}

	public void logearEvento(String mensaje) {
		this.frontEnd.logearEvento(mensaje);
	}

}
