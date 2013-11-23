package interfaces.tateti;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

public class GameOver extends JFrame{
	
	/* Atributos */
	private JPanel jPanelPrincipal = null;
	private JPanel jPanelBotones = null;
	private JLabel jLabelInformacion = null;
	private JButton jButtonAceptar = null;
		
	/* Constructores */
	public GameOver() {
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent arg0) {
				GameOver.this.dispose(); 										// Cierro frame pero no la aplicacion, el metodo System.exit(0) cierra por completo la aplicacion
				}
			public void windowOpened(WindowEvent arg0) {}
			public void windowIconified(WindowEvent arg0) {}
			public void windowDeiconified(WindowEvent arg0) {}
			public void windowDeactivated(WindowEvent arg0) {}
			public void windowClosed(WindowEvent arg0) {}
			public void windowActivated(WindowEvent arg0) {}
		});
		this.setTitle("Game Over");
		this.setSize(300, 100);
		this.setLocation(new java.awt.Point(500, 300));
		this.setResizable(true);
		this.setContentPane(getJPanelPrincipal());
		
		// TODO debe guardar toda la informacion de la partida finalizada
		jButtonAceptar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				GameOver.this.dispose();				// System.exti(0) se reemplazo por GameOver.this.dispose() porque terminaba con la ejecucion del programa y no debia ser asi, solo se debe cerrar de manera correcta el frame de la interfaz tateti
			}
		});
		
		//this.pack();	 // Compacta el frame hasta el minimo tamaño para poder seguir visualizando componentes
		this.setVisible(true);
	}
	
	/* Metodos */
	public JLabel getJLabelInformacion() {
		if(jLabelInformacion == null) {
			jLabelInformacion = new JLabel("Ganador o Empate");
			jLabelInformacion.setHorizontalAlignment(JLabel.CENTER);
			jLabelInformacion.setBounds(50, 50, 300, 50);
			jLabelInformacion.setVisible(true);
		}		
		return jLabelInformacion;
	}
	
	public JButton getJButtonAceptar() {		
		if(jButtonAceptar == null) {
			jButtonAceptar = new JButton("Aceptar");
			jButtonAceptar.setBounds(55, 125, 120, 95);
		}
		return jButtonAceptar;
	}
	
	public JPanel getJPanelPrincipal() {
		if(jPanelPrincipal == null) {
			jPanelPrincipal = new JPanel();
			jPanelPrincipal.setLayout(new BorderLayout());
			jPanelPrincipal.add(getJLabelInformacion(),java.awt.BorderLayout.CENTER);
			jPanelPrincipal.add(getJPanelBotones(),java.awt.BorderLayout.SOUTH);
		}
		return jPanelPrincipal;
	}
	
	public JPanel getJPanelBotones() {
		if(jPanelBotones == null) {
			jPanelBotones = new JPanel();
			jPanelBotones.setLayout(new FlowLayout());
			jPanelBotones.add(getJButtonAceptar());
		}
		return jPanelBotones;
	}
	
	public static void main(String args[]){
		GameOver go = new GameOver();
		go.show();
	}
	
}
