package server;

import java.io.*;
import java.net.*;
import common.*;

public class ClientHandler extends Thread{
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket client;
    private String IP;
    private Mensaje msg;
    private String user;
 
    /* Constructores */
    public ClientHandler(Socket client, String user, ObjectInputStream in, ObjectOutputStream out)
    {
        this.client = client;
        this.in = in;
        this.out = out;
        this.user = user;
        
        IP = client.getInetAddress().toString();
        IP = IP.replace("/"," ");
        IP = IP.trim();
    }
    
    /* Metodos */
    @Override
    public void run()
    {
        try{
            client.setSoTimeout(10000); //10 seg
            while(client.isConnected())
            {
              msg = (Mensaje)in.readObject();		// se traba acá hasta que hay mensaje
              readMessage(msg);
            }
        }
        
        catch (SocketTimeoutException e){
           /* msg = new Mensaje("logout","",user);
            removeUser(out);
            serverFrame.removeUser(user);
            userList.remove(user);
            console.write(user + " disconnected.");
            msg.setMessage("disconnected.");
            serverFrame.writeMessage(msg);
            sayToAll(msg);
            try{
            client.close(); */
        } 
        
        catch (IOException ioe) { 
            	ioe.printStackTrace(System.err); 
        }    
        
        catch (Exception e) 
        { 
            System.err.println(e.getMessage());
            //removeUser(out);
            //serverFrame.removeUser(msg.getUser());
        }
    }
    
    private void readMessage(Mensaje msg)
    {/*
    	TODO actualizar la lectura de mensajes del cliente al formato actual
    
        try{
        if(msg.getId().equals("say"))
        {
            sayToAll(msg);
            serverFrame.writeMessage(msg);
        }   
              
        if(msg.getId().equals("logout"))
        {
            removeUser(out);
            serverFrame.removeUser(msg.getUser());
            userList.remove(msg.getUser());
            console.write(msg.getUser() + " logged out.");
            msg.setMessage("logged out.");
            serverFrame.writeMessage(msg);
            sayToAll(msg);                  
            client.close();
         }
             
         if(msg.getId().equals("kicked"))
         {
            sayToAll(msg);
            removeUser(out);
            serverFrame.removeUser(msg.getMessage());
            userList.remove(msg.getMessage());
            console.write(msg.getMessage() + " was kicked by " + msg.getUser() + ".");
            serverFrame.writeMessage(msg);                  
            client.close();    
         }
         
         if(msg.getId().equals("banned"))
         {
            sayToAll(msg);
            removeUser(out);
            serverFrame.removeUser(msg.getMessage());
            userList.remove(msg.getMessage());
            console.write(msg.getMessage() + " was banned by " + msg.getUser() + ".");
            serverFrame.writeMessage(msg);                  
            client.close();    
         }
        } catch (IOException se) {
            se.printStackTrace(System.err);
        }*/
    }
    
    public String getUser()
    {
        return user;
    }
    
    public String getIP()
    {
        return IP;
    }
    
    public void close(Mensaje msg)
    {
    	// TODO verificar necesidad de la funcion
        readMessage(msg);
    }
}
