package interfaces.cliente;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.ChatClient;

public class ClienteConversacion extends JFrame {

	private static final long serialVersionUID = 5267263456354525306L;

	private String nombreAmigo;
	private JPanel contentPane;
	private JTextArea textArea;
	private JTextField txtMensaje;

	/**
	 * Create the frame.
	 */
	public ClienteConversacion(String nombreDeAmigo) {
		this.nombreAmigo = nombreDeAmigo;
		setTitle("Conversaci\u00F3n con: " +nombreDeAmigo);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 600, 577);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtMensaje = new JTextField();
		txtMensaje.setBounds(10, 483, 460, 43);
		contentPane.add(txtMensaje);
		txtMensaje.setColumns(10);
		txtMensaje.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar()=='\n')
					if(!txtMensaje.getText().equals("")) {
						ChatClient.getInstance().enviarMensajeChat(nombreAmigo, txtMensaje.getText());
						textArea.append("Tu : " + txtMensaje.getText() + "\n");
						txtMensaje.setText("");
					}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		

		
		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txtMensaje.getText().equals("")) {
					ChatClient.getInstance().enviarMensajeChat(nombreAmigo, txtMensaje.getText());
					textArea.append("Tu : " + txtMensaje.getText() + "\n");
					txtMensaje.setText("");
					
				}
			}
		});
		btnNewButton.setFocusPainted(true);
		btnNewButton.setBounds(483, 483, 89, 45);
		contentPane.add(btnNewButton);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setRows(1);
		textArea.setColumns(1);
		textArea.setBounds(10, 113, 460, 359);
		contentPane.add(textArea);
				
		JLabel lblNombreDeUsuario = new JLabel(nombreAmigo);
		lblNombreDeUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreDeUsuario.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblNombreDeUsuario.setBounds(141, 24, 302, 68);
		contentPane.add(lblNombreDeUsuario);
	}

	public void mostrarMensajeDeAmigo(String texto) {
		textArea.append(">> "+ nombreAmigo +" : "+ texto + "\n");
		
	}

}
