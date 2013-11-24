package interfaces.grupos;

import groups.Grupo;

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

import client.ChatClient;

import common.MensajeGrupo;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JList;
import javax.swing.JTable;

public class ClienteModCrearChatBroad extends JFrame {

	private static final long serialVersionUID = -6815140153895535755L;

	private JPanel contentPane;
	private JTextField txtNombreGrupo;

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
		
		JButton btnNewButton = new JButton("->");
		btnNewButton.setBounds(211, 186, 48, 33);
		contentPane.add(btnNewButton);
		
		JButton button = new JButton("<-");
		button.setBounds(211, 230, 48, 33);
		contentPane.add(button);
		
		JLabel lblNombreDelGrupo = new JLabel("NOMBRE DEL GRUPO: ");
		lblNombreDelGrupo.setBounds(10, 69, 191, 14);
		contentPane.add(lblNombreDelGrupo);
		
		txtNombreGrupo = new JTextField();
		txtNombreGrupo.setBounds(269, 66, 191, 20);
		contentPane.add(txtNombreGrupo);
		txtNombreGrupo.setColumns(10);
		
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
		JList listAmigos = new JList();
		listAmigos.setBounds(20, 149, 181, 245);
		contentPane.add(listAmigos);
		
		JList listAmigosAgregados = new JList();
		listAmigosAgregados.setBounds(269, 149, 191, 245);
		contentPane.add(listAmigosAgregados);
		
		btnCrearSala.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String nombre = txtNombreGrupo.getText();
				String emisor = ChatClient.getInstance().getUsuarioLogeado().getUser();
				List<String> usuarios = null;
				//TODO: hacer que tome el listado de amigos que estan en listAmigosAgregados y guardarlos en usuarios
				Grupo grupo = new Grupo(nombre, usuarios);
				MensajeGrupo mensaje = new MensajeGrupo(grupo, emisor, "");
				//if(!usuarios.isEmpty() && !nombre.isEmpty()){
					ClienteModSalaDeChat salaDeChat = new ClienteModSalaDeChat();
					salaDeChat.setVisible(true);	
				//}
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
