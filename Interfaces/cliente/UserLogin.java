package cliente;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UserLogin {

	private JFrame frmIngreso;
	private JTextField textField;
	private JPasswordField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserLogin window = new UserLogin();
					window.frmIngreso.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmIngreso = new JFrame();
		frmIngreso.setTitle("Ingreso");
		frmIngreso.setBounds(100, 100, 450, 134);
		frmIngreso.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmIngreso.getContentPane().setLayout(null);
		frmIngreso.setResizable(false);
		
		JButton btnNewButton = new JButton("Ingresar");
		btnNewButton.addKeyListener(new KeyAdapter() {
			
		});
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			
			//Acá hay que validar los campos contra la base de datos y decidir si dejar ingresar o no,
				//como estamos trabajando con una maqueta, abrimos la ventana correspondiente//
				ClienteInicial nuevoCliente = new ClienteInicial();
				nuevoCliente.setVisible(true);
			}
		});
		btnNewButton.setBounds(304, 26, 117, 23);
		frmIngreso.getContentPane().add(btnNewButton);
		
		JButton btnNuevoUsuario = new JButton("Nuevo Usuario");
		btnNuevoUsuario.setBounds(304, 60, 117, 23);
		frmIngreso.getContentPane().add(btnNuevoUsuario);
		
		JLabel lblNombreDeUsuario = new JLabel("Nombre de Usuario :");
		lblNombreDeUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNombreDeUsuario.setBounds(12, 29, 135, 14);
		frmIngreso.getContentPane().add(lblNombreDeUsuario);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a :");
		lblContrasea.setHorizontalAlignment(SwingConstants.RIGHT);
		lblContrasea.setVerticalAlignment(SwingConstants.TOP);
		lblContrasea.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblContrasea.setBounds(35, 64, 89, 14);
		frmIngreso.getContentPane().add(lblContrasea);
		
		textField = new JTextField();
		textField.setBounds(153, 27, 103, 20);
		frmIngreso.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JPasswordField();
		textField_1.setColumns(10);
		textField_1.setBounds(153, 61, 103, 20);
		frmIngreso.getContentPane().add(textField_1);
	}
}
