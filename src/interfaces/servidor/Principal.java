package interfaces.servidor;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Font;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import server.ChatServer;

import common.FriendStatus;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1501608729613907406L;

	private JPanel contentPane;
	private JList listConectados;
	private JList listDesconectados;
	private DefaultListModel modelUsuariosConectados;
	private DefaultListModel modelUsuariosDesconectados;
	private JMenuItem mntmEnviarAlerta;
	private JMenuItem mntmVisualizarInformacion;
	private JMenuItem mntmVerHistorialLogin;
	private JMenuItem mntmBlanquearClave;
	private JMenuItem mntmPenalizar;
	private JMenuItem mntmDespenalizar;
	private JMenuItem mntmDesconectar;
	private JTextArea logEventos;
	
	/**
	 * Create the frame.
	 */
	public Principal() {
		setTitle("Servidor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 708, 483);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		this.obtenerListaUsuarios();

		JLabel lblNewLabel = new JLabel("Conectados");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));

		listConectados = new JList();
		listConectados.setName("conectados");
		listConectados.setModel(modelUsuariosConectados);
		listConectados.addMouseListener(rightClick);

		JLabel lblDesconectados = new JLabel("Desconectados");
		lblDesconectados.setFont(new Font("Tahoma", Font.PLAIN, 16));

		listDesconectados = new JList();
		listDesconectados.setName("desconectados");
		listDesconectados.setModel(modelUsuariosDesconectados);
		listDesconectados.addMouseListener(rightClick);

		JLabel lblLogDeEventos = new JLabel("Eventos");
		lblLogDeEventos.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JButton btnAlerta = new JButton("Enviar Alerta General");
		btnAlerta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Alerta alertaView = new Alerta(null);
				alertaView.setVisible(true);
			}
		});
		JButton btnCerrarServer = new JButton("Cerrar Server");
		btnCerrarServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ChatServer.getInstance().cerrarServer())
					dispose();
			}
		});
		logEventos = new JTextArea();
		logEventos.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(logEventos);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(btnAlerta, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnCerrarServer, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
								.addGap(49))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblLogDeEventos)
								.addPreferredGap(ComponentPlacement.RELATED)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 371, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addComponent(listConectados, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
							.addComponent(lblNewLabel))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDesconectados)
								.addComponent(listDesconectados, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(1)
									.addComponent(btnAlerta, GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
								.addComponent(btnCerrarServer, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblLogDeEventos)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 324, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(listConectados, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
							.addGap(23)
							.addComponent(lblDesconectados)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(listDesconectados, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)))
					.addGap(52))
		);

		mntmEnviarAlerta = new JMenuItem("Enviar alerta");
		mntmEnviarAlerta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Alerta alertaView = new Alerta(obtenerNombreUsuario(e));
				alertaView.setVisible(true);
			}
		});
		mntmVisualizarInformacion = new JMenuItem("Visualizar informacion");
		mntmVisualizarInformacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InformacionUsuario infoUsuarioView = new InformacionUsuario(obtenerNombreUsuario(e));
				infoUsuarioView.setVisible(true);
			}
		});
		mntmVerHistorialLogin = new JMenuItem("Ver historial login");
		mntmVerHistorialLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HistorialLoginUsuario historialLoginView = new HistorialLoginUsuario(obtenerNombreUsuario(e));
				historialLoginView.setVisible(true);
			}
		});
		mntmBlanquearClave = new JMenuItem("Blanquear clave");
		mntmBlanquearClave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChatServer.getInstance().blanquearClave(obtenerNombreUsuario(e));
			}
		});
		mntmPenalizar = new JMenuItem("Penalizar");
		mntmPenalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Penalizacion penalizacionView = new Penalizacion(obtenerNombreUsuario(e));
				penalizacionView.setVisible(true);
			}
		});
		mntmDespenalizar = new JMenuItem("Despenalizar");
		mntmDespenalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChatServer.getInstance().despenalizar(obtenerNombreUsuario(e));
			}
		});
		mntmDesconectar = new JMenuItem("Desconectar");
		mntmDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChatServer.getInstance().desconectarUsuario(obtenerNombreUsuario(e));
			}
		});

		contentPane.setLayout(gl_contentPane);
	}

	private MouseAdapter rightClick = new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			if (SwingUtilities.isRightMouseButton(e)) {
				JList lista = (JList)e.getSource();
				if(lista.getName().equals("conectados")) {
					addPopup(listConectados, generarPopupMenu(1));
				} else {
					addPopup(listDesconectados, generarPopupMenu(-1));
				}
				try {
					Robot robot = new Robot();
					robot.mousePress(InputEvent.BUTTON1_MASK);
					robot.mouseRelease(InputEvent.BUTTON1_MASK);
				} catch (AWTException ae) {
					System.out.println(ae);
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

	private JPopupMenu generarPopupMenu(int esConectado) {
		JPopupMenu popupMenu = new JPopupMenu();
		if(esConectado > 0){
			popupMenu.setName("conectados");
			popupMenu.add(mntmEnviarAlerta);
			popupMenu.add(mntmVisualizarInformacion);
			popupMenu.add(mntmVerHistorialLogin);
			popupMenu.add(mntmBlanquearClave);
			popupMenu.add(mntmPenalizar);
			popupMenu.add(mntmDespenalizar);
			popupMenu.add(mntmDesconectar);
		} else {
			popupMenu.setName("desconectados");
			popupMenu.add(mntmVisualizarInformacion);
			popupMenu.add(mntmVerHistorialLogin);
			popupMenu.add(mntmBlanquearClave);
			popupMenu.add(mntmPenalizar);
			popupMenu.add(mntmDespenalizar);
		}
		return popupMenu;
	}

	private String obtenerNombreUsuario(ActionEvent e) {
		String nombreUsuario;
		if(((JMenuItem)e.getSource()).getParent().getName().equals("conectados")){
			nombreUsuario = (String)listConectados.getSelectedValue();
		} else {
			nombreUsuario = (String)listDesconectados.getSelectedValue();
		}
		return nombreUsuario;
	}

	private void obtenerListaUsuarios() {
		modelUsuariosConectados = new DefaultListModel();
		modelUsuariosDesconectados = new DefaultListModel();
		List<FriendStatus> usuarios = ChatServer.getInstance().obtenerUsuarios();
		for(FriendStatus usuario : usuarios) {
			//TODO FRONT : filtrar los usuarios q estan conectados y los q no
			if(usuario.getEstado()==(-1)) {
				modelUsuariosDesconectados.addElement(usuario.getUsername());
			} else {
				modelUsuariosConectados.addElement(usuario.getUsername());
			}
		}
	}

	public void logearEvento(String msg) {
		logEventos.append(msg + "\n");
		System.out.println(msg);
	}

}
