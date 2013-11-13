package server;

import interfaces.servidor.Principal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.h2.constant.SysProperties;

import common.UserMetaData;

import dataTier.DataAccess;

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
	private void go() {
		/*  Lanzamiento de handler de manejo de informacion de GUI */
		frontEnd = new Principal();
		frontEnd.setVisible(true);

		/* Informacion del startup a la consola */
		//TODO console.write();

		/* Espera de conexiones */
		new ConnectionAccepter(port).run();	
		
		//Los connection listener son lanzados por el Accepter
		//new ConnectionListener(port, handlerList).run();
	}


	//-----------------
	// Metodos privados
	//-----------------
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


	//-----------------
	// Metodos publicos para las pantallas del cliente
	//-----------------
	public List<UserMetaData> obtenerUsuarios() {
		return DataAccess.getInstance().getAllUsers();
	}

	public void enviarAlerta(String textoAlerta, String usuarioDestino) {
		//Se debe tomar q si el usuarioDestino es null entonces es una alerta general
		//TODO metodo : llamar metodo de alerta
	}

	public UserMetaData obtenerInfoUsuario(String nombreUsuario) {
		return DataAccess.getInstance().getUserByUsername(nombreUsuario);
	}

	public List<String> obtenerHistorialLoginUsuario(String nombreUsuario) {
		return DataAccess.getInstance().getLoginHistory(nombreUsuario);
	}

	public void blanquearClave(String nombreUsuario) {
		DataAccess.getInstance().blanquearClave(nombreUsuario, nombreUsuario);
		this.logearEvento("Server :: Se blanqueo la clave para el usuario: " + nombreUsuario);
	}

	public void penalizar(String nombreUsuario, int horas, String motivo) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.HOUR_OF_DAY, horas);
		DataAccess.getInstance().penalizar(nombreUsuario, motivo, c.getTime().getTime());
		this.logearEvento("Server :: Se penalizo al usuario: " + nombreUsuario + " por: " + horas + " hs");
	}

	public void despenalizar(String nombreUsuario) {
		if(DataAccess.getInstance().checkBan(nombreUsuario) != null){
			DataAccess.getInstance().despenalizar(nombreUsuario);
			this.logearEvento("Server :: Se despenalizo al usuario: " + nombreUsuario);
		} else {
			this.logearEvento("Server :: El usuario "+ nombreUsuario +" no se encuentra penalizado");
		}
	}

	public void desconectarUsuario(String nombreUsuario) {
		//TODO metodo : llamar al metodo que realice la desconexion de un usuario dado
	}

	public boolean cerrarServer() {
		//TODO metodo : llamar metodo de cerrar server
		return true;
	}

	public void logearEvento(String mensaje) {
		//TODO realizar el llamado al metodo cada vez q haya q logear algo
		this.frontEnd.logearEvento(mensaje);
	}

	
	//-----------------
	// Getters & Setters
	//-----------------	
	public HashMap<String, ClientHandler> getHandlerList(){
		return handlerList;
	}
	
}
