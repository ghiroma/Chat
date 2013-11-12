package interfaces.cliente;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import common.FriendStatus;

import client.ChatClient;
import javax.swing.SwingConstants;

public class ClienteInicial extends JFrame {

	private static final long serialVersionUID = 9163791121149343849L;

	private JPanel contentPane;
	private JLabel lblNotificacion;


	/**
	 * Create the frame.
	 */
	public ClienteInicial() {
		setTitle("Chat Principal");
		setResizable(false);

		// Modificar esto cuando se vaya a trabajar bien, que datos se lleva y que datos se trae//
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Si es necesario que se cierre toda la aplicacion o no//

		setBounds(100, 100, 450, 301);
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
				//TODO FRONT : ver como vamos a recargar la lista de amigos conectados luego de haber mandado invitaciones en la pantalla
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
				dispose();
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

		JButton btnIniciarChat = new JButton("Iniciar Chat");
		btnIniciarChat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// Inicializo una nueva Conversacion//
				JList lista=(JList)contentPane.getComponent(0);
				if(lista.getSelectedValue() != null) {
					//TODO FRONT (FERNANDO): controlar que las ventanas no esten abiertas previamente
					ClienteConversacion nuevaConversacion = new ClienteConversacion((String)lista.getSelectedValue());
					nuevaConversacion.setTitle((String)lista.getSelectedValue());
					nuevaConversacion.setVisible(true);
					lblNotificacion.setText("");
				} else {
					lblNotificacion.setText("<html>"+ "Debe seleccionar un amigo" +"</html>");
					lblNotificacion.setForeground(Color.RED);
				}
				
			}
		});
		btnIniciarChat.setBounds(281, 77, 114, 42);
		contentPane.add(btnIniciarChat);
		
		lblNotificacion = new JLabel("");
		lblNotificacion.setHorizontalAlignment(SwingConstants.CENTER);
		lblNotificacion.setBounds(229, 130, 205, 116);
		contentPane.add(lblNotificacion);
	}

	private DefaultListModel obtenerListaAmigos() {
		DefaultListModel modelAmigos = new DefaultListModel();
		List<FriendStatus> amigos = ChatClient.getInstance().getAmigos();
		for(FriendStatus amigo : amigos) {
			//TODO FRONT : filtrar los amigos q estan conectados
			modelAmigos.addElement(amigo.getUsername());
		}
		return modelAmigos;
	}

}
