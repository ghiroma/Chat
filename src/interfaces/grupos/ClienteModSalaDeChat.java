package interfaces.grupos;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ClienteModSalaDeChat extends JFrame {

	private static final long serialVersionUID = 1313190352834111687L;

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public ClienteModSalaDeChat() {
		setTitle("Sala de Chat - Moderado");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 648, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(10, 53, 489, 367);
		contentPane.add(textPane);
		
		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.setBounds(509, 430, 113, 23);
		contentPane.add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(10, 431, 489, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(509, 53, 113, 367);
		contentPane.add(textPane_1);
		
		JLabel lblConextados = new JLabel("PARTICIPANTES");
		lblConextados.setHorizontalAlignment(SwingConstants.CENTER);
		lblConextados.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblConextados.setBounds(509, 28, 113, 14);
		contentPane.add(lblConextados);
		
		JLabel lblGrupoDeChat = new JLabel("<Nombre del Grupo de Chat - Mod : <NombreMod>>");
		lblGrupoDeChat.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblGrupoDeChat.setHorizontalAlignment(SwingConstants.CENTER);
		lblGrupoDeChat.setBounds(10, 23, 489, 23);
		contentPane.add(lblGrupoDeChat);
	}
}
