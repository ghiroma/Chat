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
import java.util.Map;
import java.util.Properties;
import groups.Grupo;
import common.MensajeGrupo;
import common.UserMetaData;

import dataTier.DataAccess;

public class ChatServer {

	private static ChatServer chatServerInstance;
	private HashMap<String, ClientHandler> handlerList;
	private HashMap<String, Grupo> grupoMap;
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
		if(usuarioDestino == null) {
			this.logearEvento("Server :: Alerta general: " + textoAlerta);
			for(Map.Entry<String, ClientHandler> entry : this.handlerList.entrySet()) {
				ClientHandler client = (ClientHandler)entry.getValue();
				client.enviarAlerta(textoAlerta);
			}
		} else {
			this.logearEvento("Server :: Alerta para " + usuarioDestino + ": " + textoAlerta);
			this.handlerList.get(usuarioDestino).enviarAlerta(textoAlerta);
		}
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
		this.logearEvento("Server :: Se desconecta al usuario " + nombreUsuario);
		this.handlerList.get(nombreUsuario).cerrarSesion();
		actualizarUsuarios();
	}	

	public boolean cerrarServer() {
		this.logearEvento("Server :: Se cierra el servidor");
		try {
			for(Map.Entry<String, ClientHandler> entry : this.handlerList.entrySet()) {
				ClientHandler client = (ClientHandler)entry.getValue();
				client.cerrarSesion();
			}
			return true;
		} catch(Exception e) {
			return false;
		}
	}

	public void logearEvento(String mensaje) {
		this.frontEnd.logearEvento(mensaje);
	}


	public void actualizarUsuarios() {
		this.frontEnd.actualizarListaUsuarios();
	}

	//-----------------
	// Getters & Setters
	//-----------------	
	public HashMap<String, ClientHandler> getHandlerList(){
		return handlerList;
	}

	/**
	 * Grupos
	 */
	
	public void crearGrupo(MensajeGrupo mensajeGrupo) {
		// TODO Auto-generated method stub
		Grupo grupo = mensajeGrupo.getGrupo();
		grupoMap.put(grupo.getNombre(), grupo);
		for (String usuario : grupo.getUsuarios()) {
			ClientHandler handler=handlerList.get(usuario);
			handler.enviarMensajeChat(grupo.getNombre(), grupo.getModerador()+" ha creado la sala de chat.");
		}
	}
	
	public void enviarMensajeGrupo(MensajeGrupo mensajeGrupo) {
		// TODO Auto-generated method stub
		Grupo grupo = mensajeGrupo.getGrupo();
		grupoMap.put(grupo.getNombre(), grupo);
		for (String usuario : grupo.getUsuarios()) {
			ClientHandler handler=handlerList.get(usuario);
			handler.enviarMensajeChat(grupo.getNombre(), mensajeGrupo.getEmisor()+": " + mensajeGrupo.getMensaje());
		}
	}
}
