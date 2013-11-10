package interfaces.servidor;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import server.ChatServer;
import javax.swing.SwingConstants;
import java.awt.Color;

public class Alerta extends JFrame {

	private static final long serialVersionUID = 4836049167772662766L;

	private JPanel contentPane;
	private JTextField textAlerta;
	private JLabel lblNotificacion;

	/**
	 * Create the frame.
	 */
	public Alerta(final String nombreUsuario) {
		setTitle("Alerta");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 235);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblMensajeDeAlerta = new JLabel("Mensaje de alerta :");
		lblMensajeDeAlerta.setFont(new Font("SansSerif", Font.BOLD, 20));
		
		textAlerta = new JTextField();
		textAlerta.setColumns(10);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textAlerta.getText().equals("")) {
					ChatServer.getInstance().enviarAlerta(textAlerta.getText(), nombreUsuario);
					dispose();					
				} else {
					lblNotificacion.setText("<html>"+ "El texto a enviar no puede estar vacio" +"</html>");
					lblNotificacion.setForeground(Color.RED);
				}
			}
		});
		
		JLabel lblUsuario = new JLabel(nombreUsuario);
		lblUsuario.setForeground(Color.BLUE);
		lblUsuario.setFont(new Font("Tahoma", Font.ITALIC, 16));
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblNotificacion = new JLabel();
		lblNotificacion.setHorizontalAlignment(SwingConstants.CENTER);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(textAlerta, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(lblNotificacion, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
									.addComponent(btnEnviar)))
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblMensajeDeAlerta)
							.addPreferredGap(ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
							.addComponent(lblUsuario, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
							.addGap(40))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblMensajeDeAlerta)
						.addComponent(lblUsuario, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(14)
					.addComponent(textAlerta, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnEnviar)
						.addComponent(lblNotificacion, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}
