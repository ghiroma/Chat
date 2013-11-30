package interfaces.cliente;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import common.UserMetaData;
import client.ChatClient;

public class ClienteNuevoUsuario extends JFrame {

	private static final long serialVersionUID = -821695818153018559L;

	private JPanel contentPane;
	private JLabel lblValidez;
	private JTextField txtUsername;
	private JTextField txtNyA;
	private JTextField txtEmail;
	private JTextField txtTel;
	private JPasswordField txtPass1;
	private JPasswordField txtPass2;

	/**
	 * Create the frame.
	 */
	public ClienteNuevoUsuario() {
		setResizable(false);
		setTitle("Chat - Nuevo Usuario");
		/* Icono del frame */
		ImageIcon img = new ImageIcon(getClass().getResource("icon.png"));
		setIconImage(img.getImage());
		
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 596, 376);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBounds(0, 0, 590, 348);
		contentPane.add(panel);

		JLabel label = new JLabel("Nombre de Usuario :");
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label.setBounds(31, 144, 122, 14);
		panel.add(label);

		JLabel lblNuevoUsuario = new JLabel("NUEVO USUARIO");
		lblNuevoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblNuevoUsuario.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNuevoUsuario.setBounds(140, 11, 313, 77);
		panel.add(lblNuevoUsuario);

		JLabel lblNombreYApellido = new JLabel("Nombre y Apellido :");
		lblNombreYApellido.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNombreYApellido.setBounds(31, 179, 122, 14);
		panel.add(lblNombreYApellido);

		JLabel lblEmail = new JLabel("Email :");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEmail.setBounds(31, 212, 122, 14);
		panel.add(lblEmail);

		JLabel label_6 = new JLabel("Tel\u00E9fono :");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_6.setBounds(311, 212, 122, 14);
		panel.add(label_6);

		JLabel label_7 = new JLabel("Contrase\u00F1a : ");
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_7.setBounds(31, 240, 122, 14);
		panel.add(label_7);

		JLabel label_8 = new JLabel("Repetir Contrase\u00F1a :");
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_8.setBounds(311, 240, 122, 14);
		panel.add(label_8);

		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalGlue.setBounds(10, 99, 570, 12);
		panel.add(horizontalGlue);

		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.setBounds(188, 142, 113, 20);
		panel.add(txtUsername);

		txtNyA = new JTextField();
		txtNyA.setColumns(10);
		txtNyA.setBounds(188, 177, 368, 20);
		panel.add(txtNyA);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(188, 210, 113, 20);
		panel.add(txtEmail);

		txtTel = new JTextField();
		txtTel.setColumns(10);
		txtTel.setBounds(443, 210, 113, 20);
		panel.add(txtTel);

		txtPass1 = new JPasswordField();
		txtPass1.setColumns(10);
		txtPass1.setBounds(188, 239, 113, 20);
		panel.add(txtPass1);

		txtPass2 = new JPasswordField();
		txtPass2.setColumns(10);
		txtPass2.setBounds(443, 238, 113, 20);
		panel.add(txtPass2);

		JButton button = new JButton("Guardar");
		button.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(ChatClient.getInstance().verificarNombreUsuario(txtUsername.getText())){
					if(!txtNyA.getText().equals("") && !txtEmail.getText().equals("") && !txtTel.getText().equals("")) {
						if(verificarPassword(txtPass1.getPassword(),txtPass2.getPassword())) {
							UserMetaData user = new UserMetaData(txtUsername.getText(), txtPass1.getText(), txtNyA.getText(), 
									txtEmail.getText(), txtTel.getText(), new Date(), new Date(), 0);
							ChatClient.getInstance().altaNuevoUsuario(user);
							dispose();
						} else {
							lblValidez.setText("<html>"+ "El campo password esta vacio o no coinciden" +"</html>");
							lblValidez.setForeground(Color.RED);
						}
					} else {
						lblValidez.setText("<html>"+ "Todos los campos son obligatorios" +"</html>");
						lblValidez.setForeground(Color.RED);
					}
				} else {
					lblValidez.setText("<html>"+ "Nombre vacio o no disponible" +"</html>");
					lblValidez.setForeground(Color.RED);
					txtUsername.setText("");
				}
			}
		});
		button.setBounds(322, 294, 89, 23);
		panel.add(button);

		JButton button_1 = new JButton("Cancelar");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button_1.setBounds(446, 294, 89, 23);
		panel.add(button_1);

		lblValidez = new JLabel();
		lblValidez.setBounds(31, 268, 269, 67);
		panel.add(lblValidez);
	
		JButton btnNewButton = new JButton("Verificar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Boton de verificacion de existencia de nombre de usuario, asi no se repite
				if(ChatClient.getInstance().verificarNombreUsuario(txtUsername.getText())){
					lblValidez.setText("Nombre Disponible");
					lblValidez.setForeground(Color.GREEN);
				} else {
					lblValidez.setText("Nombre no disponible");
					lblValidez.setForeground(Color.RED);
					txtUsername.setText("");
				}
			}
		});
		btnNewButton.setBounds(341, 141, 89, 23);
		panel.add(btnNewButton);
		
	}

	private boolean verificarPassword(char[] pass1, char[] pass2) {
		if(pass1.length != 0 && pass2.length != 0 && pass1.length==pass2.length){
			boolean esDistinto = false;
			for(int i = 0; i<pass1.length; i++) {
				if(pass1[i] != pass2[i])
					esDistinto = true; 
			}
			return !esDistinto;
		}
		return false;
	}
}
