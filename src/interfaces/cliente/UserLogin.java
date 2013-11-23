package interfaces.cliente;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import client.ChatClient;
import common.UserMetaData;

public class UserLogin {

	private JFrame frmIngreso;
	private JTextField textField;
	private JPasswordField textField_1;
	private JLabel lblValidez;

	public void mostrar() {
		frmIngreso.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public UserLogin() {
		frmIngreso = new JFrame();
		frmIngreso.setTitle("Ingreso");
		frmIngreso.setBounds(100, 100, 450, 157);
		frmIngreso.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmIngreso.getContentPane().setLayout(null);
		frmIngreso.setResizable(false);

		JButton btnNewButton = new JButton("Ingresar");
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if(!textField.getText().equals("") && textField_1.getPassword().length != 0){
					UserMetaData userData = new UserMetaData(textField.getText(), textField_1.getPassword());
					ClienteInicial nuevoCliente=ChatClient.getInstance().login(userData);
					if(nuevoCliente!=null){
						nuevoCliente.setVisible(true);
						frmIngreso.dispose();
					} else {
						lblValidez.setText("<html>"+ "La combinacion de usuario y password no se encuentra registrada o fue bloqueada" +"</html>");
						lblValidez.setForeground(Color.RED);
						textField.setText("");
						textField_1.setText("");
					}
				} else {
					lblValidez.setText("Los campos no pueden estar vacios");
					lblValidez.setForeground(Color.RED);
				}
			}
		});
		btnNewButton.setBounds(304, 26, 117, 23);
		frmIngreso.getContentPane().add(btnNewButton);

		JButton btnNuevoUsuario = new JButton("Nuevo Usuario");
		btnNuevoUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Instancio una nueva ventana de creacion de Creacion de nuevo usuario
				ClienteNuevoUsuario nuevoCliente = new ClienteNuevoUsuario();
				nuevoCliente.setVisible(true);
			}
		});
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
		textField_1.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyChar()=='\n')
				{
					if(!textField.getText().equals("") && textField_1.getPassword().length != 0){
						UserMetaData userData = new UserMetaData(textField.getText(), textField_1.getPassword());
						ClienteInicial nuevoCliente=ChatClient.getInstance().login(userData);
						if(nuevoCliente!=null){
							if(ChatClient.getInstance().getBanInfo().getDias()>0)
								//TODO imprimo mensaje de ban mas baninfo. Eliminar System.out
								System.out.println("Esta baneado.");
							else
							{
							nuevoCliente.setVisible(true);
							frmIngreso.dispose();
							}
						} else {
							lblValidez.setText("<html>"+ "La combinacion de usuario y password no se encuentra registrada o fue bloqueada" +"</html>");
							lblValidez.setForeground(Color.RED);
							textField.setText("");
							textField_1.setText("");
						}
					} else {
						lblValidez.setText("Los campos no pueden estar vacios");
						lblValidez.setForeground(Color.RED);
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		lblValidez = new JLabel();
		lblValidez.setForeground(Color.BLUE);
		lblValidez.setHorizontalAlignment(SwingConstants.CENTER);
		lblValidez.setText("Ingrese su usuario y contraseña o dese de alta, si no tiene usuario.");
		lblValidez.setBounds(10, 92, 411, 26);
		frmIngreso.getContentPane().add(lblValidez);
	}
	
}
