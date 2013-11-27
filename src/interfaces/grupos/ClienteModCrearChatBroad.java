package interfaces.grupos;

import groups.ClienteGrupo;
import groups.Grupo;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.ChatClient;

import common.FriendStatus;
import common.MensajeGrupo;

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

		final JList listAmigos = new JList();
		listAmigos.setBounds(20, 149, 181, 245);
		contentPane.add(listAmigos);
		listAmigos.setModel(this.obtenerListaAmigos());

		final JList listAmigosAgregados = new JList();
		listAmigosAgregados.setBounds(269, 149, 191, 245);
		contentPane.add(listAmigosAgregados);

		JButton btnAgregarAmigo = new JButton("->");
		btnAgregarAmigo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Obtengo el usuario que se selecciono
				String nombreUsuario = listAmigos.getSelectedValue().toString();
				// Hago el cambio entre la lista de amigos y la lista de amigos agregados
				// Lo saco de la lista de amigos y lo pongo en la lista de amigos agregados
				listAmigosAgregados.setModel(this.agregarAmigo(nombreUsuario));
				listAmigos.setModel(this.removerAmigo(nombreUsuario));
			}

			private DefaultListModel agregarAmigo(String nombreUsuario) {
				DefaultListModel modelAmigos = new DefaultListModel();
				boolean existe = false;
				// Recorro toda la lista de amigos agregados para que quede cargada con todos los elemtos que tenia
				for(int i = 0; i < listAmigosAgregados.getModel().getSize(); i++) {
					Object item = listAmigosAgregados.getModel().getElementAt(i);
					modelAmigos.addElement(item.toString());
					// Valido que el usuario no esta ya agregado
					if(item.toString() == nombreUsuario) {
						existe = true;
					}
				}
				if(!existe) {
					modelAmigos.addElement(nombreUsuario);	
				}
				return modelAmigos;
			}

			private DefaultListModel removerAmigo(String nombreUsuario) {
				DefaultListModel modelAmigos = new DefaultListModel();
				// Vuelvo a cargar la lista de amigos con todos los amigos menos el que se agrego
				for(int i = 0; i < listAmigos.getModel().getSize(); i++) {
					Object item = listAmigos.getModel().getElementAt(i);
					if(item.toString() != nombreUsuario) {
						modelAmigos.addElement(item.toString());
					}
				}
				return modelAmigos;
			}
		});
		btnAgregarAmigo.setBounds(211, 186, 48, 33);
		contentPane.add(btnAgregarAmigo);
		
		JButton btnRemoverAmigo = new JButton("<-");
		btnRemoverAmigo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Obtengo el usuario que se selecciono
				String nombreUsuario = listAmigosAgregados.getSelectedValue().toString();
				// Hago el cambio entre la lista de amigos y la lista de amigos agregados
				// Lo saco de la lista de amigos y lo pongo en la lista de amigos agregados
				listAmigos.setModel(this.agregarAmigo(nombreUsuario));
				listAmigosAgregados.setModel(this.removerAmigo(nombreUsuario));
			}

			private DefaultListModel agregarAmigo(String nombreUsuario) {
				DefaultListModel modelAmigos = new DefaultListModel();
				boolean existe = false;
				// Recorro toda la lista de amigos agregados para que quede cargada con todos los elemtos que tenia
				for(int i = 0; i < listAmigos.getModel().getSize(); i++) {
					Object item = listAmigos.getModel().getElementAt(i);
					modelAmigos.addElement(item.toString());
					// Valido que el usuario no este ya agregado
					if(item.toString() == nombreUsuario) {
						existe = true;
					}
				}
				if(!existe) {
					modelAmigos.addElement(nombreUsuario);	
				}
				return modelAmigos;
			}

			private DefaultListModel removerAmigo(String nombreUsuario) {
				DefaultListModel modelAmigos = new DefaultListModel();
				// Vuelvo a cargar la lista de amigos con todos los amigos menos el que se agrego
				for(int i = 0; i < listAmigosAgregados.getModel().getSize(); i++) {
					Object item = listAmigosAgregados.getModel().getElementAt(i);
					if(item.toString() != nombreUsuario) {
						modelAmigos.addElement(item.toString());
					}
				}
				return modelAmigos;
			}
		});
		btnRemoverAmigo.setBounds(211, 230, 48, 33);
		contentPane.add(btnRemoverAmigo);
		
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
				// Crea grupo
				List<ClienteGrupo> amigos = new ArrayList<ClienteGrupo>();
				for (int i = 0; i < listAmigosAgregados.getModel().getSize(); i++) {
					Object item = listAmigosAgregados.getModel().getElementAt(i);
					amigos.add(new ClienteGrupo(item.toString(), false, true));
					// TODO chequear ClienteAmigos
				}
				Grupo grupo = new Grupo(txtNombreGrupo.getText(), amigos);
				ChatClient.getInstance().crearGrupo(grupo);
				String nombre = txtNombreGrupo.getText();
				String emisor = ChatClient.getInstance().getUsuarioLogeado().getUser();
				List<ClienteGrupo> usuarios = new ArrayList<ClienteGrupo>();
				// Recorro toda la lista de los usuarios que se agregaron y los agrego a usuarios
				for (int i = 0; i < listAmigosAgregados.getModel().getSize(); i++) {
					Object item = listAmigosAgregados.getModel().getElementAt(i);
					usuarios.add(new ClienteGrupo(item.toString(), false, true));
				}
				// Creo el mensaje que se envia a todos los usuarios del grupo
				MensajeGrupo mensaje = new MensajeGrupo(grupo, emisor, "");
				// Valido que este todo bien antes de mostrar la pantalla
				// Se valida que haya usuarios agregados y que tenga nombre el grupo
				if (!usuarios.isEmpty() && !nombre.isEmpty()) {
					ClienteModSalaDeChat salaDeChat = new ClienteModSalaDeChat();
					// Me agrego como moderador//
					salaDeChat.setTitulos(ChatClient.getInstance().getUsuarioLogeado().getUser(), nombre);
					// Agrego todos mis amigos//
					salaDeChat.setClientes(usuarios);
					salaDeChat.setGrupo(nombre);
					salaDeChat.setVisible(true);
					ChatClient.getInstance().getMapaGrupos().put(nombre, salaDeChat);
				}
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
	
	private DefaultListModel obtenerListaAmigos() {
		DefaultListModel modelAmigos = new DefaultListModel();
		List<FriendStatus> amigos = ChatClient.getInstance().getAmigos();
		for (FriendStatus amigo : amigos) {
			if(amigo.getEstado() == 1)
				modelAmigos.addElement(amigo.getUsername());
		}
		return modelAmigos;
	}	
}
