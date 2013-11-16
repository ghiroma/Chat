package interfaces.cliente;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.ChatClient;

import common.UserMetaData;

public class ClienteDatosPersonales extends JFrame {

	private static final long serialVersionUID = 1597995466151011550L;

	private JPanel contentPane;
	private JLabel lblValidez;
	private JTextField txtUserName;
	private JTextField txtNombre;
	private JTextField txtEmail;
	private JTextField txtTel;
	private JPasswordField txtPass;
	private JPasswordField txtPass2;

	/**
	 * Create the frame.
	 */
	public ClienteDatosPersonales() {
		setTitle("Modificaci\u00F3n de datos del Cliente");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 618, 344);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNombreDeUsuario = new JLabel("Nombre de Usuario :");
		lblNombreDeUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNombreDeUsuario.setBounds(31, 144, 122, 14);
		contentPane.add(lblNombreDeUsuario);

		JLabel lblDatosPersonales = new JLabel("DATOS PERSONALES");
		lblDatosPersonales.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatosPersonales.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblDatosPersonales.setBounds(146, 11, 313, 77);
		contentPane.add(lblDatosPersonales);

		JLabel lblNombre = new JLabel("Nombre y apellido :");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNombre.setBounds(31, 169, 122, 14);
		contentPane.add(lblNombre);

		JLabel lblEmail = new JLabel("Email :");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEmail.setBounds(324, 194, 122, 14);
		contentPane.add(lblEmail);

		JLabel lblTelfono = new JLabel("Tel\u00E9fono :");
		lblTelfono.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTelfono.setBounds(31, 194, 122, 14);
		contentPane.add(lblTelfono);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a : ");
		lblContrasea.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblContrasea.setBounds(31, 219, 122, 14);
		contentPane.add(lblContrasea);

		JLabel lblRepetirContrasea = new JLabel("Repetir Contrase\u00F1a :");
		lblRepetirContrasea.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblRepetirContrasea.setBounds(324, 219, 122, 14);
		contentPane.add(lblRepetirContrasea);

		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalGlue.setBounds(10, 99, 574, 12);
		contentPane.add(horizontalGlue);

		txtUserName = new JTextField();
		txtUserName.setEditable(false);
		txtUserName.setBounds(188, 142, 113, 20);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);

		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(188, 167, 381, 20);
		contentPane.add(txtNombre);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(456, 192, 113, 20);
		contentPane.add(txtEmail);

		txtTel = new JTextField();
		txtTel.setColumns(10);
		txtTel.setBounds(188, 192, 113, 20);
		contentPane.add(txtTel);

		txtPass = new JPasswordField();
		txtPass.setColumns(10);
		txtPass.setBounds(188, 217, 113, 20);
		contentPane.add(txtPass);

		txtPass2 = new JPasswordField();
		txtPass2.setColumns(10);
		txtPass2.setBounds(456, 217, 113, 20);
		contentPane.add(txtPass2);

		lblValidez = new JLabel();
		lblValidez.setBounds(10, 244, 314, 61);
		contentPane.add(lblValidez);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(!txtNombre.getText().equals("") && !txtEmail.getText().equals("") && !txtTel.getText().equals("")) {
					if(verificarPassword(txtPass.getPassword(),txtPass2.getPassword())) {
						ChatClient.getInstance().modificarUsuario(txtNombre.getText(), txtEmail.getText(),
								txtTel.getText(), txtPass.getText());
						dispose();
					} else {
						lblValidez.setText("<html>"+ "El campo password esta vacio o no coinciden" +"</html>");
						lblValidez.setForeground(Color.RED);
					}
				} else {
					lblValidez.setText("<html>"+ "Todos los campos son obligatorios" +"</html>");
					lblValidez.setForeground(Color.RED);
				}
			}
		});
		btnGuardar.setBounds(335, 267, 89, 23);
		contentPane.add(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(480, 267, 89, 23);
		contentPane.add(btnCancelar);

		this.cargarDatos();

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

	private void cargarDatos() {
		UserMetaData user = ChatClient.getInstance().getUsuarioLogeado();
		txtUserName.setText(user.getUser());
		txtNombre.setText(user.getApyn());
		txtTel.setText(user.getTelefono());
		txtEmail.setText(user.getMail());		
	}

}
