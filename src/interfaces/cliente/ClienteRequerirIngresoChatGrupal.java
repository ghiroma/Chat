package interfaces.cliente;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.ChatClient;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClienteRequerirIngresoChatGrupal extends JFrame {

	private static final long serialVersionUID = -7365500936671806946L;

	private JPanel contentPane;
	private JComboBox cbxGrupos;

	/**
	 * Create the frame.
	 */
	public ClienteRequerirIngresoChatGrupal() {
		setTitle("Unirse");
		/* Icono del frame */
		ImageIcon img = new ImageIcon(getClass().getResource("icon.png"));
		setIconImage(img.getImage());
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 215, 131);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		cbxGrupos = new JComboBox();
		cbxGrupos.setBounds(40, 36, 128, 20);
		contentPane.add(cbxGrupos);

		JLabel lblUnirseAlGrupo = new JLabel("ELEGIR UN GRUPO AL QUE UNIRSE");
		lblUnirseAlGrupo.setHorizontalAlignment(SwingConstants.CENTER);
		lblUnirseAlGrupo.setBounds(16, 11, 175, 14);
		contentPane.add(lblUnirseAlGrupo);

		JButton btnNewButton = new JButton("Unirme");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String grupoSeleccionado = (String)cbxGrupos.getSelectedItem();
				if(grupoSeleccionado != null) {
					ChatClient.getInstance().solicitarUnionGrupo(grupoSeleccionado);
					dispose();
				}
			}
		});
		btnNewButton.setBounds(60, 67, 89, 23);
		contentPane.add(btnNewButton);

		// Cargar los grupos que estan en el Server//
		// Pido al client Handler que lo haga //
		List<String> grupos = ChatClient.getInstance().actualizarGrupos();

		for (String s : grupos) {
			cbxGrupos.addItem(s);
		}
	}
}
