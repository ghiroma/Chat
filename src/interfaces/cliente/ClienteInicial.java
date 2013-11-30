package interfaces.cliente;

import interfaces.grupos.ClienteModCrearChatBroad;
import interfaces.grupos.ClienteModSalaDeChat;
import interfaces.tateti.Blackboard;
import interfaces.tateti.InterfazTateti;
import interfaces.tateti.InvitacionJuego;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.ChatClient;

import common.FriendStatus;
import common.Mensaje;
import common.MensajeInvitacion;
import common.MensajeJuego;
import common.MensajeMovimiento;
import common.MensajePartida;
import common.MensajeSolicitudGrupo;

public class ClienteInicial extends JFrame {

	private static final long serialVersionUID = 9163791121149343849L;

	private JPanel contentPane;
	private JLabel lblNotificacion;
	private DefaultListModel modelAmigos;
	private String user;

	/**
	 * Create the frame.
	 */
	public ClienteInicial() {
		user = ChatClient.getInstance().getUsuarioLogeado().getUser();
		setTitle("Menu principal - " + ChatClient.getInstance().getUsuarioLogeado().getUser().toUpperCase());
		setResizable(false);

		/* Seteo del cierre del Frame */
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				JFrame frame = (JFrame) e.getSource();

				int result = JOptionPane.showConfirmDialog(frame,
						"Esta seguro que quiere salir?", "Salir",
						JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION)
					ChatClient.getInstance().close();
			}
		});

		setBounds(100, 100, 442, 301);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Agregar Amigos");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Instancio un nuevo panel para agregar los Amigos//
				ClienteAgregarAmigo agregarAmigos = new ClienteAgregarAmigo();
				agregarAmigos.setVisible(true);
			}
		});
		btnNewButton.setBounds(1, 0, 145, 46);
		contentPane.add(btnNewButton);

		JButton btnModificarDatos = new JButton("Modificar Datos");
		btnModificarDatos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Creo una nueva Instancia de Modificar Datos
				ClienteDatosPersonales modDatosCliente = new ClienteDatosPersonales();
				modDatosCliente.setVisible(true);
			}
		});
		btnModificarDatos.setBounds(145, 0, 145, 46);
		contentPane.add(btnModificarDatos);

		JButton btnDesconectar = new JButton("Desconectar");
		btnDesconectar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ChatClient.getInstance().close();
			}
		});
		btnDesconectar.setBounds(289, 0, 145, 46);
		contentPane.add(btnDesconectar);

		JList list = new JList();
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(11, 87, 205, 171);

		list.setModel(this.obtenerListaAmigos());
		contentPane.add(list, 0);

		JLabel lblAmigosOnline = new JLabel("Amigos Online");
		lblAmigosOnline.setBounds(11, 67, 183, 21);
		contentPane.add(lblAmigosOnline);

		JButton btnIniciarChat = new JButton("Chatear");
		btnIniciarChat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// Inicializo una nueva Conversacion//
				String nombreUsuario = (String) ((JList)contentPane.getComponent(0)).getSelectedValue();
				if (nombreUsuario != null) {
					getNuevaConversacion(nombreUsuario);
				} else {
					lblNotificacion.setText("<html>" + "Debe seleccionar un amigo" + "</html>");
					lblNotificacion.setForeground(Color.RED);
				}

			}
		});
		btnIniciarChat.setBounds(221, 57, 95, 42);
		contentPane.add(btnIniciarChat);

		
		// TODO Diego
		/* Boton Iniciar Juego */
		JButton btnIniciarJuego = new JButton("Iniciar Juego");
		btnIniciarJuego.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { // Version Nico
				String nombreUsuario=(String)((JList)contentPane.getComponent(0)).getSelectedValue();
				if(nombreUsuario != null && ChatClient.getInstance().getMapaTateti().size() < 3) {
					ChatClient.getInstance().invitarAmigoAJugar(nombreUsuario);
				} else {
					lblNotificacion.setText("<html>"+ "Error: seleccione a un amigo. Recuerde que la maxima cantidad de partidas activas es 3." +"</html>");
					lblNotificacion.setForeground(Color.RED);
				}
			}
		});
		btnIniciarJuego.setBounds(221, 125, 95, 42);
		contentPane.add(btnIniciarJuego);
		
		lblNotificacion = new JLabel("");
		lblNotificacion.setHorizontalAlignment(SwingConstants.CENTER);
		lblNotificacion.setBounds(221, 182, 205, 80);
		contentPane.add(lblNotificacion);

		JButton btnIngresarGrupo = new JButton("Grupos");
		btnIngresarGrupo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ClienteRequerirIngresoChatGrupal clienteReqIngChatGrupal = new ClienteRequerirIngresoChatGrupal();
				clienteReqIngChatGrupal.setVisible(true);
			}
		});
		btnIngresarGrupo.setBounds(326, 57, 95, 42);
		contentPane.add(btnIngresarGrupo);
		
		JButton btnCrearGrupo = new JButton("Crear Grupo");
		btnCrearGrupo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ClienteModCrearChatBroad clienteModCrearChatBroad = new ClienteModCrearChatBroad();
				clienteModCrearChatBroad.setVisible(true);
			}
		});
		btnCrearGrupo.setBounds(326, 110, 95, 42);
		contentPane.add(btnCrearGrupo);
		
		JButton btnPuntuacion = new JButton("Ver Puntuaje");
		btnPuntuacion.setBounds(221, 164, 95, 42);
		btnPuntuacion.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0){
				ChatClient.getInstance().obtenerPuntuacion();
			}
		});
		contentPane.add(btnPuntuacion);
	}

	public ClienteConversacion getNuevaConversacion(String nombreUsuario) {
		ClienteConversacion nuevaConversacion = ChatClient.getInstance().getMapaConversaciones().get(nombreUsuario);

		if (nuevaConversacion == null) {
			nuevaConversacion = new ClienteConversacion(nombreUsuario, Mensaje.MENSAJE_INDIVIDUAL);
			nuevaConversacion.setVisible(true);
			lblNotificacion.setText("");
			ChatClient.getInstance().getMapaConversaciones().put(nombreUsuario, nuevaConversacion);
		} else {
			nuevaConversacion.setVisible(true);
			nuevaConversacion.toFront();
		}
		return nuevaConversacion;
	}

	public ClienteConversacion getNuevaConversacion(String nombreUsuario, String texto) {

		ClienteConversacion nuevaConversacion = ChatClient.getInstance().getMapaConversaciones().get(nombreUsuario);

		if (nuevaConversacion == null) {
			nuevaConversacion = new ClienteConversacion(nombreUsuario, Mensaje.MENSAJE_INDIVIDUAL);
			nuevaConversacion.setVisible(true);

			lblNotificacion.setText("");
			ChatClient.getInstance().getMapaConversaciones().put(nombreUsuario, nuevaConversacion);
		} else {
			nuevaConversacion.setVisible(true);
			nuevaConversacion.toFront();
		}
		nuevaConversacion.mostrarMensajeDeAmigo(texto);
		return nuevaConversacion;
	}

	private DefaultListModel obtenerListaAmigos() {
		modelAmigos = new DefaultListModel();
		List<FriendStatus> amigos = ChatClient.getInstance().getAmigos();
		for (FriendStatus amigo : amigos) {
			if(amigo.getEstado() == 1)
				modelAmigos.addElement(amigo.getUsername());
		}
		return modelAmigos;
	}

	public void friendStatusChanged(String user, int estado) {
		if (estado==1) {
			if(!modelAmigos.contains(user))
				modelAmigos.addElement(user);
		} else {
			modelAmigos.removeElement(user);
		}
	}

	public void mostrarAlerta(String txtAlerta) {
		lblNotificacion.setText("<html>" + txtAlerta + "</html>");
		lblNotificacion.setForeground(Color.BLUE);
	}

	public void mostrarPopUpInvitacion(MensajeInvitacion msgInvitacion) {
		AlertaSolicitudAmistad popUp = new AlertaSolicitudAmistad(msgInvitacion);
		popUp.setVisible(true);
		popUp.toFront();

	}


	// Inicio: TATETI
	public void mostrarPopUpInvitacionJuego(Mensaje msg) {//Version Nico origen = solicitante, destino = invitado
		InvitacionJuego inv = new InvitacionJuego();
		inv.setUsuarioOrigen((String)msg.getCuerpo());	//msg = "INVITACION_JUEGO, solicitante"
		inv.setUsuarioDestino(user);
		inv.setJLblInformacion(inv.getUsuarioOrigen() + " desea jugar con usted " + inv.getUsuarioDestino());
		inv.setVisible(true);
		inv.toFront();
	}

	public void iniciarTaTeTi(MensajeJuego msg){ //Version Nico; inicializa el TaTeTi de la nueva partida
		/* Inicializacion del TaTeTi*/
		InterfazTateti tateti = new InterfazTateti();
		//Seteo rival
		String rival = msg.getRival();
		tateti.setRival(rival);
		tateti.setUser(user);
		if(msg.getProxTurno().equals(user)){
			tateti.setId(1);			
		}

		else{
			tateti.setId(0);
		}

		
		/* Inicializacion del Blackboard */
		Blackboard pizarra = new Blackboard();
		//Seteo de pizarra
		pizarra.setUser(user);
		pizarra.setRival(rival);
		pizarra.setProxturno(msg.getProxTurno());
		tateti.setPizarra(pizarra);
		pizarra.setId(tateti.getId());
		tateti.updateTurno();
		System.out.println("User: " + user+ " Turno: " + pizarra.getProxturno());
		
		// Agrego al mapaPartidas la partida recien creada
		ChatClient.getInstance().getMapaPizarra().put(rival, pizarra);
		// Agrego al mapaTateti la interfaz recien creada
		ChatClient.getInstance().getMapaTateti().put(rival, tateti);
		tateti.setTitle("Usuario: " + user + " Rival: " + rival);
		tateti.setVisible(true);
		tateti.toFront();
	}
	
	public void mostrarTateti(Mensaje msg) {	//Version Nico: En desuso.
		/*InterfazTateti tateti = new InterfazTateti();
		String jugador1 = ((MensajePartida)msg.getCuerpo()).getJugador1();
		String jugador2 = ((MensajePartida)msg.getCuerpo()).getJugador2();
		String proxTurno = ((MensajePartida)msg.getCuerpo()).getProxTurno();
		tateti.setUser(jugador1);
		tateti.setRival(jugador2);
		Blackboard pizarra = new Blackboard();
		pizarra.setJugador1(jugador1);
		pizarra.setJugador2(jugador2);
		pizarra.setProxturno(proxTurno);
		// Agrego al mapaPartidas la partida recien creada
		ChatClient.getInstance().getMapaPizarra().put(jugador2, pizarra);
		// Agrego al mapaTateti la interfaz recien creada
		ChatClient.getInstance().getMapaTateti().put(jugador2, tateti);
		tateti.setTitle(jugador1 + " vs " + jugador2);
		tateti.setVisible(true);
		tateti.toFront();*/
	}

	public void getNuevaConversacionTateti(String nombreUsuario, String texto) {
		InterfazTateti tateti = ChatClient.getInstance().getMapaTateti().get(nombreUsuario);
		try{
			if(tateti == null)
				throw new Exception("No se encuentra la interfaz tateti: " + nombreUsuario);
			tateti.mostrarMensajeDeRival(texto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void mostrarPuntuacion(HashMap<String,Integer>puntuacion)
	{
		//TODO convertir hashmap a matriz mejor.
		String[][] punt = new String[1][3];
		punt[0][0] = puntuacion.get("Ganados").toString();
		punt[0][1] = puntuacion.get("Empatados").toString();
		punt[0][2] = puntuacion.get("Perdidos").toString();
		PuntuacionTateti tateti = new PuntuacionTateti(punt);
		tateti.setVisible(true);
	}
	// Fin: TATETI


	// Inicio: GRUPOS
	public ClienteConversacion getNuevaConversacion(int mensajeGrupo, String nombreUsuario, String texto) {

		ClienteConversacion nuevaConversacion = ChatClient.getInstance().getMapaConversaciones().get(nombreUsuario);

		if (nuevaConversacion == null) {
			nuevaConversacion = new ClienteConversacion(nombreUsuario, Mensaje.MENSAJE_GRUPAL);
			nuevaConversacion.setVisible(true);

			lblNotificacion.setText("");
			ChatClient.getInstance().getMapaConversaciones().put(nombreUsuario, nuevaConversacion);
		} else {
			nuevaConversacion.setVisible(true);
			nuevaConversacion.toFront();
		}
		nuevaConversacion.mostrarMensajeDeAmigo(texto);
		return nuevaConversacion;
	}

	public ClienteConversacion cerrarGrupo(String nombreUsuario, String texto) {

		ClienteConversacion nuevaConversacion = ChatClient.getInstance().getMapaConversaciones().get(nombreUsuario);

		if (nuevaConversacion == null) {
			nuevaConversacion = new ClienteConversacion(nombreUsuario, Mensaje.MENSAJE_GRUPAL);
			nuevaConversacion.setVisible(true);

			lblNotificacion.setText("");
			ChatClient.getInstance().getMapaConversaciones().put(nombreUsuario, nuevaConversacion);
		} else {
			nuevaConversacion.setVisible(true);
			nuevaConversacion.toFront();
		}
		nuevaConversacion.mostrarMensajeDeAmigo(texto);
		nuevaConversacion.inhabilitarVentana();
		return nuevaConversacion;
	}

	public ClienteConversacion bannearGrupo(String nombreUsuario, String texto) {

		ClienteConversacion nuevaConversacion = ChatClient.getInstance().getMapaConversaciones().get(nombreUsuario);

		if (nuevaConversacion == null) {
			nuevaConversacion = new ClienteConversacion(nombreUsuario, Mensaje.MENSAJE_GRUPAL);
			nuevaConversacion.setVisible(true);

			lblNotificacion.setText("");
			ChatClient.getInstance().getMapaConversaciones().put(nombreUsuario, nuevaConversacion);
		} else {
			nuevaConversacion.setVisible(true);
			nuevaConversacion.toFront();
		}
		nuevaConversacion.mostrarMensajeDeAmigo(texto);
		nuevaConversacion.inhabilitarVentana();
		return nuevaConversacion;
	}

	public ClienteConversacion desbannearUsuarioGrupo(String nombreUsuario, String texto) {

		ClienteConversacion nuevaConversacion = ChatClient.getInstance().getMapaConversaciones().get(nombreUsuario);

		if (nuevaConversacion == null) {
			nuevaConversacion = new ClienteConversacion(nombreUsuario, Mensaje.MENSAJE_GRUPAL);
			nuevaConversacion.setVisible(true);

			lblNotificacion.setText("");
			ChatClient.getInstance().getMapaConversaciones().put(nombreUsuario, nuevaConversacion);
		} else {
			nuevaConversacion.setVisible(true);
			nuevaConversacion.toFront();
		}
		nuevaConversacion.mostrarMensajeDeAmigo(texto);
		nuevaConversacion.habilitarVentana();
		return nuevaConversacion;
	}

	public void setNuevoMensajeGrupo(String grupo, String mensaje) {
		ClienteModSalaDeChat salaGrupo=ChatClient.getInstance().getMapaGrupos().get(grupo);
		salaGrupo.mostrarMensajeDeAmigo(mensaje);
	}

	public void mostrarPopUpSolicitudGrupo(MensajeSolicitudGrupo msgSolicitud) {
		AlertaSolicitudUnion popUp = new AlertaSolicitudUnion(msgSolicitud);
		popUp.setVisible(true);
		popUp.toFront();
	}
	
	public DefaultListModel getListaAmigos(){
		return modelAmigos;
	}
	// Fin: GRUPOS

}
