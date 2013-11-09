package interfaces.cliente;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

public class ClienteInicial extends JFrame {

	private static final long serialVersionUID = 9163791121149343849L;

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ClienteInicial() {
		setTitle("Chat Principal");
		setResizable(false);

		// Modificar esto cuando se vaya a trabajar bien, que datos se lleva y que datos se trae//
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		DefaultListModel asd = new DefaultListModel();
		asd.addElement("1");
		asd.addElement("2");
		list.setModel(asd);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(11, 87, 205, 171);
		contentPane.add(list,0);

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
					//TODO: controlar que las ventanas no esten abiertas previamente.
					ClienteConversacion nuevaConversacion = new ClienteConversacion();
					nuevaConversacion.setTitle((String)lista.getSelectedValue());
					nuevaConversacion.setVisible(true);
				}
				
			}
		});
		btnIniciarChat.setBounds(283, 160, 114, 23);
		contentPane.add(btnIniciarChat);
	}
}
