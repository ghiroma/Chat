package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import common.Mensaje;
import common.UserMetaData;
import dataTier.BanInfo;

public class ConnectionAccepter extends Thread{
	private int serverPort;
	
	public ConnectionAccepter(int port){
		serverPort = port;
	}
	
	public void run(){
		ServerSocket server;
		Socket client;
		ObjectInputStream in;
		ObjectOutputStream out;
		String clientIP;
		
		try {
			server = new ServerSocket(serverPort);

			while (true) {
				client = server.accept(); // La espera traba el flujo de codigo 
				in = new ObjectInputStream(client.getInputStream());
				out = new ObjectOutputStream(client.getOutputStream());
				clientIP = client.getInetAddress().toString();
				clientIP = clientIP.replace("/", " ");
				clientIP = clientIP.trim();
				
				new ConnectionListener(client,clientIP).run();
				}
		} catch (Exception e) {
			System.err.println("Exception no manejada en ConnectionAccepter");
			e.printStackTrace();
		}
	}
	
}
