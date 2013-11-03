package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import common.*;
import dataTier.BanInfo;
import dataTier.DataAccess;

public class ConnectionListener extends Thread{
	ArrayList<ClientHandler> handlerList;
	ClientHandler clientHandler;    
	String loggedUser,clientIP;     
    Mensaje msg;
    UserMetaData userMeta;
    ObjectInputStream in;
    ObjectOutputStream out;         
    ServerSocket server;
    Socket client;  
    int serverPort;
    DataAccess dataAccess;
    
    /* Constructores */
    public ConnectionListener(int serverPort, ArrayList<ClientHandler> handlerList){
    	this.serverPort = serverPort;
    	this.handlerList = handlerList;
    	dataAccess = DataAccess.getInstance();
    }
    
    /* Metodos */
	public void run(){
       
		try{
            server = new ServerSocket(serverPort);
            
            /* Espera y manejo de clientes */
            while(true)
            {
                client = server.accept();							//La espera traba el flujo de codigo	
                in = new ObjectInputStream(client.getInputStream());
                out = new ObjectOutputStream(client.getOutputStream());
                clientIP = client.getInetAddress().toString();
                clientIP = clientIP.replace("/"," ");
                clientIP = clientIP.trim();
                //TODO CONSOLA: IP connection attempt
                
                /* Handshake */
                /* Recibo username y password*/
                msg = (Mensaje)in.readObject(); 
                userMeta = (UserMetaData)msg.getCuerpo();
                BanInfo bInfo = dataAccess.checkBan(userMeta);
                //TODO TESTEAR. 
                if(!dataAccess.checkUser(userMeta)){		//chequeo User y PW contra DB
                	msg.setId(Mensaje.DENIED);
                	msg.setCuerpo("Usuario o contraseña incorrecta.");
                	out.writeObject(msg);
                	client.close();
                }
                
                /* Chequeo contra el ban list */ 
                else if(bInfo.getDias() > 0)
                	{
                    	msg = new Mensaje(Mensaje.BANNED,bInfo.getMotivo());
                    	out.writeObject(msg);
                    	client.close();
                	}
                	else //si no esta penalizado
                	{
                		/* Envio Accept + amigos con estado  */
                		msg.setId(Mensaje.ACCEPTED);
                		msg.setCuerpo(dataAccess.getFriends(userMeta));
                		out.writeObject(msg);
                		in.readObject();		//espero un Mensaje con ID "ready". Como no se procesa, no lo referenciamos.
                		
                	    /* lanzo client handler */
                		clientHandler = new ClientHandler(client, loggedUser,in, out);
                		clientHandler.addEventListener(new ClientEventListener(handlerList,userMeta.getUser()));
                    	handlerList.add(clientHandler);
                    	new Thread(clientHandler).start();  //verificar que tan cabeza es hacer esto.   (nuevo thread con un ClientHandler adentro que recibe la data del user)                    
                    	//TODO y escribo en consola que se termino la entrada del cliente al server
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
