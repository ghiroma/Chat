package interfaces.servidor;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class InformacionUsuario extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InformacionUsuario frame = new InformacionUsuario();
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
	public InformacionUsuario() {
		setTitle("Informaci\u00F3n de Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 327, 246);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblDatosDelUsuario = new JLabel("Datos del usuario");
		lblDatosDelUsuario.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblEdad = new JLabel("Edad:");
		lblEdad.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblFechaDeNacimiento = new JLabel("Fecha de Nacimiento:");
		lblFechaDeNacimiento.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblNombreUsuario = new JLabel("Nombre Ejemplo");
		
		JLabel lblApellidoUsuario = new JLabel("Apellido Ejemplo");
		
		JLabel lblEdadUsuario = new JLabel("25");
		
		JLabel lblFechaNacimientoUsuario = new JLabel("10/10/1989");
		
		JLabel lblEmailUsuario = new JLabel("ejemplo@ejemplo.com");
		
		JButton btnCerrar = new JButton("Cerrar");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblEmail, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
						.addComponent(lblFechaDeNacimiento, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
						.addComponent(lblEdad, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
						.addComponent(lblApellido, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
						.addComponent(lblNombre, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNombreUsuario, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblApellidoUsuario, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEdadUsuario, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFechaNacimientoUsuario, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEmailUsuario, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(72)
					.addComponent(lblDatosDelUsuario)
					.addContainerGap(229, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(132, Short.MAX_VALUE)
					.addComponent(lblUsuario)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblUsername)
					.addGap(24))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(239, Short.MAX_VALUE)
					.addComponent(btnCerrar)
					.addGap(22))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsuario)
						.addComponent(lblUsername))
					.addGap(18)
					.addComponent(lblDatosDelUsuario)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNombre)
						.addComponent(lblNombreUsuario))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblApellido)
						.addComponent(lblApellidoUsuario))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEdad)
						.addComponent(lblEdadUsuario))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFechaDeNacimiento)
						.addComponent(lblFechaNacimientoUsuario))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEmail)
						.addComponent(lblEmailUsuario))
					.addPreferredGap(ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
					.addComponent(btnCerrar))
		);
		contentPane.setLayout(gl_contentPane);
	}

}
