package server;

import interfaces.tateti.InvitacionJuego;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.EventObject;

import javax.swing.event.EventListenerList;

import common.FriendStatus;
import common.Mensaje;
import common.MensajeChat;
import common.MensajeInvitacion;
import common.MensajePartida;
import common.MensajeRespuestaInvitacion;
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

	/* Constructores */
	public ClientHandler(Socket client, String user, ObjectInputStream in, ObjectOutputStream out) {
		this.listenerList = new EventListenerList();
		this.client = client;
		this.in = in;
		this.out = out;
		this.user = user;
		estado = 1;

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
					ChatServer.getInstance().logearEvento("Server :: " + user + " acepto amistad a: " + ((MensajeInvitacion)msg.getCuerpo()).getInvitado());
					break;
				case Mensaje.OBTENER_USUARIO:
					obtenerUsuario((String)msg.getCuerpo());
					break;
				case Mensaje.CERRAR_SESION:
					ChatServer.getInstance().logearEvento("Server :: " + user + " cerro sesion.");
					cerrarSesion();
					break;
				// TODO Diego
				case Mensaje.INVITACION_JUEGO:
					enviarInvitacionJuego((MensajeInvitacion)msg.getCuerpo());
					ChatServer.getInstance().logearEvento("Server :: " + user + " invito a jugar a " + ((MensajeInvitacion)msg.getCuerpo()).getInvitado() + ".");
					break;
				case Mensaje.RESPUESTA_INVITACION_JUEGO:
					String invitado = ((MensajeRespuestaInvitacion)msg.getCuerpo()).getInvitado();
					String solicitante = ((MensajeRespuestaInvitacion)msg.getCuerpo()).getSolicitante();
					ChatServer.getInstance().logearEvento("Server :: " + invitado + (((MensajeRespuestaInvitacion)msg.getCuerpo()).isAcepto()?" acepto ":" no acepto ") + " jugar con " + solicitante);
					if(((MensajeRespuestaInvitacion)msg.getCuerpo()).isAcepto()) {
						// TODO ejecutarPartida();
						iniciarPartida(solicitante,invitado);
						System.out.println("Ejecutar partida");
					}					
					break;
				case Mensaje.INICIO_PARTIDA:
					//ejecutarJuego()
					break;
				//	
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
			ChatServer.getInstance().removerUsuario(user);
			// Actualizo en la DB el estado
			UserMetaData userMeta = DataAccess.getInstance().getUserByUsername(user);
			userMeta.setConectado(0);
			DataAccess.getInstance().modifyUser(userMeta);
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
		client.enviarMensajeChat(user, msgChat.getTexto());
	}
	
	// TODO Diego
	private void enviarInvitacionJuego(MensajeInvitacion inv) {
		ClientHandler client = ChatServer.getInstance().getHandlerList().get(inv.getInvitado());
		client.recibirInvitacionJuego(new Mensaje(Mensaje.INVITACION_JUEGO,inv));
	}
	
	private void recibirInvitacionJuego(Mensaje inv) {
		try {
			out.writeObject(inv);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void iniciarPartida(String s, String i) {
		try {
			out.writeObject(new Mensaje(Mensaje.INICIO_PARTIDA,new MensajePartida(s,i)));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// Fin TODO Diego
	
	private void invitarUsuario(MensajeInvitacion msgInvitacion) {
		ClientHandler client = ChatServer.getInstance().getHandlerList().get(msgInvitacion.getInvitado());
		client.recibirInvitacion(msgInvitacion);
	}

	private void cerrarSesion(){
		try {
			in.close();
			out.close();
			client.close();
			/* Avisa a todos que se desconecta el user */
			dispatchEvent(new StatusChangedEvent(this, user, 0));
		} catch (IOException e) {
			System.err.println("Error al cerrar sesion de " + user);
		}
	}

	/* Metodos publicos */
	public void enviarAlerta(String textoAlerta){
		try {
			Mensaje msg=new Mensaje(Mensaje.ALERTA, textoAlerta);
			out.writeObject(msg);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void enviarMensajeChat(String emisor, String texto) {
		try {
			out.writeObject(new Mensaje(Mensaje.ENVIAR_MENSAJE, new MensajeChat(emisor, texto)));
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
	}

	/* Metodos de update (Son llamados desde los ClientEventListener)*/
	public void friendStatusUpdate(String user, int estado){
		try { 
			out.writeObject(new Mensaje(Mensaje.CAMBIO_ESTADO, new FriendStatus(user, estado)));
			
		}
		catch(SocketException e1)
		{
			
		}
		catch(IOException e) {
			e.printStackTrace();
			System.err.println("Error al enviar cambio de estado al cliente.");
		}		
	}

	/* Desconectar al cliente */
	public void close() {
		// TODO: almacenar la informacion, y desconectar al cliente del servidor.
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

}
