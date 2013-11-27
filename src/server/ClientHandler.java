package server;

import interfaces.tateti.Blackboard;
import interfaces.tateti.InterfazTateti;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.event.EventListenerList;

import common.FriendStatus;
import common.Mensaje;
import common.MensajeChat;
import common.MensajeConsulta;
import common.MensajeGrupo;
import common.MensajeInvitacion;
import common.MensajeMovimiento;
import common.MensajePartida;
import common.MensajePizarra;
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
	private ArrayList<Blackboard> partidas= new ArrayList<Blackboard>();
	private ArrayList<InterfazTateti> interfaces = new ArrayList<InterfazTateti>();
	private int cantpartidas;


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
				case Mensaje.INVITACION_JUEGO:
					enviarInvitacionJuego((MensajeInvitacion)msg.getCuerpo());
					ChatServer.getInstance().logearEvento("Server :: " + user + " invito a jugar a " + ((MensajeInvitacion)msg.getCuerpo()).getInvitado() + ".");
					break;
				case Mensaje.RESPUESTA_INVITACION_JUEGO:
					String invitado = ((MensajeRespuestaInvitacion)msg.getCuerpo()).getInvitado();
					String solicitante = ((MensajeRespuestaInvitacion)msg.getCuerpo()).getSolicitante();
					ChatServer.getInstance().logearEvento("Server :: " + invitado + (((MensajeRespuestaInvitacion)msg.getCuerpo()).isAcepto()?" acepto ":" no acepto ") + " jugar con " + solicitante);
					if(((MensajeRespuestaInvitacion)msg.getCuerpo()).isAcepto()) {
						// Verifico que no este jugando mas de 3 partidas
						if(partidas.size() < 3) {
							MensajeConsulta mc = new MensajeConsulta(solicitante,invitado,false,this.cantpartidas);
							consultarCantidadPartidasRival(new Mensaje(Mensaje.CANTIDAD_PARTIDAS_VALIDA,mc));							
						} else {
							// TODO enviar mensaje a usuario de que no puede jugar ya que llego al limite de partidas
						}
					}					
					break;
				case Mensaje.INICIO_PARTIDA:
					// Debo enviar la partida creada a mi rival
					agregarInterfaz(msg);
					break;			
				case Mensaje.ENVIO_PARTIDA:
					agregarInterfaz(msg);
					break;
				case Mensaje.RESPUESTA_CONSULTA_PARTIDAS:
					if(partidas.size() < 3) {
						// Cuando respondo con mi cantidad de partidas para crear la pizzara yo soy el invitado
						String inv = ((MensajeConsulta)msg.getCuerpo()).getSolicitante();
						String sol = ((MensajeConsulta)msg.getCuerpo()).getInvitado();
						int id = ((MensajeConsulta)msg.getCuerpo()).getIdPizzaraSolicitante();
						MensajePartida mp = new MensajePartida(sol,inv,null);
						iniciarPartida(new Mensaje(Mensaje.INICIO_PARTIDA,mp));
						MensajePizarra mpi = new MensajePizarra(sol,inv,crearPizarraDePartida(sol, inv, this.cantpartidas,id));
						// Le paso la id de la partida a crear
						enviarPizarraARival(mpi);
					} else {
						// TODO rival no puede jugar ya que se encuentra al limite de partidas permitidas
					}
					break;
				case Mensaje.RESPUESTA_PIZARRA:
					vincularPizarra(msg);
					break;
				case Mensaje.MOVIMIENTO:
					// Debo actualizarlo en ambas interfaces
					if(verificarMovimiento(msg))
						actualizarMovimiento(msg);
					break;
				case Mensaje.RESPUESTA_ACTUALIZACION_PIZARRA:
					actualizarMovimiento(msg);
					break;
				case Mensaje.ENVIAR_MENSAJE_TATETI:
					enviarMensajeChatTaTeTi((MensajeChat)msg.getCuerpo());
					ChatServer.getInstance().logearEvento("Server :: "+user + " envio mensaje en el tateti a "+ ((MensajeChat)msg.getCuerpo()).getDestinatario());
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
				case Mensaje.OBTENER_GRUPOS:
					obtenerGrupos((String) msg.getCuerpo());
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

	/* Metodos publicos */
	public void cerrarSesion() {
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
		dispatchEvent(new StatusChangedEvent(this, user, 1));
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


	// Inicio: TATETI
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
		
	private void consultarCantidadPartidasRival(Mensaje msg) {
		ClientHandler client = ChatServer.getInstance().getHandlerList().get(((MensajeConsulta)msg.getCuerpo()).getInvitado());
		client.enviarConsultaPartidas(msg);
	}
		
	private void enviarConsultaPartidas(Mensaje msg) {
		try {
			out.writeObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	private void iniciarPartida(Mensaje msg) {
		try {
			ClientHandler client = ChatServer.getInstance().getHandlerList().get(((MensajePartida)msg.getCuerpo()).getJugador2());
			client.enviarPartida(new Mensaje(
					Mensaje.ENVIO_PARTIDA,new MensajePartida(
							((MensajePartida)msg.getCuerpo()).getJugador2(),(
									(MensajePartida)msg.getCuerpo()).getJugador1(),null)));
			out.writeObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
		
	private void enviarPartida(Mensaje msg) {
		try {
			out.writeObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	private void agregarInterfaz(Mensaje msg) {
		MensajePartida mp = (MensajePartida)msg.getCuerpo();
		interfaces.add(mp.getPartida());
	}

	// Creo pizarra
	private Blackboard crearPizarraDePartida(String s, String i, int idPartidas, int idPartidai) {
		Blackboard pizarra = new Blackboard();
		pizarra.setJugador1(s);
		pizarra.setJugador2(i);
		pizarra.setIdBlackboard1(idPartidas);
		pizarra.setIdBlackboard2(idPartidai);
		this.cantpartidas++;
		partidas.add(pizarra);
		return pizarra;
	}

	// Vinculo pizarra del solicitante con la enviada por el invitado
	private void vincularPizarra(Mensaje msg) {
		Blackboard pizarra = ((MensajePizarra)msg.getCuerpo()).getPizarra();
		this.cantpartidas++;
		partidas.add(pizarra);	
	}

	private void enviarPizarraARival(MensajePizarra mp) {
		ClientHandler client = ChatServer.getInstance().getHandlerList().get(mp.getJugador2());
		client.recibirPizarra(new Mensaje(Mensaje.ENVIO_PIZARRA,mp));
	}

	private void recibirPizarra(Mensaje msg) {
		try {
			out.writeObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean verificarMovimiento(Mensaje msg) {
		Iterator<Blackboard> i = partidas.iterator();
		Blackboard pizarra = new Blackboard();
		MensajeMovimiento mm = (MensajeMovimiento) msg.getCuerpo();
		String jugador1 = mm.getJugador1();
		String jugador2 = mm.getJugador2();
		while(i.hasNext()) {
			Blackboard bb = (Blackboard) i.next();
			if((bb.getJugador1() == jugador1 && bb.getJugador2() == jugador2) || (bb.getJugador1() == jugador2 && bb.getJugador2() == jugador1)) {
				pizarra = bb;
			}
		}
		if(pizarra != null) {
			if(pizarra.inspect(mm.getX(), mm.getY(), pizarra.nroJugadas)) {
				return true;
			} 
		}
		return false;
	}

	private void actualizarMovimiento(Mensaje msg) {
		Iterator<Blackboard> i = partidas.iterator();
		Blackboard pizarra = new Blackboard();
		MensajeMovimiento mm = (MensajeMovimiento)msg.getCuerpo();
		String jugador1 = mm.getJugador1();
		String jugador2 = mm.getJugador2();
		JButton boton = mm.getBoton();
		// Encuentro pizarra 
		while(i.hasNext()) {
			Blackboard bb = (Blackboard) i.next();
			if((bb.getJugador1() == jugador1 && bb.getJugador2() == jugador2) || (bb.getJugador1() == jugador2 && bb.getJugador2() == jugador1)) {
				pizarra = bb;
			}
		}
		// Encuentro interfaz
		Iterator<InterfazTateti> j = interfaces.iterator();
		InterfazTateti tateti = new InterfazTateti();
		while(j.hasNext()) {
			InterfazTateti tt = (InterfazTateti) j.next();
			if((tt.getPlayer1() == jugador1 && tt.getPlayer2() == jugador2) || (tt.getPlayer1() == jugador2 && tt.getPlayer2() == jugador1)) {
				tateti = tt;
			}
		}
		// Actualizacion pizarra e interfaz de ClientHandler actual
		JLabel label1 = tateti.getJLabel2();
		JLabel label2 = tateti.getJLabel3();

		// Actualizo label de proximo turno
		setearIcono(label1,(pizarra.nroJugadas+1)%2);

		// Debo encontrar boton correspondiente en el tateti, 
		// comparo por referencia ya que lo me envian por mensaje es una referencia 
		// del boton que fue presionado de determinada interfaz que obtuve anteriormente
		if(tateti.getJButton1() == boton) {
			// Actualizo icono del boton
			setearIcono(boton,pizarra.nroJugadas);
		}
		if(tateti.getJButton2() == boton) {
			// Actualizo icono del boton
			setearIcono(boton,pizarra.nroJugadas);
		}
		if(tateti.getJButton3() == boton) {
			// Actualizo icono del boton
			setearIcono(boton,pizarra.nroJugadas);
		}
		if(tateti.getJButton4() == boton) {
			// Actualizo icono del boton
			setearIcono(boton,pizarra.nroJugadas);
		}
		if(tateti.getJButton5() == boton) {
			// Actualizo icono del boton
			setearIcono(boton,pizarra.nroJugadas);
		}
		if(tateti.getJButton6() == boton) {
			// Actualizo icono del boton
			setearIcono(boton,pizarra.nroJugadas);
		}
		if(tateti.getJButton7() == boton) {
			// Actualizo icono del boton
			setearIcono(boton,pizarra.nroJugadas);
		}
		if(tateti.getJButton8() == boton) {
			// Actualizo icono del boton
			setearIcono(boton,pizarra.nroJugadas);
		}
		if(tateti.getJButton9() == boton) {
			// Actualizo icono del boton
			setearIcono(boton,pizarra.nroJugadas);
		}

		// Actualizo pizarra
		int aux = pizarra.update(mm.getX(), mm.getY(), pizarra.nroJugadas);
		// TODO recordar que el update() de la clase blackboard devuelvo un entero corresponediente al resultado de la partida
		// 1 - Hay Ganador
		// 0 - Empate 
		// -1 - No paso nada
		if(aux != -1) {
			if(aux == 1){
				// 	Hay ganador
				label2.setText("Ganador: " + (pizarra.nroJugadas%2 == 0?"Player2":"Player1"));
			} else if(aux == 0) {
				// 	Hay empate
				label2.setText("Empate!");
			}
			label2.setVisible(true);
		}

		if(msg.getId() == Mensaje.RESPUESTA_ACTUALIZACION_PIZARRA) {
			// Debo mandar informacion necesaria al client handler del rival para que actualice su pizarra
			ClientHandler client = ChatServer.getInstance().getHandlerList().get(mm.getJugador2());
			client.enviarActualizacion(new Mensaje(Mensaje.ACTUALIZACION_PIZARRA,mm));
		}
	}

	private void enviarActualizacion(Mensaje msg) {
		try {
			out.writeObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}			

	public void setearIcono(Object obj, int id) {
		try {
			if(obj != null) {
				if(obj instanceof JLabel) {
					JLabel l = (JLabel) obj;
					if (id != 0)
						l.setIcon(new ImageIcon(getClass().getResource("cruzChica.png")));
					else
						l.setIcon(new ImageIcon(getClass().getResource("circuloChico.png")));
				} else if(obj instanceof JButton) {
					JButton b = (JButton) obj;
					if (id != 0)
						b.setIcon(new ImageIcon(getClass().getResource("cruzGrande.png")));
					else
						b.setIcon(new ImageIcon(getClass().getResource("circuloGrande.png")));
				}
			}
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
	}

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
	// Fin: GRUPOS

}
