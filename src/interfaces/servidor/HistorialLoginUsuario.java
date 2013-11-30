package interfaces.servidor;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import server.ChatServer;

public class HistorialLoginUsuario extends JFrame {

	private static final long serialVersionUID = -5169780461060638717L;

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public HistorialLoginUsuario(String nombreUsuario) {
		setTitle("Historial de Login");
		/* Icono del frame */
		ImageIcon img = new ImageIcon(getClass().getResource("icon.png"));
		setIconImage(img.getImage());
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblHistorialDeLogin = new JLabel("Historial de login del usuario:");
		lblHistorialDeLogin.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblNombreusuario = new JLabel(nombreUsuario);
		lblNombreusuario.setForeground(Color.BLUE);
		lblNombreusuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreusuario.setFont(new Font("Tahoma", Font.ITALIC, 18));

		JTextArea historialLogin = new JTextArea();
		historialLogin.setEditable(false);
		for(String linea : ChatServer.getInstance().obtenerHistorialLoginUsuario(nombreUsuario)) {
			historialLogin.append(linea + "\n");
		}
		JScrollPane scrollPane = new JScrollPane(historialLogin);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblHistorialDeLogin)
							.addGap(29)
							.addComponent(lblNombreusuario, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnCerrar)))
					.addContainerGap(28, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblHistorialDeLogin)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(2)
							.addComponent(lblNombreusuario)))
					.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnCerrar)
							.addContainerGap())))
		);
		contentPane.setLayout(gl_contentPane);
	}

}
