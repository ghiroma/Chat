package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import common.*;

public class ConnectionListener extends Thread{
	ArrayList<ClientHandler> handlerList;
	ClientHandler clientHandler;    
	String loggedUser,clientIP;     
    Mensaje msg;         
    ObjectInputStream in;
    ObjectOutputStream out;         
    ServerSocket server;
    Socket client;  
    int serverPort;
    
    /* Constructores */
    public ConnectionListener(int serverPort, ArrayList<ClientHandler> handlerList){
    	this.serverPort = serverPort;
    	this.handlerList = handlerList;
    }
    
    /* Metodos */
	public void run(){
       
		try{
            server = new ServerSocket(serverPort);
            
            /* Espera y manejo de clientes */
            while(true)
            {
                client = server.accept();
                in = new ObjectInputStream(client.getInputStream());
                out = new ObjectOutputStream(client.getOutputStream());
                clientIP = client.getInetAddress().toString();
                clientIP = clientIP.replace("/"," ");
                clientIP = clientIP.trim();
                //TODO console.write("IP: "+clientIP+" streams: OK.");
                
           /* Handshake */
                /* obtengo username y password*/
                msg = (Mensaje)in.readObject(); 
                //TODO verifico contra base de datos
                
                /* TODO Chequeo contra el ban list */ 
                if(false)
                {
                    msg = new Mensaje(Mensaje.BANNED,"RAZON");	//TODO obtener la razon de la tabla
                    out.writeObject(msg);
                    client.close();
                }
                else //si no esta penalizado
                {
                    /* lanzo client handler */
                    clientHandler = new ClientHandler(client, loggedUser,in, out);
                    handlerList.add(clientHandler);
                    new Thread(clientHandler).start();  //verificar que tan cabeza es hacer esto.   (nuevo thread con un ClientHandler adentro que recibe la data del user)
                    
                    /* aviso de cambio de estado */
                    //TODO y   escribo en consola
                }  
            }
         } 
         catch(Exception e) 
         { 
        	 //TODO adecuado manejo de exceptions
             System.err.println(e.getMessage());
         }
	}
}
