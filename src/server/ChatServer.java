package server;

import interfaces.servidor.Principal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import common.FriendStatus;
import common.UserMetaData;

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

		/* Thread de espera y manejo de clientes */
		// Thread que espera las conexiones
		new ConnectionListener(port, handlerList).run();
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
	public List<FriendStatus> obtenerUsuarios() {
		//TODO metodo : ver de obtener todos los usuarios registrados en el sistema
		List<FriendStatus> usuarios = new ArrayList<FriendStatus>();
		usuarios.add(new FriendStatus("pepe", -1));
		usuarios.add(new FriendStatus("grillo", 0));
		usuarios.add(new FriendStatus("la Martha", 2));
		return usuarios;
	}

	public void enviarAlerta(String textoAlerta, String usuarioDestino) {
		//Se debe tomar q si el usuarioDestino es null entonces es una alerta general
		//TODO metodo : llamar metodo de alerta
	}

	public UserMetaData obtenerInfoUsuario(String nombreUsuario) {
		//TODO metodo : llamar metodo para obtener los datos de un usuario dado
		return new UserMetaData("pepe", "asd", "Pepe Grillo", "pepe@algo.com", "0810-555-1111", new Date(), new Date(), 1);
	}

	public List<String> obtenerHistorialLoginUsuario(String nombreUsuario) {
		//TODO metodo : llamar metodo para obtener el historial de login de un usuario dado
		List<String> historialLogin = new ArrayList<String>();
		historialLogin.add("algun dia");
		historialLogin.add("algun otro dia");
		historialLogin.add("algun otro dia mas");
		return historialLogin;
	}

	public void blanquearClave(String nombreUsuario) {
		//TODO metodo : llamar al metodo que realice el blanqueo de clave. se considera en poner como clave el mismo nombre de usuario
	}

	public void penalizar(String nombreUsuario, String horas, String motivo) {
		//TODO metodo : llamar al metodo que realice la penalizacion de un usuario dado
	}

	public void despenalizar(String nombreUsuario) {
		//TODO metodo : llamar al metodo que realice la despenalizacion de un usuario dado
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

}
