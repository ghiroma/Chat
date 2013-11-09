package interfaces.cliente;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ClienteAgregarAmigo extends JFrame {

	private static final long serialVersionUID = 4891039026246519212L;

	private JPanel contentPane;
	private JTextField textField;
	private JList list;
	private JButton btnNewButton;

	/**
	 * Create the frame.
	 */
	public ClienteAgregarAmigo() {
		setTitle("Invitar Amigos");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblBuscarAmigos = new JLabel("Buscar Amigos :");
		lblBuscarAmigos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblBuscarAmigos.setBounds(26, 27, 125, 19);
		contentPane.add(lblBuscarAmigos);

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setText("<texto de busqueda>");
		textField.setBounds(279, 28, 155, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(26, 82, 233, 179);
		contentPane.add(list);

		btnNewButton = new JButton("Enviar Invitaci\u00F3n");
		btnNewButton.setBounds(297, 238, 137, 23);
		contentPane.add(btnNewButton);
	}

}
