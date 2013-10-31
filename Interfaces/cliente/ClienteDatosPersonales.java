package cliente;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClienteDatosPersonales extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JPasswordField textField_6;
	private JPasswordField textField_7;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClienteDatosPersonales frame = new ClienteDatosPersonales();
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
	public ClienteDatosPersonales() {
		setTitle("Modificaci\u00F3n de datos del Cliente");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 527, 428);
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
		lblDatosPersonales.setBounds(188, 11, 313, 77);
		contentPane.add(lblDatosPersonales);

		JLabel lblNombre = new JLabel("Nombre :");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNombre.setBounds(31, 169, 122, 14);
		contentPane.add(lblNombre);

		JLabel lblApellido = new JLabel("Apellido : ");
		lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblApellido.setBounds(31, 194, 122, 14);
		contentPane.add(lblApellido);

		JLabel lblEmail = new JLabel("email :");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEmail.setBounds(31, 220, 122, 14);
		contentPane.add(lblEmail);

		JLabel lblDireccin = new JLabel("Direcci\u00F3n :");
		lblDireccin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDireccin.setBounds(31, 246, 122, 14);
		contentPane.add(lblDireccin);

		JLabel lblTelfono = new JLabel("Tel\u00E9fono :");
		lblTelfono.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTelfono.setBounds(31, 271, 122, 14);
		contentPane.add(lblTelfono);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a : ");
		lblContrasea.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblContrasea.setBounds(31, 296, 122, 14);
		contentPane.add(lblContrasea);

		JLabel lblRepetirContrasea = new JLabel("Repetir Contrase\u00F1a :");
		lblRepetirContrasea.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblRepetirContrasea.setBounds(31, 321, 122, 14);
		contentPane.add(lblRepetirContrasea);

		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalGlue.setBounds(10, 99, 491, 0);
		contentPane.add(horizontalGlue);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(188, 142, 113, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(188, 167, 113, 20);
		contentPane.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(188, 192, 113, 20);
		contentPane.add(textField_2);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(188, 218, 113, 20);
		contentPane.add(textField_3);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(188, 244, 113, 20);
		contentPane.add(textField_4);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(188, 269, 113, 20);
		contentPane.add(textField_5);

		textField_6 = new JPasswordField();
		textField_6.setColumns(10);
		textField_6.setBounds(188, 294, 113, 20);
		contentPane.add(textField_6);

		textField_7 = new JPasswordField();
		textField_7.setColumns(10);
		textField_7.setBounds(188, 319, 113, 20);
		contentPane.add(textField_7);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(255, 356, 89, 23);
		contentPane.add(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(376, 356, 89, 23);
		contentPane.add(btnCancelar);

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 11, 158, 77);
		contentPane.add(panel);
	}
}
