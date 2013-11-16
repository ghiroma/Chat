package server;

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
			/* Inicio escucha al cliente */
			while (client.isConnected()) {
				// se traba aca hasta que hay mensaje
				msg = (Mensaje) in.readObject();

				//Switch con cada constante de mensaje ENVIADA POR EL CLIENTE que ejecute un metodo privado.
				switch (msg.getId()) {
				case Mensaje.MODIFICACION_USUARIO:
					modificacionUsuario((UserMetaData)msg.getCuerpo());
					break;
				case Mensaje.ENVIAR_MENSAJE:
					enviarMensajeChat((MensajeChat)msg.getCuerpo());
					break;
				case Mensaje.BUSCAR_USUARIO:
					buscarUsuarios((String)msg.getCuerpo());
					break;
				case Mensaje.INVITAR_USUARIO:
					invitarUsuario((MensajeInvitacion)msg.getCuerpo());
					break;
				case Mensaje.ACEPTACION_INVITACION_AMIGO:
					aceptacionInvitacionAmistad((MensajeInvitacion)msg.getCuerpo());
					break;
				}

			}
		} catch (SocketException se) {
			ChatServer.getInstance().logearEvento("El usuario " + user + " se ha desconectado");
		} catch (IOException ioe) {
			ioe.printStackTrace(System.err);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Exception no manejada en ClientHandler");
			// removeUser(out);
		} finally {
			// TODO Remover el usuario de la lista.
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

	private void invitarUsuario(MensajeInvitacion msgInvitacion) {
		ClientHandler client = ChatServer.getInstance().getHandlerList().get(msgInvitacion.getInvitado());
		client.recibirInvitacion(msgInvitacion);
	}

	/* Metodos de update (Son llamados desde los ClientEventListener)*/
	public void friendStatusUpdate(String user, int estado){
		try {  
			out.writeObject(new Mensaje(Mensaje.CAMBIO_ESTADO, new FriendStatus(user, estado)));
		} catch(IOException e) {
			e.printStackTrace();
			System.err.println("Error al enviar cambio de estado al cliente.");
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
