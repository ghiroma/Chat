package interfaces.cliente;

import java.awt.Color;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import common.FriendStatus;

import client.ChatClient;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

public class ClienteAgregarAmigo extends JFrame {

	private static final long serialVersionUID = 4891039026246519212L;

	private JPanel contentPane;
	private JTextField textField;
	private JList list;
	private JButton btnNewButton;
	private JLabel lblNotificacion;
	private JButton btnCerrar;

	/**
	 * Create the frame.
	 */
	public ClienteAgregarAmigo() {
		setTitle("Invitar Amigos");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 309);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblBuscarAmigos = new JLabel("Buscar Amigos :");
		lblBuscarAmigos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblBuscarAmigos.setBounds(26, 27, 125, 19);
		contentPane.add(lblBuscarAmigos);

		textField = new JTextField();
		textField.setToolTipText("Texto de busqueda");
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setText("<texto de busqueda>");
		textField.setBounds(140, 23, 184, 30);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent arg0) {
				textField.setText("");
			}
			@Override
			public void focusLost(FocusEvent arg0) {
			}
		});

		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(26, 69, 233, 197);
		contentPane.add(list, 0);

		btnNewButton = new JButton("Enviar Invitaci\u00F3n");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JList lista=(JList)contentPane.getComponent(0);
				if(lista.getSelectedValue() != null) {
					ChatClient.getInstance().invitarAmigo((String)lista.getSelectedValue());
				} else {
					lblNotificacion.setText("<html>"+ "Debe seleccionar un contacto" +"</html>");
					lblNotificacion.setForeground(Color.RED);
				}
			}
		});
		btnNewButton.setBounds(277, 64, 137, 40);
		contentPane.add(btnNewButton);
		
		lblNotificacion = new JLabel("");
		lblNotificacion.setHorizontalAlignment(SwingConstants.CENTER);
		lblNotificacion.setBounds(281, 166, 133, 100);
		contentPane.add(lblNotificacion);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textField.getText().trim().equals("")) {
					list.setModel(filtrarContactos());
				} else {
					lblNotificacion.setText("<html>"+ "Debe completar el texto a buscar" +"</html>");
					lblNotificacion.setForeground(Color.RED);
				}
			}
		});
		btnBuscar.setBounds(334, 23, 89, 30);
		contentPane.add(btnBuscar);
		
		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCerrar.setBounds(277, 115, 137, 40);
		contentPane.add(btnCerrar);
	}

	private DefaultListModel filtrarContactos() {
		DefaultListModel listaContactos = new DefaultListModel();
		List<String> contactos = ChatClient.getInstance().buscarAmigoPorTexto(textField.getText());
		boolean esAmigo = false;
		for(String contacto : contactos) {
			if(!ChatClient.getInstance().getUsuarioLogeado().getUser().equalsIgnoreCase(contacto))
			{
				for(FriendStatus friend : ChatClient.getInstance().getAmigos())
					if(friend.getUsername().equalsIgnoreCase(contacto))
						esAmigo = true;
			if(esAmigo == false)
				listaContactos.addElement(contacto);
			}
			esAmigo=false;
		}
		return listaContactos;
	}
	

}
