package server;

import interfaces.servidor.Principal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class ChatServer {

	private HashMap<String, ClientHandler> handlerList;
	private int port;
	private String serverName;

	/* Constructor */
	private ChatServer() {
		handlerList = new HashMap<String, ClientHandler>();
		/* Cargo properties */
		loadProperties();
	}

	/* Main*/
	public static void main(String args[]){
		new ChatServer().go();
	}

	public void go(){

		/*  Lanzamiento de handler de manejo de informacion de GUI */
		Principal frontEnd = new Principal();
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
		}
		catch (FileNotFoundException e1){
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
		}
		catch (IOException e3){
			e3.printStackTrace();
		}

	}
}
