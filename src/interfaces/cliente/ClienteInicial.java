package interfaces.cliente;

import interfaces.grupos.ClienteModCrearChatBroad;
import interfaces.tateti.InvitacionJuego;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

public class ClienteInicial extends JFrame {

	private static final long serialVersionUID = 9163791121149343849L;

	private JPanel contentPane;
	private JLabel lblNotificacion;

	/**
	 * Create the frame.
	 */
	public ClienteInicial() {

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
				// TODO FRONT : ver como vamos a recargar la lista de amigos
				// conectados luego de haber mandado invitaciones en la pantalla
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
			public void mouseClicked(MouseEvent e) {
				String nombreUsuario=(String)((JList)contentPane.getComponent(0)).getSelectedValue();
				if(nombreUsuario != null) {
					ChatClient.getInstance().invitarAmigoAJugar(nombreUsuario);
				} else {
					lblNotificacion.setText("<html>"+ "Debe seleccionar un amigo" +"</html>");
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
		btnCrearGrupo.setBounds(326, 125, 95, 42);
		contentPane.add(btnCrearGrupo);
	}

	public ClienteConversacion getNuevaConversacion(String nombreUsuario) {
		ClienteConversacion nuevaConversacion = ChatClient.getInstance().getMapaConversaciones().get(nombreUsuario);

		if (nuevaConversacion == null) {
			nuevaConversacion = new ClienteConversacion(nombreUsuario);
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
			nuevaConversacion = new ClienteConversacion(nombreUsuario);
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
		DefaultListModel modelAmigos = new DefaultListModel();
		List<FriendStatus> amigos = ChatClient.getInstance().getAmigos();
		for (FriendStatus amigo : amigos) {
			if(amigo.getEstado() == 1)
				modelAmigos.addElement(amigo.getUsername());
		}
		return modelAmigos;
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

	
	// TODO Diego
	public void mostrarPopUpInvitacionJuego(Mensaje msg) {
		InvitacionJuego inv = new InvitacionJuego();
		inv.setUsuarioOrigen(((MensajeInvitacion)msg.getCuerpo()).getSolicitante());
		inv.setUsuarioDestino(((MensajeInvitacion)msg.getCuerpo()).getInvitado());
		inv.setJLblInformacion(inv.getUsuarioOrigen() + " desea jugar con usted " + inv.getUsuarioDestino());
		inv.setVisible(true);
		inv.toFront();
	}
	//
	
	public void friendStatusChanged(String user, int estado){
		//TODO: agregar/quitar de la lista de conectados en el front-end el user recibido por parametro.


	}
}
