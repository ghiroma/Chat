package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.EventObject;

import javax.swing.event.EventListenerList;

import common.FriendStatus;
import common.Mensaje;

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
			client.setSoTimeout(10000); // 10 seg, el alive tira cada 5 una senial
			/* evento de inicio sesion */
			dispatchEvent(new StatusChangedEvent(this, user, estado));
			/* Inicio escucha al cliente */
			while (client.isConnected()) {
				msg = (Mensaje) in.readObject(); // se traba ac� hasta que hay
													// mensaje

				// TODO: switch con cada constante de mensaje que ejecute un
				// metodo privado.
				switch (msg.getId()) {
				case Mensaje.ENVIAR_MENSAJE:
					break;
				case Mensaje.BUSCAR_USUARIO:
					break;
				case Mensaje.INVITAR_USUARIO:
					break;
				}

			}
		}

		catch (SocketTimeoutException e) {
			// TODO connection lost!
		}

		catch (IOException ioe) {
			ioe.printStackTrace(System.err);
		}

		catch (Exception e) {
			System.err.println(e.getMessage());
			// removeUser(out);
		}

		finally {
			// TODO Remover el usuario de la lista.
		}
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
