package servidor;

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
import javax.swing.JList;
import javax.swing.JButton;

public class HistorialLoginUsuario extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HistorialLoginUsuario frame = new HistorialLoginUsuario();
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
	public HistorialLoginUsuario() {
		setTitle("Historial de Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblHistorialDeLogin = new JLabel("Historial de login del usuario:");
		lblHistorialDeLogin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblNombreusuario = new JLabel("NombreUsuario");
		lblNombreusuario.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JList listHistorial = new JList();
		
		JButton btnCerrar = new JButton("Cerrar");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblHistorialDeLogin)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNombreusuario))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(listHistorial, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnCerrar)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblHistorialDeLogin)
						.addComponent(lblNombreusuario))
					.addPreferredGap(ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(listHistorial, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnCerrar)
							.addContainerGap())))
		);
		contentPane.setLayout(gl_contentPane);
	}

}
