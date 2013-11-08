package interfaces.servidor;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1501608729613907406L;

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public Principal() {
		setTitle("Servidor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 708, 483);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JList listConectados = new JList();
		
		JLabel lblNewLabel = new JLabel("Conectados");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel lblDesconectados = new JLabel("Desconectados");
		lblDesconectados.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JList listDesconectados = new JList();
		
		JList listLogEventos = new JList();
		
		JLabel lblLogDeEventos = new JLabel("Eventos");
		lblLogDeEventos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JButton btnAlerta = new JButton("Enviar Alerta General");
		btnAlerta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Alerta alertaView = new Alerta();
				alertaView.setVisible(true);
			}
		});
		
		JButton btnCerrarServer = new JButton("Cerrar Server");
		btnCerrarServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(listLogEventos, GroupLayout.PREFERRED_SIZE, 369, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(btnAlerta, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnCerrarServer, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
								.addGap(49))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblLogDeEventos)
								.addPreferredGap(ComponentPlacement.RELATED))))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addComponent(listConectados, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
							.addComponent(lblNewLabel))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDesconectados)
								.addComponent(listDesconectados, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(1)
									.addComponent(btnAlerta, GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
								.addComponent(btnCerrarServer, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblLogDeEventos)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(listLogEventos, GroupLayout.PREFERRED_SIZE, 322, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(listConectados, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
							.addGap(23)
							.addComponent(lblDesconectados)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(listDesconectados, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)))
					.addGap(52))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
