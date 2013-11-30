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
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import server.ChatServer;

public class Penalizacion extends JFrame {

	private static final long serialVersionUID = -7324300375798355667L;

	private JPanel contentPane;
	private JTextField textTiempo;
	private JTextField textField;
	private JLabel lblNotificacion;

	/**
	 * Create the frame.
	 */
	public Penalizacion(final String nombreUsuario) {
		setTitle("Penalizaci\u00F3n");	//te amo unicode
		/* Icono del frame */
		ImageIcon img = new ImageIcon(getClass().getResource("icon.png"));
		setIconImage(img.getImage());
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 423, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblTiempo = new JLabel("Tiempo (horas)");
		lblTiempo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblMotivo = new JLabel("Motivo");
		lblMotivo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		textTiempo = new JTextField();
		textTiempo.setColumns(10);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JButton btnPenalizar = new JButton("Penalizar");
		btnPenalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textTiempo.getText().equals("") && !textField.getText().equals("")) {
					try {
						int horas = Integer.valueOf(textTiempo.getText());
						ChatServer.getInstance().penalizar(nombreUsuario, horas, textField.getText());
						dispose();
					} catch(NumberFormatException ex) {
						lblNotificacion.setText("<html>"+ "El campo 'HORAS' debe ser numerico" +"</html>");
						lblNotificacion.setForeground(Color.RED);
					}
				} else {
					lblNotificacion.setText("<html>"+ "Todos los campos son obligatorios" +"</html>");
					lblNotificacion.setForeground(Color.RED);
				}
			}
		});
		
		JLabel lblPenalizacin = new JLabel("Penalización : ");
		lblPenalizacin.setFont(new Font("Tahoma", Font.PLAIN, 26));
		
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
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblTiempo)
								.addComponent(lblMotivo))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(textField, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
								.addComponent(textTiempo, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(21)
							.addComponent(lblPenalizacin, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblUsuario, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNotificacion, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
							.addComponent(btnPenalizar, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblPenalizacin, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUsuario, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(14)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTiempo)
						.addComponent(textTiempo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblMotivo)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnPenalizar, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNotificacion, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}

}
