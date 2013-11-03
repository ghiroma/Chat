package client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

public class ChatClient {
	//Config
	private int port;
	private int serverIP;
	
	//User
	private String username;
	
	//Conexion / Auxiliar
	private Socket socket;
	private static ChatClient chatClientInstance;
	
	/* Constructor */
	private ChatClient(){
		/* Cargo properties */
		loadProperties();
		chatClientInstance = this;
	}
	
	public static void main(String args[]){
		ChatClient.getInstance().go();
	}
	
	public void go(){
		
		/* Inicializo GUI de login */
		//TODO GUI login
		//de algna manera obtengo el socket y username
		
		/* Lanzo Alive*/
		//TODO alive.start
		
		/* Lanzo GUI principal*/
		//TODO GUI principal
		
	}
	
	
	
	
	
	/* Metodos */
	private void loadProperties(){
		Properties prop = new Properties();
		try{
			prop.load(new FileInputStream("ServerConfig.properties"));
			port = Integer.valueOf(prop.getProperty("port"));
			serverIP = Integer.valueOf(prop.getProperty("ip"));
		}		
		
		catch (FileNotFoundException e1){
			//Properties no existe => creo uno
			prop.setProperty("port", "16016");
			prop.setProperty("ip", "localhost");
			try{
				prop.store(new FileOutputStream("ServerConfig.properties"),null);
			}
			catch (IOException e2){
				e2.printStackTrace();
			}

		}
		
		catch (IOException e3){
			e3.printStackTrace();
		}

	}

	public static ChatClient getInstance(){
		if(chatClientInstance == null)
			chatClientInstance = new ChatClient();
		return chatClientInstance;
		
	}
	
	public Socket getSocket(){
		return socket;
	}
	
}
