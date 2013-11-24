package interfaces.grupos;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClienteModCrearChatBroad extends JFrame {

	private static final long serialVersionUID = -6815140153895535755L;

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public ClienteModCrearChatBroad() {
		setTitle("Creaci\u00F3n de Sala de Chat");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 489, 498);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCrearGrupoDe = new JLabel("CREAR GRUPO DE CHAT BROADCAST");
		lblCrearGrupoDe.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCrearGrupoDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblCrearGrupoDe.setBounds(10, 11, 461, 47);
		contentPane.add(lblCrearGrupoDe);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setBounds(10, 149, 191, 238);
		contentPane.add(editorPane);
		
		JButton btnNewButton = new JButton("->");
		btnNewButton.setBounds(211, 186, 48, 33);
		contentPane.add(btnNewButton);
		
		JButton button = new JButton("<-");
		button.setBounds(211, 230, 48, 33);
		contentPane.add(button);
		
		JEditorPane editorPane_1 = new JEditorPane();
		editorPane_1.setBounds(269, 149, 191, 238);
		contentPane.add(editorPane_1);
		
		JLabel lblNombreDelGrupo = new JLabel("NOMBRE DEL GRUPO: ");
		lblNombreDelGrupo.setBounds(10, 69, 191, 14);
		contentPane.add(lblNombreDelGrupo);
		
		textField = new JTextField();
		textField.setBounds(269, 66, 191, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblSeleccionarAmigos = new JLabel("SELECCIONAR AMIGOS");
		lblSeleccionarAmigos.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeleccionarAmigos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSeleccionarAmigos.setBounds(10, 94, 461, 47);
		contentPane.add(lblSeleccionarAmigos);
		
		JButton btnCrearSala = new JButton("Crear Sala");
		btnCrearSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnCrearSala.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ClienteModSalaDeChat salaDeChat = new ClienteModSalaDeChat();
				salaDeChat.setVisible(true);
			}
		});
		btnCrearSala.setBounds(251, 419, 107, 23);
		contentPane.add(btnCrearSala);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(368, 419, 89, 23);
		contentPane.add(btnCancelar);
	}
}
