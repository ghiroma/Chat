package cliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;

public class ClienteInicial extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClienteInicial frame = new ClienteInicial();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClienteInicial() {
		setTitle("Chat Principal");
		setResizable(false);

		// Modificar esto cuando se vaya a trabajar bien, qué datos se lleva y
		// qué datos se trae//
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// Si es necesario que se cierre toda la aplicación o no//

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
				ClienteAgragarAmigo agregarAmigos = new ClienteAgragarAmigo();
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
				dispose();
			}
		});
		btnDesconectar.setBounds(289, 0, 145, 46);
		contentPane.add(btnDesconectar);

		JList list = new JList();
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(10, 92, 184, 160);
		contentPane.add(list);

		JLabel lblAmigosOnline = new JLabel("Amigos Online");
		lblAmigosOnline.setBounds(11, 67, 183, 21);
		contentPane.add(lblAmigosOnline);

		JButton btnIniciarChat = new JButton("Iniciar Chat");
		btnIniciarChat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// Inicializo una nueva Conversación//

				ClienteConversacion nuevaConversación = new ClienteConversacion();
				nuevaConversación.setVisible(true);
			}
		});
		btnIniciarChat.setBounds(283, 160, 114, 23);
		contentPane.add(btnIniciarChat);
	}
}
