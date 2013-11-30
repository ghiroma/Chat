package interfaces.servidor;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import server.ChatServer;
import common.UserMetaData;

public class InformacionUsuario extends JFrame {

	private static final long serialVersionUID = 3905985356784741611L;

	private JPanel contentPane;
	private JTextField txtUserName;
	private JTextField txtNombre;
	private JTextField txtEmail;
	private JTextField txtTel;

	/**
	 * Create the frame.
	 */
	public InformacionUsuario(String nombreUsuario) {
		setTitle("Informaci\u00F3n de Usuario");
		/* Icono del frame */
		ImageIcon img = new ImageIcon(getClass().getResource("icon.png"));
		setIconImage(img.getImage());
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 618, 312);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNombreDeUsuario = new JLabel("Nombre de Usuario :");
		lblNombreDeUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNombreDeUsuario.setBounds(31, 144, 122, 14);
		contentPane.add(lblNombreDeUsuario);

		JLabel lblDatosPersonales = new JLabel("INFORMACION DEL USUARIO");
		lblDatosPersonales.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatosPersonales.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblDatosPersonales.setBounds(109, 11, 391, 77);
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

		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalGlue.setBounds(10, 99, 574, 12);
		contentPane.add(horizontalGlue);

		txtUserName = new JTextField();
		txtUserName.setEditable(false);
		txtUserName.setBounds(188, 142, 113, 20);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);

		txtNombre = new JTextField();
		txtNombre.setEditable(false);
		txtNombre.setColumns(10);
		txtNombre.setBounds(188, 167, 381, 20);
		contentPane.add(txtNombre);

		txtEmail = new JTextField();
		txtEmail.setEditable(false);
		txtEmail.setColumns(10);
		txtEmail.setBounds(456, 192, 113, 20);
		contentPane.add(txtEmail);

		txtTel = new JTextField();
		txtTel.setEditable(false);
		txtTel.setColumns(10);
		txtTel.setBounds(188, 192, 113, 20);
		contentPane.add(txtTel);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnCerrar.setBounds(480, 233, 89, 23);
		contentPane.add(btnCerrar);

		this.cargarDatos(nombreUsuario);
	}

	private void cargarDatos(String nombreUsuario) {
		UserMetaData user = ChatServer.getInstance().obtenerInfoUsuario(nombreUsuario);
		txtUserName.setText(user.getUser());
		txtNombre.setText(user.getApyn());
		txtTel.setText(user.getTelefono());
		txtEmail.setText(user.getMail());		
	}

}
