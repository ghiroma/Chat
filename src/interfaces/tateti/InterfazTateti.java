package interfaces.tateti;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.StringTokenizer;

import javax.swing.*;

import common.Mensaje;
import common.MensajeMovimiento;
import client.ChatClient;

//import Control.Controller;

public class InterfazTateti extends JFrame implements ActionListener, Serializable {

	/* Atributos */
		
		/* Clases y variables utiles */
		//private Controller controlador = null;
		//private Blackboard bb = null;
		private InvitacionJuego invitacion = null;
		//private NuevaPartida nuevaPartida = null;
		private static final long serialVersionUID = 1L;
		private String player1;
		private String player2;
		private String ganador;
		private String perdedor;
		private boolean empate = false;
		
		/* Paneles y Menues */
		private JPanel jPanelJuego = null;
		private JMenuBar jJMenuBar = null;
		private JMenu jMenu = null;
		private JMenuItem jMenuItemNuevoJuego = null;
		private JMenuItem jMenuItemSalir = null;
		private JPanel jPanelTabla = null;
		private JPanel jPanelLinea1 = null;
		private JPanel jPanelLineas23 = null;
		private JPanel jPanelLinea2 = null;
		private JPanel jPanelLinea3 = null;
		private JPanel jPanelInformacionJuego = null;
		private JPanel jPanelLabels = null;
		private JPanel jPanelJugadores = null;
		private JPanel jPanelChat = null;
		private JPanel jPanelConversacion = null;
		private JPanel jPanelMensajeEmisor = null;
		private JPanel jPanelPrincipal = null;
		
		/* Botones */
		private JButton jButton1 = null;
		private JButton jButton2 = null;
		private JButton jButton3 = null;
		private JButton jButton4 = null;
		private JButton jButton5 = null;
		private JButton jButton6 = null;
		private JButton jButton7 = null;
		private JButton jButton8 = null;
		private JButton jButton9 = null;
		private JButton jButtonEnviarMensaje = null;	
		
		/* Labels */
		private JLabel jLabel1 = null;									// Texto informativo
		private JLabel jLabel2 = null;									//	Indica a quien le toca
		private JLabel jLabel3 = null;									// 	Indica ganador
		private JLabel jLabelPlayer1 = null;
		private JLabel jLabelPlayer2 = null;
		
		/* Texts Areas */
		private JScrollPane jScrollPaneConversacion = null;						
		private JScrollPane jScrollPaneMensajeEmisor = null;
		private JTextArea textAreaConversacion;
		private JTextArea textAreaMensajeEmisor;	
		
	/* Constructores */ 

	public InterfazTateti(){
		super();
		//this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent arg0) {
				InterfazTateti.this.dispose(); 										// Cierro interfaz tateti pero no la aplicacion, el metodo System.exit(0) cierra por completo la aplicacion
				}
			public void windowOpened(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowDeactivated(WindowEvent arg0) {}
			public void windowClosed(WindowEvent arg0) {}
			public void windowActivated(WindowEvent arg0) {}
		});		
		
		//this.bb = new Blackboard();
		this.limpiarTablero();

		/* Agrego actionListeners a los botones */
		jButton1.addActionListener(this);
		jButton2.addActionListener(this);
		jButton3.addActionListener(this);
		jButton4.addActionListener(this);
		jButton5.addActionListener(this);
		jButton6.addActionListener(this);
		jButton7.addActionListener(this);
		jButton8.addActionListener(this);
		jButton9.addActionListener(this);
		
		/* Agrego eventos a los text areas y al boton del panelChat */
		getJButtonEnviarMensaje().addActionListener(this);
		getTextAreaMensajeEmisor().addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				if (evt.getKeyCode()==KeyEvent.VK_ENTER) {
					int maxCaracteresPorLinea = 40;
					String mensajeEmisor = textAreaMensajeEmisor.getText();								// TODO enviarselo al server para que lo cargue en el textAreaConversacion
					String mensajeReceptor = textAreaConversacion.getText();
					String aux = "";
					StringTokenizer palabras= new StringTokenizer(mensajeEmisor," ");					// Copio todo el mensaje y lo cargo en un vector de palabras
					textAreaMensajeEmisor.setText("");
					String linea = "";
					while(palabras.hasMoreTokens()) {
						aux = palabras.nextToken() + " ";
						linea = linea + aux;
						if(linea.length() > maxCaracteresPorLinea) {
							mensajeReceptor = mensajeReceptor + "\r\n" + aux;
							linea = "";
						} else
							mensajeReceptor = mensajeReceptor + aux;
					}
					textAreaConversacion.setText(mensajeReceptor);
				}
			}}); 
		
		//TODO CONTROLADOR this.controlador = new Controller();
		this.setTitle("Ta Te Ti ");
		this.setSize(750, 550);												
		this.setLocation(new java.awt.Point(200, 100));
		this.setResizable(false);
		this.setContentPane(getJPanelPrincipal());							// Panel principal tiene el panel de juego y el panel de chat
		this.setJMenuBar(getJJMenuBar());
		this.paintComponents(getGraphics());								// Pinta los componentes
		this.pack();
	}
	
	/* Metodos */
	public void limpiarTablero() {											
		
		getJButton1().setIcon(new ImageIcon(getClass().getResource("nada.png")));
		getJButton1().setBackground(Color.BLACK);
		getJButton2().setIcon(new ImageIcon(getClass().getResource("nada.png")));
		getJButton2().setBackground(Color.BLACK);
		getJButton3().setIcon(new ImageIcon(getClass().getResource("nada.png")));
		getJButton3().setBackground(Color.BLACK);
		getJButton4().setIcon(new ImageIcon(getClass().getResource("nada.png")));
		getJButton4().setBackground(Color.BLACK);
		getJButton5().setIcon(new ImageIcon(getClass().getResource("nada.png")));
		getJButton5().setBackground(Color.BLACK);
		getJButton6().setIcon(new ImageIcon(getClass().getResource("nada.png")));
		getJButton6().setBackground(Color.BLACK);
		getJButton7().setIcon(new ImageIcon(getClass().getResource("nada.png")));
		getJButton7().setBackground(Color.BLACK);
		getJButton8().setIcon(new ImageIcon(getClass().getResource("nada.png")));
		getJButton8().setBackground(Color.BLACK);
		getJButton9().setIcon(new ImageIcon(getClass().getResource("nada.png")));
		getJButton9().setBackground(Color.BLACK);

		getJLabel2().setIcon(new ImageIcon(getClass().getResource("circuloChico.png"))); 	
		getJLabel3().setVisible(false); 																
	}

	// TODO proceso de informacion de ganador y actualizacion del tablero para una nueva partida
	// 1- Activar Label de Ganador jugador tanto, y mostrar una ventana indicando jugador ganador o hubo empate que solo tenga un boton aceptar
	// 2- Luego de aceptar esa ventana emergente debe aparecer ventana si se desea iniciar nueva partida
	// 		Si->I- Envio solicitud de juego a mi rival
	//					Si-> Creo nuevo blackboard y limpio tablero
	//					No-> Cierro todo y vuelvo a pantalla principal de chat
	//		No->Cierro todo y voy a la interfaz del chat.
	
	
	public void actualizarEstadoJuego(int i, int j, int id, JButton btn) {
		/*
		int aux;
		setearIcono(jLabel2,(id+1)%2);
		setearIcono(btn,id);
		aux = bb.update(i, j, id);
		if(aux != -1) {
			if(aux == 1){
				// Hay ganador
				jLabel3.setText("Ganador: " + (id == 0?"Player2":"Player1"));
			} else if(aux == 0) {
				// Hay empate
				jLabel3.setText("Empate!");
			}
			jLabel3.setVisible(true);
		}
		*/
	}

	public void actionPerformed(ActionEvent ae) {
				
		if(ae.getSource() == jButton1) {
			MensajeMovimiento msg = new MensajeMovimiento(ChatClient.getInstance().getUsuarioLogeado().getUser(),player1,player2,0,0,jButton1,jLabel2,jLabel3);
			ChatClient.getInstance().enviarMovimiento(msg);
		}
		
		if(ae.getSource() == jButton2) {
			MensajeMovimiento msg = new MensajeMovimiento(ChatClient.getInstance().getUsuarioLogeado().getUser(),player1,player2,0,1,jButton2,jLabel2,jLabel3);
			ChatClient.getInstance().enviarMovimiento(msg);
		}
		
		if(ae.getSource() == jButton3) {
			MensajeMovimiento msg = new MensajeMovimiento(ChatClient.getInstance().getUsuarioLogeado().getUser(),player1,player2,0,2,jButton3,jLabel2,jLabel3);
			ChatClient.getInstance().enviarMovimiento(msg);
		}
		
		if(ae.getSource() == jButton4) {
			MensajeMovimiento msg = new MensajeMovimiento(ChatClient.getInstance().getUsuarioLogeado().getUser(),player1,player2,1,0,jButton4,jLabel2,jLabel3);
			ChatClient.getInstance().enviarMovimiento(msg);
		}
		
		if(ae.getSource() == jButton5) {
			MensajeMovimiento msg = new MensajeMovimiento(ChatClient.getInstance().getUsuarioLogeado().getUser(),player1,player2,1,1,jButton5,jLabel2,jLabel3);
			ChatClient.getInstance().enviarMovimiento(msg);
		}
		
		if(ae.getSource() == jButton6) {
			MensajeMovimiento msg = new MensajeMovimiento(ChatClient.getInstance().getUsuarioLogeado().getUser(),player1,player2,1,2,jButton6,jLabel2,jLabel3);
			ChatClient.getInstance().enviarMovimiento(msg);
		}
		
		if(ae.getSource() == jButton7) {
			MensajeMovimiento msg = new MensajeMovimiento(ChatClient.getInstance().getUsuarioLogeado().getUser(),player1,player2,2,0,jButton7,jLabel2,jLabel3);
			ChatClient.getInstance().enviarMovimiento(msg);
		}
		
		if(ae.getSource() == jButton8) {
			MensajeMovimiento msg = new MensajeMovimiento(ChatClient.getInstance().getUsuarioLogeado().getUser(),player1,player2,2,1,jButton8,jLabel2,jLabel3);
			ChatClient.getInstance().enviarMovimiento(msg);
		}
		
		if(ae.getSource() == jButton9) {
			MensajeMovimiento msg = new MensajeMovimiento(ChatClient.getInstance().getUsuarioLogeado().getUser(),player1,player2,2,2,jButton9,jLabel2,jLabel3);
			ChatClient.getInstance().enviarMovimiento(msg);
		}
		
		/*	if(ae.getSource() == jButton1){
			int iden = bb.nroJugadas%2;
			int aux;
			if(bb.inspect(0, 0, iden))							// Si inspect == true puedo realizar movimiento
				this.actualizarEstadoJuego(0,0,iden,jButton1);
			else
				System.out.println("Posicion ya ocupada");
		}
		
		if(ae.getSource() == jButton2){
			int iden = bb.nroJugadas%2;
			int aux;
			if(bb.inspect(0, 1, iden))			
				this.actualizarEstadoJuego(0,1,iden,jButton2);
			else
				System.out.println("Posicion ya ocupada");
		}
		
		if(ae.getSource() == jButton3){
			int iden = bb.nroJugadas%2;
			int aux;
			if(bb.inspect(0, 2, iden))						
				this.actualizarEstadoJuego(0,2,iden,jButton3);
			else
				System.out.println("Posicion ya ocupada");
		}		
		
		if(ae.getSource() == jButton4){
			int iden = bb.nroJugadas%2;
			int aux;
			if(bb.inspect(1, 0, iden))					
				this.actualizarEstadoJuego(1,0,iden,jButton4);
			else
				System.out.println("Posicion ya ocupada");
		}
		
		if(ae.getSource() == jButton5){
			int iden = bb.nroJugadas%2;
			int aux;
			if(bb.inspect(1, 1, iden))				
				this.actualizarEstadoJuego(1,1,iden,jButton5);
			else
				System.out.println("Posicion ya ocupada");
		}
		
		if(ae.getSource() == jButton6){
			int iden = bb.nroJugadas%2;
			int aux;
			if(bb.inspect(1, 2, iden))					
				this.actualizarEstadoJuego(1,2,iden,jButton6);
			else
				System.out.println("Posicion ya ocupada");
		}
		
		if(ae.getSource() == jButton7){
			int iden = bb.nroJugadas%2;
			int aux;
			if(bb.inspect(2, 0, iden))					
				this.actualizarEstadoJuego(2,0,iden,jButton7);
			else
				System.out.println("Posicion ya ocupada");
		}
		
		if(ae.getSource() == jButton8){
			int iden = bb.nroJugadas%2;
			int aux;
			if(bb.inspect(2, 1, iden))					
				this.actualizarEstadoJuego(2,1,iden,jButton8);
			else
				System.out.println("Posicion ya ocupada");
		}
		
		if(ae.getSource() == jButton9){
			int iden = bb.nroJugadas%2;
			int aux;
			if(bb.inspect(2, 2, iden))					
				this.actualizarEstadoJuego(2,2,iden,jButton9);
			else
				System.out.println("Posicion ya ocupada");
		}
		
		if(ae.getSource() == jButtonEnviarMensaje) {
			int maxCaracteresPorLinea = 40;
			String mensajeEmisor = textAreaMensajeEmisor.getText();			// TODO enviarselo al server para que lo cargue en el textAreaConversacion
			String mensajeReceptor = textAreaConversacion.getText();
			String aux = "";
			StringTokenizer palabras= new StringTokenizer(mensajeEmisor," ");					// Copio todo el mensaje y lo cargo en un vector de palabras
			textAreaMensajeEmisor.setText("");
			String linea = "";
			while(palabras.hasMoreTokens()) {
				aux = palabras.nextToken() + " ";
				linea = linea + aux;
				if(linea.length() > maxCaracteresPorLinea) {
					mensajeReceptor = mensajeReceptor + "\r\n" + aux;
					linea = "";
				} else
					mensajeReceptor = mensajeReceptor + aux;
			}
			textAreaConversacion.setText(mensajeReceptor + "\n");
		}
	*/	
	}
	public void setearIcono(Object obj, int id) {							
		if(obj instanceof JLabel) {
			JLabel l = (JLabel) obj;
			if (id != 0)
				l.setIcon(new ImageIcon(getClass().getResource("cruzChica.png")));
			else
				l.setIcon(new ImageIcon(getClass().getResource("circuloChico.png")));
		} else if(obj instanceof JButton) {
			JButton b = (JButton) obj;
			if (id != 0)
				b.setIcon(new ImageIcon(getClass().getResource("cruzGrande.png")));
			else
			b.setIcon(new ImageIcon(getClass().getResource("circuloGrande.png")));
		}
	}
	
	/* PANEL JUEGO */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			try{
				jJMenuBar = new JMenuBar();
				jJMenuBar.setPreferredSize(new java.awt.Dimension(20, 30));
				jJMenuBar.add(getJMenu());
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jJMenuBar;
	}

	private JMenu getJMenu() {
		if (jMenu == null) {
			try{
				jMenu = new JMenu();
				jMenu.setText("Archivo");
				jMenu.add(getJMenuItemNuevoJuego());
				jMenu.add(getJMenuItemSalir());
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jMenu;
	}

	// TODO agregar funcionalidad de los itemButtons
	private JMenuItem getJMenuItemNuevoJuego() {				
		if (jMenuItemNuevoJuego == null) {
			try {
				jMenuItemNuevoJuego = new JMenuItem();
				jMenuItemNuevoJuego.setText("Nuevo juego");
				jMenuItemNuevoJuego.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent e) {
							invitacion = new InvitacionJuego();	// Debo enviar este objeto junto con el usuario con el que quiero jugar entonces el servidor se encargara de hacer invitacion.show y esperar por la respuesta escuchando si apreto el boton si o no
							//invitacion.show();
							//bb = new Blackboard();
							limpiarTablero();
						}
					});
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jMenuItemNuevoJuego;
	}

	private JMenuItem getJMenuItemSalir() {
		if (jMenuItemSalir == null) {
			try{
				jMenuItemSalir = new JMenuItem();
				jMenuItemSalir.setText("Salir");
				jMenuItemSalir.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							InterfazTateti.this.dispose();				// System.exti(0) se reemplaoz por InterfazTateti.this.dispose() porque terminaba con la ejecucion del programa y no debia ser asi, solo se debe cerrar de manera correcta el frame de la interfaz tateti
						}
					});
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jMenuItemSalir;
	}

	public JPanel getJPanelPrincipal() {
		if(jPanelPrincipal == null){
			try {
				jPanelPrincipal = new JPanel();
				jPanelPrincipal.setLayout(new FlowLayout());
				jPanelPrincipal.add(getJPanelJuego());
				jPanelPrincipal.add(getJPanelChat());
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jPanelPrincipal;
	}
	
	public JPanel getJPanelJuego() {
		if(jPanelJuego == null) {
			try {
				jPanelJuego = new JPanel();
				jPanelJuego.setLayout(new BorderLayout());
				jPanelJuego.setBounds(0, 0, 450, 450);
				//jPanelJuego.add(new JLabel("Panel1"));
				//jPanelJuego.setBackground(Color.RED);
				jPanelJuego.add(getJPanelTabla(),java.awt.BorderLayout.CENTER);
				jPanelJuego.add(getJPanelInformacionJuego(),java.awt.BorderLayout.SOUTH);
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jPanelJuego;
	}
	
	public JPanel getJPanelTabla() {
		if(jPanelTabla == null) {
			try {
				jPanelTabla = new JPanel();
				jPanelTabla.setLayout(new BorderLayout());
				jPanelTabla.setBounds(0, 0, 450, 375);
				jPanelTabla.add(getJPanelLinea1(),java.awt.BorderLayout.NORTH);
				jPanelTabla.add(getJPanelLineas23(),java.awt.BorderLayout.CENTER);
				jPanelTabla.setVisible(true);
				
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jPanelTabla;
	}
	
	public JPanel getJPanelInformacionJuego() {
		if(jPanelInformacionJuego == null) {
			try {
				jPanelInformacionJuego = new JPanel();
				jPanelInformacionJuego.setLayout(new BorderLayout());
				jPanelInformacionJuego.setBounds(0,0,450,75);
				jPanelInformacionJuego.add(getJPanelLabels(),java.awt.BorderLayout.CENTER);
				jPanelInformacionJuego.add(getJPanelJugadores(),java.awt.BorderLayout.SOUTH);
				jPanelInformacionJuego.setVisible(true);
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jPanelInformacionJuego;
	}
	
	public JPanel getJPanelLabels() {
		if(jPanelLabels == null) {
			try {
				jPanelLabels = new JPanel();
				jPanelLabels.setLayout(new FlowLayout());
				jPanelLabels.add(getJLabel1());
				jPanelLabels.add(getJLabel2());
				jPanelLabels.add(getJLabel3());
				jPanelLabels.setVisible(true);
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jPanelLabels;
	}
	
	public JLabel getJLabel1() {
		if(jLabel1 == null) {
			try {
				jLabel1 = new JLabel();
				jLabel1.setFont(new java.awt.Font("Arial Black", java.awt.Font.BOLD, 18));
				jLabel1.setBounds(new java.awt.Rectangle(4, 7, 120, 40));
				jLabel1.setText("Juega: ");
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jLabel1;
	}

	public JLabel getJLabel2() {
		if(jLabel2 == null) {
			try {
				jLabel2 = new JLabel();
				jLabel2.setIcon(new ImageIcon(getClass().getResource("circuloChico.png")));
				jLabel2.setBounds(new java.awt.Rectangle(125, 7, 55, 55));		
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jLabel2;
	}
	
	public JLabel getJLabel3() {					//TODO colocar Player1 = usurio1, Player2 = usuario2 (invitado)
		if(jLabel3 == null) {
			try {
				jLabel3 = new JLabel();
				jLabel3.setBounds(200, 7, 150, 40);
				jLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.red, 5));
				jLabel3.setFont(new java.awt.Font("Rockwell Extra Bold",java.awt.Font.BOLD, 20));
				jLabel3.setForeground(java.awt.Color.RED);
				jLabel3.setText("Ganador: Player1");		
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jLabel3;
	}
	
	public JPanel getJPanelJugadores() {			// TODO ver porque no se muestra el panel de jugadores
		if(jPanelJugadores == null) {
			try {
				jPanelJugadores = new JPanel();
				jPanelJugadores.setLayout(new FlowLayout());
				jPanelJugadores.add(getJLabelPlayer1());
				jPanelJugadores.add(getJLabelPlayer2());
				jPanelJugadores.setVisible(true);
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jPanelJugadores;
	}
	
	public JLabel getJLabelPlayer1() {
		if(jLabelPlayer1 == null) {
			try {
				jLabelPlayer1.setFont(new java.awt.Font("Arial Black", java.awt.Font.BOLD, 12));
				jLabelPlayer1.setBounds(new java.awt.Rectangle(4, 7, 120, 20));
				jLabelPlayer1.setText("Player1:");	// player1 sera usuario1
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jLabelPlayer1;
	}
	
	public JLabel getJLabelPlayer2() {
		if(jLabelPlayer2 == null) {
			try {

				jLabelPlayer2.setFont(new java.awt.Font("Arial Black", java.awt.Font.BOLD, 12));
				jLabelPlayer2.setBounds(new java.awt.Rectangle(150, 7, 120, 20));
				jLabelPlayer2.setText("Player2:");	// player2 sera usuario2
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jLabelPlayer2;
	}
	
	public JPanel getJPanelLinea1() {
		if(jPanelLinea1 == null) {
			try {
				jPanelLinea1 = new JPanel();
				jPanelLinea1.setLayout(new FlowLayout());
				jPanelLinea1.setBounds(0, 0, 450, 125);
				//jPanelLinea1.setBackground(Color.YELLOW);
				jPanelLinea1.add(getJButton1());
				jPanelLinea1.add(getJButton2());
				jPanelLinea1.add(getJButton3());
				jPanelLinea1.setVisible(true);
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jPanelLinea1;
	}
	
	// Botones de la fila 1 
	public JButton getJButton1() {
		if(jButton1 == null) {
			try {
				//jButton1 = new JButton("Boton1");
				jButton1 = new JButton(new ImageIcon(getClass().getResource("circuloGrande.png")));
				//jButton1.setBounds(10, 100, 100, 100);
				jButton1.setBackground(Color.BLACK);
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jButton1;
	}
	
	public JButton getJButton2() {
		if(jButton2 == null) {
			try {
				//jButton2 = new JButton("Boton2");
				jButton2 = new JButton(new ImageIcon(getClass().getResource("circuloGrande.png")));
				//jButton2.setBounds(210, 100, 100, 100);
				jButton2.setBackground(Color.BLACK);
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jButton2;
	}
	
	public JButton getJButton3() {
		if(jButton3 == null) {
			try {
				//jButton3 = new JButton("Boton3");
				jButton3 = new JButton(new ImageIcon(getClass().getResource("circuloGrande.png")));
				//jButton3.setBounds(310, 100, 100, 100);	
				jButton3.setBackground(Color.BLACK);
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jButton3;
	}
	
	public JPanel getJPanelLineas23() {
		if(jPanelLineas23 == null) {
			try {
				jPanelLineas23 = new JPanel();
				jPanelLineas23.setLayout(new BorderLayout());
				jPanelLineas23.add(getJPanelLinea2(),java.awt.BorderLayout.NORTH);
				jPanelLineas23.add(getJPanelLinea3(),java.awt.BorderLayout.CENTER);
				jPanelLineas23.setVisible(true);
				
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jPanelLineas23;
	}
	
	public JPanel getJPanelLinea2() {
		if(jPanelLinea2 == null) {
			try {
				jPanelLinea2 = new JPanel();
				jPanelLinea2.setLayout(new FlowLayout());
				jPanelLinea2.setBounds(0, 0, 450, 125);
				//jPanelLinea2.setBackground(Color.YELLOW);
				jPanelLinea2.add(getJButton4());
				jPanelLinea2.add(getJButton5());
				jPanelLinea2.add(getJButton6());
				jPanelLinea2.setVisible(true);
				
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jPanelLinea2;
	}
	
	// Botones de la fila 2 
	public JButton getJButton4() {
		if(jButton4 == null) {
			try {
				//jButton4 = new JButton("Boton4");
				jButton4 = new JButton(new ImageIcon(getClass().getResource("circuloGrande.png")));
				//jButton4.setBounds(10, 100, 100, 100);
				jButton4.setBackground(Color.BLACK);
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jButton4;
	}
	
	public JButton getJButton5() {
		if(jButton5 == null) {
			try {
				//jButton5 = new JButton("Boton5");
				jButton5 = new JButton(new ImageIcon(getClass().getResource("circuloGrande.png")));
				//jButton5.setBounds(210, 100, 100, 100);
				jButton5.setBackground(Color.BLACK);
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jButton5;
	}
	
	public JButton getJButton6() {
		if(jButton6 == null) {
			try {
				//jButton6 = new JButton("Boton6");
				jButton6 = new JButton(new ImageIcon(getClass().getResource("circuloGrande.png")));
				//jButton6.setBounds(310, 100, 100, 100);	
				jButton6.setBackground(Color.BLACK);
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jButton6;
	}
	
	public JPanel getJPanelLinea3() {
		if(jPanelLinea3 == null) {
			try {
				jPanelLinea3 = new JPanel();
				jPanelLinea3.setLayout(new FlowLayout());
				jPanelLinea3.setBounds(0, 0, 450, 125);
				//jPanelLinea3.setBackground(Color.YELLOW);
				jPanelLinea3.add(getJButton7());
				jPanelLinea3.add(getJButton8());
				jPanelLinea3.add(getJButton9());
				jPanelLinea3.setVisible(true);
				
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jPanelLinea3;
	}
	
	// Botones de la fila 3 
	public JButton getJButton7() {
		if(jButton7 == null) {
			try {
				//jButton7 = new JButton("Boton7");
				jButton7 = new JButton(new ImageIcon(getClass().getResource("circuloGrande.png")));
				//jButton7.setBounds(10, 100, 100, 100);	
				jButton7.setBackground(Color.BLACK);
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jButton7;
	}
	
	public JButton getJButton8() {
		if(jButton8 == null) {
			try {
				//jButton8 = new JButton("Boton8");
				jButton8 = new JButton(new ImageIcon(getClass().getResource("circuloGrande.png")));
				//jButton8.setBounds(210, 100, 100, 100);
				jButton8.setBackground(Color.BLACK);
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jButton8;
	}
	
	public JButton getJButton9() {
		if(jButton9 == null) {
			try {
				//jButton9 = new JButton("Boton9");
				jButton9 = new JButton(new ImageIcon(getClass().getResource("circuloGrande.png")));
				jButton9.setBackground(Color.BLACK);
				//jButton9.setBounds(310, 100, 100, 100);	
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jButton9;
	}
	/* FIN PANEL JUEGO */
	
	/* PANEL CHAT */
	public JPanel getJPanelChat() {
		if(jPanelChat == null) {
			try {
				jPanelChat = new JPanel();
				jPanelChat.setLayout(new BorderLayout());
				jPanelChat.setBounds(0, 0, 300, 450);
				//jPanelChat.setBackground(Color.BLUE);
				//jPanelChat.add(new JLabel("Panel2"));
				jPanelChat.add(getJPanelConversacion(),java.awt.BorderLayout.CENTER);
				jPanelChat.add(getJPanelMensajeEmisor(),java.awt.BorderLayout.SOUTH);
				jPanelChat.setVisible(true);
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jPanelChat;
	}
	
	public JPanel getJPanelConversacion() {
		if(jPanelConversacion == null) {
			try {
				jPanelConversacion = new JPanel();
				jPanelConversacion.setLayout(new BorderLayout());
				textAreaConversacion = new JTextArea(20,30);
				textAreaConversacion.setEditable(false);
				jScrollPaneConversacion = new JScrollPane(textAreaConversacion);
				jPanelConversacion.add(jScrollPaneConversacion,java.awt.BorderLayout.CENTER);
				jPanelConversacion.setVisible(true);
				
			} catch(java.lang.Throwable e) {
				
			}
		} 
		return jPanelConversacion;
	}
	
	public JPanel getJPanelMensajeEmisor() {
		if(jPanelMensajeEmisor == null) {
			try {
				jPanelMensajeEmisor = new JPanel();
				jPanelMensajeEmisor.setLayout(new BorderLayout());				
				jScrollPaneMensajeEmisor = new JScrollPane(getTextAreaMensajeEmisor());
				jPanelMensajeEmisor.add(jScrollPaneMensajeEmisor,java.awt.BorderLayout.CENTER);
				jPanelMensajeEmisor.add(getJButtonEnviarMensaje(),java.awt.BorderLayout.EAST);
				jPanelMensajeEmisor.setVisible(true);
				
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jPanelMensajeEmisor;
	}
	
	public JButton getJButtonEnviarMensaje() {
		if(jButtonEnviarMensaje == null) {
			try {
				jButtonEnviarMensaje = new JButton("Enviar");
			} catch(java.lang.Throwable e) {
				
			}
		}
		return jButtonEnviarMensaje;
	}
	
	public JTextArea getTextAreaMensajeEmisor() {
		if(textAreaMensajeEmisor == null) {
			try {
				textAreaMensajeEmisor = new JTextArea(5,25);
			} catch(java.lang.Throwable e) {
				
			}
		}
		return textAreaMensajeEmisor;
	}

	public String getPlayer1() {
		return player1;
	}

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public void setPlayer2(String player2) {
		this.player2 = player2;
	}
	
	/* FIN PANEL CHAT */
	
}
