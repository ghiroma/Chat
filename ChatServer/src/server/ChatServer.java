package server;

import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ChatServer {
	ArrayList<ClientHandler> handlerList;
	int port;
	
	/* Constructor */
	private ChatServer(){
        handlerList = new ArrayList<ClientHandler>();
		/* Leo los datos del properties.txt */
        //loadProperties();      
	}
	
	/* Main*/
	public static void main(String args[]){
       new ChatServer().go();
	}
	
	public void go(){
		
		/*  Lanzamiento de handler de manejo de informacion de GUI */
    
        /* Informacion del startup a la consola */
		//console.write();
        
        /* Thread de espera y manejo de clientes */
		new ConnectionListener(port, handlerList).run();
	}
}
