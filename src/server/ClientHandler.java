package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.event.EventListenerList;

import common.FriendStatus;
import common.Mensaje;
import common.MensajeChat;
import common.MensajeConsulta;
import common.MensajeEstadoJuego;
import common.MensajeGrupo;
import common.MensajeInvitacion;
import common.MensajeJuego;
import common.MensajeMovimiento;
import common.MensajePartida;
import common.MensajeRespuestaMovimiento;
import common.MensajeSolicitudGrupo;
import common.UserMetaData;
import dataTier.DataAccess;
import events.StatusChangedEvent;

public class ClientHandler extends Thread {

	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket client;
	private String IP;
	private Mensaje msg;
	private String user;
	private EventListenerList listenerList;
	private int estado;
	private DataAccess dataAccess;

	/* Constructores */
	public ClientHandler(Socket client, String user, ObjectInputStream in, ObjectOutputStream out) {
		this.listenerList = new EventListenerList();
		this.client = client;
		this.in = in;
		this.out = out;
		this.user = user;
		estado = 1;
		dataAccess = DataAccess.getInstance();
		IP = client.getInetAddress().toString();
		IP = IP.replace("/", " ");
		IP = IP.trim();
	}

	/* Run */
	@Override
	public void run() {
		try {
			//client.setSoTimeout(10000); // 10 seg, el alive tira cada 5 una senial
			/* evento de inicio sesion */
			dispatchEvent(new StatusChangedEvent(this, user, estado));
			ChatServer.getInstance().logearEvento("Server :: " + user + " inicio sesion.");
			ChatServer.getInstance().actualizarUsuarios();
			/* Inicio escucha al cliente */
			while (client.isConnected()) {
				// se traba aca hasta que hay mensaje
				msg = (Mensaje) in.readObject();

				//Switch con cada constante de mensaje ENVIADA POR EL CLIENTE que ejecute un metodo privado.
				switch (msg.getId()) {
				case Mensaje.MODIFICACION_USUARIO:
					modificacionUsuario((UserMetaData)msg.getCuerpo());
					ChatServer.getInstance().logearEvento("Server :: " + user + " cambio su informacion.");
					break;
				case Mensaje.ENVIAR_MENSAJE:
					enviarMensajeChat((MensajeChat)msg.getCuerpo());
					ChatServer.getInstance().logearEvento("Server :: " + user + " envio mensaje a " + ((MensajeChat)msg.getCuerpo()).getDestinatario());
					break;
				case Mensaje.BUSCAR_USUARIO:
					buscarUsuarios((String)msg.getCuerpo());
					ChatServer.getInstance().logearEvento("Server :: " + user + " busco: " + (String)msg.getCuerpo());
					break;
				case Mensaje.INVITAR_USUARIO:
					invitarUsuario((MensajeInvitacion)msg.getCuerpo());
					ChatServer.getInstance().logearEvento("Server :: " + user + " solicito amistad a: " + ((MensajeInvitacion)msg.getCuerpo()).getInvitado());
					break;
				case Mensaje.ACEPTACION_INVITACION_AMIGO:
					aceptacionInvitacionAmistad((MensajeInvitacion)msg.getCuerpo());
					ChatServer.getInstance().logearEvento("Server :: " + user + " acepto amistad a: " + ((MensajeInvitacion)msg.getCuerpo()).getSolicitante());
					break;
				case Mensaje.OBTENER_USUARIO:
					obtenerUsuario((String)msg.getCuerpo());
					break;
				case Mensaje.CERRAR_SESION:
					ChatServer.getInstance().logearEvento("Server :: " + user + " cerro sesion.");
					cerrarSesion();
					break;
				case Mensaje.PEDIR_PUNTUACION:
					ChatServer.getInstance().obtenerPuntuacionPorUsuarioCliente((String)msg.getCuerpo());
					ChatServer.getInstance().logearEvento("Server :: "+user +" pidio su puntuacion de tateti");
					break;
				case Mensaje.INVITACION_JUEGO:
					enviarInvitacionJuego((String)msg.getCuerpo());
					ChatServer.getInstance().logearEvento("Server :: " + user + " invito a jugar a " + ((String)msg.getCuerpo()) + ".");
					break;
				case Mensaje.ACEPTO_TATETI:	//Version Nico; Si estas aca, esto es un handler del invitado
					String invitado = user;
					String solicitante = (String) msg.getCuerpo();
					//inicio partida en solicitante
					ChatServer.getInstance().logearEvento("Server :: " + invitado + " acepto jugar con " + solicitante);
					//inicio partida en invitado (local)
					iniciarPartida(solicitante,invitado);
					break;
				/*case Mensaje.INICIO_PARTIDA:
					agregarPartida(msg);
					break;*/			
				case Mensaje.RESPUESTA_CONSULTA_PARTIDAS:
					if(((MensajeConsulta)msg.getCuerpo()).isCantValida()) {
						String sol = ((MensajeConsulta)msg.getCuerpo()).getSolicitante();
						String inv = ((MensajeConsulta)msg.getCuerpo()).getInvitado();
						MensajePartida mp = new MensajePartida(sol,inv,inv);
						iniciarPartida(new Mensaje(Mensaje.INICIO_PARTIDA,mp));
					}
					break;
				case Mensaje.MOVIMIENTO:
					//obtengo handler del destinatario
					MensajeMovimiento msgMov = (MensajeMovimiento) msg.getCuerpo();
					ClientHandler handlerDestino = ChatServer.getInstance().getHandlerList().get(msgMov.getDestinatario());
					//le envio el movimiento
					handlerDestino.enviarMovimiento(msgMov);				
					break;
				case Mensaje.RESPUESTA_VERIFICACION_MOVIMIENTO:
					if(((MensajeRespuestaMovimiento)msg.getCuerpo()).isValido())
						actualizarMovimiento((MensajeRespuestaMovimiento)msg.getCuerpo());
					break;
				case Mensaje.RESPUESTA_ACTUALIZACION_PIZARRA:
					actualizarMovimiento(msg);
					break;
				case Mensaje.ACTUALIZAR_TATETI:
					actualizarTateti(msg);
					break;
				case Mensaje.EMPATE:
					finPartida((MensajeEstadoJuego)msg.getCuerpo());
					break;
				case Mensaje.GANADOR:
					finPartida((MensajeEstadoJuego)msg.getCuerpo());
					break;
				case Mensaje.ENVIAR_MENSAJE_TATETI:
					enviarMensajeChatTaTeTi((MensajeChat)msg.getCuerpo());
					ChatServer.getInstance().logearEvento("Server :: "+user + " envio mensaje en el tateti a "+ ((MensajeChat)msg.getCuerpo()).getDestinatario());
					break;
				case Mensaje.ABANDONO:
					UserMetaData userMeta = dataAccess.getUserByUsername(user);
					dataAccess.addDefeat(userMeta);	//el que abandona es el que envia el mensaje "abandono"
					
					userMeta = dataAccess.getUserByUsername((String)msg.getCuerpo());
					dataAccess.addVictory(userMeta);
					
					ChatServer.getInstance().logearEvento("Server :: "+user + " abandono la partida con "+ (String) msg.getCuerpo());
					ChatServer.getInstance().getHandlerList().get((String)msg.getCuerpo()).jugadorAbandono(user);						
					break;
				case Mensaje.CREAR_GRUPO:
					//TODO: logear la creacion del grupo
					crearGrupo((MensajeGrupo)msg.getCuerpo());
					break;
				case Mensaje.MENSAJE_GRUPAL:
					enviarMensajeGrupo((MensajeGrupo)msg.getCuerpo());
					break;
				case Mensaje.CERRAR_GRUPO:
					cerrarGrupo((MensajeGrupo)msg.getCuerpo());
					break;
				case Mensaje.MENSAJE_USUARIO_GRUPO:
					//TODO recibo un mensaje que es para un usuario en especifico del chat grupal//
					enviarMensajeUsuarioEnGrupo((MensajeGrupo) msg.getCuerpo());
					//get cuerpo tiene todo lo referido al mensaje
					break;
				case Mensaje.OBTENER_GRUPOS:
					buscarGrupos();
					break;
				case Mensaje.SOLICITAR_UNION_GRUPO:
					solicitarUnionGrupo((MensajeSolicitudGrupo)msg.getCuerpo());
					break;
				case Mensaje.ACEPTACION_SOLICITUD_UNION_GRUPO:
					aceptarSolicitudUnionGrupo((MensajeSolicitudGrupo)msg.getCuerpo());
					break;
				}
			} 
		} catch (SocketException se) {
			ChatServer.getInstance().logearEvento("Server :: "+ user + " se ha desconectado");
		} catch (IOException ioe) {
			ChatServer.getInstance().logearEvento("Server :: "+ user + " se ha desconectado");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Exception no manejada en ClientHandler");
		} finally {
			// Actualizo en la DB el estado
			UserMetaData userMeta = DataAccess.getInstance().getUserByUsername(user);
			userMeta.setConectado(0);
			DataAccess.getInstance().modifyUser(userMeta);
			ChatServer.getInstance().actualizarUsuarios();
		} 
	}

	/* Metodos privados */
	private void obtenerUsuario(String nombreUsuario) {
		try {
			out.writeObject(new Mensaje(Mensaje.OBTENER_USUARIO, DataAccess.getInstance().getUserByUsername(nombreUsuario)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void modificacionUsuario(UserMetaData user) {
		DataAccess.getInstance().modifyUser(user);
	}

	private void buscarUsuarios(String txtBusqueda) {
		try {
			out.writeObject(new Mensaje(Mensaje.BUSCAR_USUARIO, DataAccess.getInstance().getUsers(txtBusqueda)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void enviarMensajeChat(MensajeChat msgChat) {
		ClientHandler client = ChatServer.getInstance().getHandlerList().get(msgChat.getDestinatario());
		client.enviarMensajeChat(Mensaje.MENSAJE_INDIVIDUAL, user, msgChat.getTexto());
	}

	private void invitarUsuario(MensajeInvitacion msgInvitacion) {
		ClientHandler client = ChatServer.getInstance().getHandlerList().get(msgInvitacion.getInvitado());
		client.recibirInvitacion(msgInvitacion);
	}

	private void jugadorAbandono(String player){
		try {
			out.writeObject(new Mensaje(Mensaje.ABANDONO,player));
		} catch (IOException e) {
			System.err.println("Error al enviar mensaje de abandono al usuario " + user);
		}
	}
	/* Metodos publicos */
	public void cerrarSesion() {
		try {
			/* Almaceno estado en la DB */
			UserMetaData userMeta = DataAccess.getInstance().getUserByUsername(user);
			userMeta.setConectado(0);
			DataAccess.getInstance().modifyUser(userMeta);
			
			/* Cierro conexion y streams */
			in.close();
			out.close();
			client.close();
			/* Avisa a todos que se desconecta el user */
			dispatchEvent(new StatusChangedEvent(this, user, 0));
		} catch (IOException e) {
			System.err.println("Error al cerrar sesion de " + user);
		}
	}

	public void enviarAlerta(String textoAlerta){
		try {
			Mensaje msg=new Mensaje(Mensaje.ALERTA, textoAlerta);
			out.writeObject(msg);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void enviarMensajeChat(int tipoMensaje, String emisor, String texto) {
		//el tipo de mensaje diferencia el mensaje de un mensaje normal, grupal o enviado al moderador del grupo.
		try {
			out.writeObject(new Mensaje(tipoMensaje, new MensajeChat(emisor, texto)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void recibirInvitacion(MensajeInvitacion msgInvitacion) {
		try {
			out.writeObject(new Mensaje(Mensaje.INVITAR_USUARIO, msgInvitacion));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void aceptacionInvitacionAmistad(MensajeInvitacion msgInvitacion) {
		DataAccess.getInstance().insertAmigos(msgInvitacion.getInvitado(), msgInvitacion.getSolicitante());
		//TODO Guille Actualizar lista de amigos en el invitado y solicitante.
		ChatServer.getInstance().getHandlerList().get(msgInvitacion.getInvitado()).actualizarFriendList(msgInvitacion.getSolicitante());
		ChatServer.getInstance().getHandlerList().get(msgInvitacion.getSolicitante()).actualizarFriendList(msgInvitacion.getInvitado());
		dispatchEvent(new StatusChangedEvent(this, user, 1));
	}

	public void actualizarFriendList(String amigo)
	{
		try{
		out.writeObject(new Mensaje(Mensaje.AGREGAR_AMIGO_FRIENDLIST,amigo));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/* Metodos de update (Son llamados desde los ClientEventListener)*/
	public void friendStatusUpdate(String user, int estado){
		try {
			out.writeObject(new Mensaje(Mensaje.CAMBIO_ESTADO, new FriendStatus(user, estado)));
		} catch(SocketException e1) {
		} catch(IOException e) {
			e.printStackTrace();
			System.err.println("Error al enviar cambio de estado al cliente.");
		}
	}

	/* Eventos */
	public void addEventListener(ClientEventListener e) {
		listenerList.add(ClientEventListener.class, e);
	}

	public void removeEventListener(ClientEventListener e) {
		listenerList.remove(ClientEventListener.class, e);
	}

	private void dispatchEvent(EventObject e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {

			if (e instanceof StatusChangedEvent) {
				StatusChangedEvent e1 = (StatusChangedEvent)e;
				((ClientEventListener) listeners[i+1]).statusChanged(e1);
			}

//			if(e instanceof TuEvento)
//				((ClientEventListener) listeners[i+1]).tuMetodo(e);
		}
	}

	/* Getters & Setters */
	public String getUser() {
		return user;
	}
	public String getIP() {
		return IP;
	}

	
	
	
	
	
	/* Inicio: TATETI */
	private void enviarInvitacionJuego(String invitado) { //Version Nico
		ClientHandler client = ChatServer.getInstance().getHandlerList().get(invitado);	// Obtengo thread del invitado
		client.enviarInvitacionJuego(new Mensaje(Mensaje.INVITACION_JUEGO,user));		// Envio invitacion de juego a mi amigo. "INVITACION_JUEGO, solicitante"
	}
		
	private void enviarInvitacionJuego(Mensaje msg) {
		try {
			out.writeObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void iniciarPartida(String solicitante, String invitado){	//Version Nico; Este metodo lo ejecutan tanto el solicitante como el invitado
		//Siempre comienza el solicitante
		try {
			System.out.println("User: " + user + " Solicitante: " + solicitante + " Invitado: " + invitado);
			//inicio local
			out.writeObject(new Mensaje(Mensaje.INICIO_PARTIDA,new MensajeJuego(solicitante,solicitante)));
			//inicia el rival
			ClientHandler client = ChatServer.getInstance().getHandlerList().get(solicitante);
			client.iniciarPartida(new Mensaje(Mensaje.INICIO_PARTIDA,new MensajeJuego(invitado,solicitante)));
			
		} catch (IOException e) {
			System.err.println("Error al enviar mensaje de inicio de partida.");
		}
	}
	
	private void enviarMovimiento(MensajeMovimiento msgMov){
		try{
			out.writeObject(new Mensaje(Mensaje.MOVIMIENTO,msgMov));
		} catch(IOException e){
			e.printStackTrace();
		}
	}
		
	private void iniciarPartida(Mensaje msg) {
		try {
			out.writeObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private void actualizarMovimiento(MensajeRespuestaMovimiento msg) {
		try {
			ClientHandler client = ChatServer.getInstance().getHandlerList().get(msg.getJugador2());
			client.actualizarMovimiento(new Mensaje(Mensaje.ACTUALIZAR_MOVIMIENTO,msg));					// Actualizo movimiento en rival
			out.writeObject(new Mensaje(Mensaje.ACTUALIZAR_MOVIMIENTO,msg));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void actualizarMovimiento(Mensaje msg) {
		try {
			out.writeObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void actualizarTateti(Mensaje msg) {
		try {
			MensajeRespuestaMovimiento mrm = (MensajeRespuestaMovimiento)msg.getCuerpo();
			ClientHandler client = ChatServer.getInstance().getHandlerList().get(mrm.getJugador2());
			client.actualizarTateti(mrm);
			out.writeObject(new Mensaje(Mensaje.ACTUALIZAR_TATETI,mrm));		// Debo actualizar ambas interfaces
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void actualizarTateti(MensajeRespuestaMovimiento msg) {
		try {
			out.writeObject(new Mensaje(Mensaje.ACTUALIZAR_TATETI,msg));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*private void enviarActualizacion(Mensaje msg) { EN DESUSO
		try {
			out.writeObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	*/		
	
	private void enviarMensajeChatTaTeTi(MensajeChat msgChat) {
		ClientHandler client = ChatServer.getInstance().getHandlerList().get(msgChat.getDestinatario());
		client.enviarMensajeChatTaTeTi(user, msgChat.getTexto());
	}

	public void enviarMensajeChatTaTeTi(String emisor, String texto){
		try{
			out.writeObject(new Mensaje(Mensaje.ENVIAR_MENSAJE_TATETI, new MensajeChat(emisor,texto)));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void finPartida(MensajeEstadoJuego msg){	//se ejecuta en el handler del origen
		String destino = msg.getGanador();	//obtengo el destino
		ClientHandler handlerDestino = ChatServer.getInstance().getHandlerList().get(destino);
		if(msg.isHayEmpate()){
			handlerDestino.finalizarPartida(Mensaje.EMPATE,user);
			//Loggeo en consola
			ChatServer.getInstance().logearEvento("Server :: Resultado partida " + user + " - " + destino + ": empate");
		}
		if(msg.isHayGanador()){
			handlerDestino.finalizarPartida(Mensaje.GANADOR,user);
			//actualizo DB
			dataAccess = DataAccess.getInstance();
			//origen pierde		(Ante duda, ver diagrama de "secuencia"; si se le puede decir asi)
			UserMetaData userMeta = dataAccess.getUserByUsername(user);
			dataAccess.addDefeat(userMeta);
			System.out.println("Defeat agregado a " + user);
			//destino gana
			userMeta = dataAccess.getUserByUsername(msg.getGanador());
			dataAccess.addVictory(userMeta);
			System.out.println("Victory agregado a " + msg.getGanador());
			//Loggeo en consola
			ChatServer.getInstance().logearEvento("Server :: Resultado partida " + user + " - " + destino + ": gano " + msg.getGanador());
		}
			
	}
	
	public void finalizarPartida(int idMensaje, String rival){	//se ejecuta en el handler del destinatario
		try{
			out.writeObject(new Mensaje(idMensaje,rival));
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void enviarPuntuacion(HashMap<String,Integer> puntuacion)
	{
		try{
			out.writeObject(new Mensaje(Mensaje.PUNTUACION,puntuacion));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	// Fin TODO Diego
	// Fin: TATETI


	// Inicio: GRUPOS
	private void crearGrupo(MensajeGrupo mensajeGrupo) {
		ChatServer.getInstance().crearGrupo(mensajeGrupo);
	}

	private void obtenerGrupos(String userName) {
		ChatServer.getInstance().actualizarGrupos(userName);
	}

	private void enviarMensajeGrupo(MensajeGrupo mensajeGrupo) {
		ChatServer.getInstance().enviarMensajeGrupo(mensajeGrupo);
	}

	private void enviarMensajeUsuarioEnGrupo(MensajeGrupo mensajeGrupo) {
		ChatServer.getInstance().enviarMensajeUsuarioEnGrupo(mensajeGrupo);
	}

	private void cerrarGrupo(MensajeGrupo mensajeGrupo) {
		ChatServer.getInstance().cerrarGrupo(mensajeGrupo);
	}

	private void buscarGrupos() {
		try {
			out.writeObject(new Mensaje(Mensaje.OBTENER_GRUPOS, ChatServer.getInstance().getGrupos()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void solicitarUnionGrupo(MensajeSolicitudGrupo mensaje) {
		ChatServer.getInstance().solicitarUnionGrupo(mensaje);
	}

	private void aceptarSolicitudUnionGrupo(MensajeSolicitudGrupo mensaje) {
		ChatServer.getInstance().aceptarSolicitudUnionGrupo(mensaje);
	}

	public void enviarSolicitudUnionGrupo(int tipoMensaje, MensajeSolicitudGrupo mensaje) {
		try {
			out.writeObject(new Mensaje(tipoMensaje, mensaje));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Fin: GRUPOS

}
