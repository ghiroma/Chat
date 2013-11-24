package interfaces.cliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class ClienteRequerirIngresoChatGrupal extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClienteRequerirIngresoChatGrupal frame = new ClienteRequerirIngresoChatGrupal();
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
	public ClienteRequerirIngresoChatGrupal() {
		setTitle("Unirse");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 215, 131);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(40, 36, 128, 20);
		contentPane.add(comboBox);
		
		JLabel lblUnirseAlGrupo = new JLabel("ELEGIR UN GRUPO AL QUE UNIRSE");
		lblUnirseAlGrupo.setHorizontalAlignment(SwingConstants.CENTER);
		lblUnirseAlGrupo.setBounds(16, 11, 175, 14);
		contentPane.add(lblUnirseAlGrupo);
		
		JButton btnNewButton = new JButton("Unirme");
		btnNewButton.setBounds(60, 67, 89, 23);
		contentPane.add(btnNewButton);
	}
}
