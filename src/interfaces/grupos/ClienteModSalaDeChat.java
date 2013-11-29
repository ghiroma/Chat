package interfaces.grupos;

import groups.ClienteGrupo;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Font;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

import client.ChatClient;

import common.Mensaje;

public class ClienteModSalaDeChat extends JFrame {

	private static final long serialVersionUID = 1313190352834111687L;

	private JPanel contentPane;
	private JTextField txtMensaje;
	private JLabel lblGrupoDeChat;
	private JList list;
	private String grupo;
	private JTextArea textArea;
	private JMenuItem mntmBanear;
	private JMenuItem mntmDesBannear;
	private JMenuItem mntmDesconectar;
	DefaultListModel modelo;

	/**
	 * Create the frame.
	 */
	public ClienteModSalaDeChat() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				ChatClient.getInstance().cerrarGrupo(grupo, " ha cerrado el grupo.");
				textArea.setText("Tu : " + txtMensaje.getText() + "\n");
				DefaultCaret caret = (DefaultCaret) textArea.getCaret();
				caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
			}
		});
		setTitle("Sala de Chat - Moderado");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 648, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				if (!txtMensaje.getText().equals("")) {
					ChatClient.getInstance().enviarMensajeGrupo(grupo, txtMensaje.getText());
					textArea.append("Tu : " + txtMensaje.getText() + "\n");
					DefaultCaret caret = (DefaultCaret) textArea.getCaret();
					caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
					// Limpio el text fiel para seguir escribiendo//
					txtMensaje.setText("");
				}
			}
		});

		/* Metodos PopUp */
		mntmBanear = new JMenuItem("Banear");
		mntmBanear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChatClient.getInstance().enviarMensajeUsuarioDeGrupo(grupo, obtenerNombreUsuario(e), Mensaje.BANNED_GRUPO);
			}
		});
		mntmDesconectar = new JMenuItem("Desconectar");
		mntmDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChatClient.getInstance().enviarMensajeUsuarioDeGrupo(grupo, obtenerNombreUsuario(e), Mensaje.DISCONNECT_GRUPO);
				removerDeLista(e);
			}
		});
		mntmDesBannear = new JMenuItem("Desbannear");
		mntmDesBannear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChatClient.getInstance().enviarMensajeUsuarioDeGrupo(grupo, obtenerNombreUsuario(e), Mensaje.DESBANEAR_GRUPO);
			}
		});
		/* Fin metodos PopUp */

		btnNewButton.setBounds(509, 430, 113, 23);
		contentPane.add(btnNewButton);

		txtMensaje = new JTextField();
		txtMensaje.setBounds(10, 431, 489, 20);
		contentPane.add(txtMensaje);
		txtMensaje.setColumns(10);

		JLabel lblConectados = new JLabel("PARTICIPANTES");
		lblConectados.setHorizontalAlignment(SwingConstants.CENTER);
		lblConectados.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblConectados.setBounds(509, 28, 113, 14);
		contentPane.add(lblConectados);

		list = new JList();
		list.setBounds(509, 53, 123, 366);
		contentPane.add(list);

		textArea = new JTextArea();
		textArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(textArea);
		textArea.setBounds(10, 49, 489, 371);
		contentPane.add(textArea);

	}

	public void mostrarMensajeDeAmigo(String texto) {
		textArea.append(">> " + " : " + texto + "\n");
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}

	public void setTitulos(String moderador, String nombre) {
		lblGrupoDeChat = new JLabel("Sala de Chat: " + nombre + " - Mod : " + moderador);
		lblGrupoDeChat.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblGrupoDeChat.setHorizontalAlignment(SwingConstants.CENTER);
		lblGrupoDeChat.setBounds(10, 23, 489, 23);
		list.addMouseListener(rightClick);
		contentPane.add(lblGrupoDeChat);
	}

	public void setClientes(List<ClienteGrupo> clientes) {
		modelo = new DefaultListModel();
		for (ClienteGrupo cli : clientes) {
			if (!cli.isMod()) {
				modelo.addElement(cli.getNombre());
			}
		}
		list.setModel(modelo);
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	private MouseAdapter rightClick = new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			if (SwingUtilities.isRightMouseButton(e)) {
				JList lista = (JList) e.getSource();
				addPopup(list, generarPopupMenu());
				try {
					Robot robot = new Robot();
					robot.mousePress(InputEvent.BUTTON1_MASK);
					robot.mouseRelease(InputEvent.BUTTON1_MASK);
				} catch (AWTException ae) {
					ae.printStackTrace();
				}
			}
		}
	};

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	private JPopupMenu generarPopupMenu() {
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setName("online");
		popupMenu.add(mntmBanear);
		popupMenu.add(mntmDesconectar);
		popupMenu.add(mntmDesBannear);
		return popupMenu;
	}

	private String obtenerNombreUsuario(ActionEvent e) {
		String nombreUsuario = null;
		if (((JMenuItem) e.getSource()).getParent().getName().equals("online")) {
			nombreUsuario = (String) list.getSelectedValue();
		}
		return nombreUsuario;
	}

	private int removerDeLista(ActionEvent e) {
		String nombre = obtenerNombreUsuario(e);
		modelo.remove(modelo.indexOf(nombre));
		list.setModel(modelo);
		return 0;
	}

}
