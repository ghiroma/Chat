package interfaces.cliente;

import java.awt.Component;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import client.ChatClient;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClienteNuevoUsuario extends JFrame {

	private static final long serialVersionUID = -821695818153018559L;

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Create the frame.
	 */
	public ClienteNuevoUsuario() {
		setResizable(false);
		setTitle("Chat - Nuevo Usuario");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 527, 428);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBounds(0, 0, 521, 400);
		contentPane.add(panel);

		JLabel label = new JLabel("Nombre de Usuario :");
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label.setBounds(31, 144, 122, 14);
		panel.add(label);

		JLabel lblNuevoUsuario = new JLabel("NUEVO USUARIO");
		lblNuevoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblNuevoUsuario.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNuevoUsuario.setBounds(188, 11, 313, 77);
		panel.add(lblNuevoUsuario);

		JLabel label_2 = new JLabel("Nombre :");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_2.setBounds(31, 169, 122, 14);
		panel.add(label_2);

		JLabel label_3 = new JLabel("Apellido : ");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_3.setBounds(31, 194, 122, 14);
		panel.add(label_3);

		JLabel label_4 = new JLabel("email :");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_4.setBounds(31, 220, 122, 14);
		panel.add(label_4);

		JLabel label_5 = new JLabel("Direcci\u00F3n :");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_5.setBounds(31, 246, 122, 14);
		panel.add(label_5);

		JLabel label_6 = new JLabel("Tel\u00E9fono :");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_6.setBounds(31, 271, 122, 14);
		panel.add(label_6);

		JLabel label_7 = new JLabel("Contrase\u00F1a : ");
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_7.setBounds(31, 296, 122, 14);
		panel.add(label_7);

		JLabel label_8 = new JLabel("Repetir Contrase\u00F1a :");
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_8.setBounds(31, 321, 122, 14);
		panel.add(label_8);

		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalGlue.setBounds(10, 99, 491, 0);
		panel.add(horizontalGlue);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(188, 142, 113, 20);
		panel.add(textField);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(188, 167, 113, 20);
		panel.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(188, 192, 113, 20);
		panel.add(textField_2);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(188, 218, 113, 20);
		panel.add(textField_3);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(188, 244, 113, 20);
		panel.add(textField_4);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(188, 269, 113, 20);
		panel.add(textField_5);

		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(188, 294, 113, 20);
		panel.add(passwordField);

		passwordField_1 = new JPasswordField();
		passwordField_1.setColumns(10);
		passwordField_1.setBounds(188, 319, 113, 20);
		panel.add(passwordField_1);

		JButton button = new JButton("Guardar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		button.setBounds(255, 356, 89, 23);
		panel.add(button);

		JButton button_1 = new JButton("Cancelar");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		button_1.setBounds(376, 356, 89, 23);
		panel.add(button_1);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(10, 11, 158, 77);
		panel.add(panel_1);

		// Boton de verificacion de existencia de nombre de usuario, asi no se repite
		JButton btnNewButton = new JButton("Verificar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ChatClient.getInstance().verificarNombreUsuario(textField.getText())){
					//TODO terminar PABLO
				} else {
					//TODO terminar PABLO					
				}
			}
		});
		btnNewButton.setBounds(376, 141, 89, 23);
		panel.add(btnNewButton);
	}
}
