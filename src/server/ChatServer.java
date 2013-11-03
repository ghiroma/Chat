package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Properties;

public class ChatServer {
	private ArrayList<ClientHandler> handlerList;
	private int port;
	private String serverName;
	
	/* Constructor */
	private ChatServer(){
        handlerList = new ArrayList<ClientHandler>();
		/* Cargo properties */
        loadProperties();    
	}
	
	/* Main*/
	public static void main(String args[]){
       new ChatServer().go();
	}
	
	public void go(){
		
		/*  Lanzamiento de handler de manejo de informacion de GUI */
		//TODO ver GUI del otro grupo
        
		/* Informacion del startup a la consola */
		//TODO console.write();
        
        /* Thread de espera y manejo de clientes */
		new ConnectionListener(port, handlerList).run();	// Thread que espera las conexiones
	}
	
	
	/* Metodos */
	private void loadProperties(){
		Properties prop = new Properties();
		try{
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
