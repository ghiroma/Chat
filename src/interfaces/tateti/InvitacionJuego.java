package interfaces.tateti;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

import common.Mensaje;
import common.MensajeInvitacion;
import common.MensajeRespuestaInvitacion;
import client.ChatClient;

public class InvitacionJuego extends JFrame{

	/* Atributos */
	private JPanel jPanelPrincipal = null;
	private JPanel jPanelBotones = null;
	private JPanel jPanelLabels = null;
	private JLabel jLblInformacion = null;
	private JLabel jLblPregunta = null;
	private JButton jButtonSi = null;
	private JButton jButtonNo = null;	
	private String usuarioOrigen;
	private String usuarioDestino;
	private boolean acepto = false;
	
	/* Constructores */
	public InvitacionJuego() {
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent arg0) {
				InvitacionJuego.this.dispose(); 										// Cierro frame pero no la aplicacion, el metodo System.exit(0) cierra por completo la aplicacion
				}
			public void windowOpened(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowDeactivated(WindowEvent arg0) {}
			public void windowClosed(WindowEvent arg0) {}
			public void windowActivated(WindowEvent arg0) {}
		});		
		
		this.setTitle("Invitacion de juego");
		this.setSize(300, 110);
		this.setLocation(new java.awt.Point(500, 300));
		this.setResizable(true);
		this.setContentPane(getJPanelPrincipal());
		
		jButtonSi.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// TODO Diego
				MensajeRespuestaInvitacion mri = new MensajeRespuestaInvitacion(usuarioOrigen,usuarioDestino,true);
				acepto = true;
				ChatClient.getInstance().aceptacionInvitacionJuego(new Mensaje(Mensaje.RESPUESTA_INVITACION_JUEGO,mri));
				dispose();
			}
		});
		jButtonNo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				MensajeRespuestaInvitacion mri = new MensajeRespuestaInvitacion(usuarioOrigen,usuarioDestino,false);
				acepto = false;
				ChatClient.getInstance().aceptacionInvitacionJuego(new Mensaje(Mensaje.RESPUESTA_INVITACION_JUEGO,mri));
				InvitacionJuego.this.dispose();				// System.exti(0) se reemplaoz por InvitacionJuego.this.dispose() porque terminaba con la ejecucion del programa y no debia ser asi, solo se debe cerrar de manera correcta el frame de la interfaz tateti
			}
		});
		
		//this.pack();	 // Compacta el frame hasta el minimo tamaño para poder seguir visualizando componentes
		this.setVisible(true);
	}
	
	/* Metodos */
	public JLabel getJLblInformacion() {
		if(jLblInformacion == null) {
			jLblInformacion = new JLabel("User quiere jugar con usted");	// TODO la palabra user debe ser reemplazada por el cliente que este haciendo la invitacion a jugar
			jLblInformacion.setHorizontalAlignment(JLabel.CENTER);
			jLblInformacion.setBounds(50, 50, 300, 50);
		}		
		return jLblInformacion;
	}
	
	public void setJLblInformacion(String info) {
		jLblInformacion.setText(info);
	}
	
	public JLabel getJLblPregunta() {
		if(jLblPregunta == null) {
			jLblPregunta = new JLabel("¿Acepta invitacion?");
			jLblPregunta.setHorizontalAlignment(JLabel.CENTER);
			jLblPregunta.setBounds(50, 50, 300, 50);
		}		
		return jLblPregunta;
	}
	
	public JButton getJButtonSi() {		
		if(jButtonSi == null) {
			jButtonSi = new JButton("Si");
			jButtonSi.setBounds(55, 125, 120, 95);
		}
		return jButtonSi;
	}
	
	public JButton getJButtonNo() {
		if(jButtonNo == null) {
			jButtonNo = new JButton("No");
			jButtonNo.setBounds(180, 125, 120, 95);
		}
		return jButtonNo;
	}
	
	public JPanel getJPanelBotones() {
		if(jPanelBotones == null) {
			jPanelBotones = new JPanel();
			jPanelBotones.setLayout(new FlowLayout());
			jPanelBotones.add(getJButtonSi());
			jPanelBotones.add(getJButtonNo());
		}
		return jPanelBotones;
	}
	
	public JPanel getJPanelLabels() {
		if(jPanelLabels == null) {
			jPanelLabels = new JPanel();
			jPanelLabels.setLayout(new BorderLayout());
			jPanelLabels.add(getJLblInformacion(),java.awt.BorderLayout.NORTH);
			jPanelLabels.add(getJLblPregunta(),java.awt.BorderLayout.CENTER);
		}
		return jPanelLabels;
	}
	
	public JPanel getJPanelPrincipal() {
		if(jPanelPrincipal == null) {
			jPanelPrincipal = new JPanel();
			jPanelPrincipal.setLayout(new BorderLayout());
			jPanelPrincipal.add(getJPanelLabels(),java.awt.BorderLayout.NORTH);
			jPanelPrincipal.add(getJPanelBotones(),java.awt.BorderLayout.CENTER);
		}
		return jPanelPrincipal;
	}

	/* Getters && Setters */
	public String getUsuarioOrigen() {
		return usuarioOrigen;
	}

	public void setUsuarioOrigen(String usuarioOrigen) {
		this.usuarioOrigen = usuarioOrigen;
	}

	public String getUsuarioDestino() {
		return usuarioDestino;
	}

	public void setUsuarioDestino(String usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}

	public boolean isAcepto() {
		return acepto;
	}

	public void setAcepto(boolean acepto) {
		this.acepto = acepto;
	}
	
	
}
