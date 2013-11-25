package common;

import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JLabel;

public class MensajeMovimiento implements Serializable {

	/* Atributos */
	private String jugador;
	private String jugador1;				// jugador1 y jugador2 van a ser mis identificadores de interfaz y me ayudaran a vincular con las pizarras
	private String jugador2;
	private int x;
	private int y;
	private JButton boton;
	private JLabel label1;
	private JLabel label2;
		
	/* Constructores */
	public MensajeMovimiento() {
		this("","","",0,0,null,null,null);
	}
	
	public MensajeMovimiento(String jugador, String j1, String j2, int x, int y, JButton boton, JLabel label1, JLabel label2) {
		this.jugador = jugador;
		this.jugador1 = j1;
		this.jugador2 = j2;
		this.x = x;
		this.y = y;
		this.boton = boton;
		this.label1 = label1;
		this.label2 = label2;
	}

	/* Metodos */
	
	/* Getters && Setters */
	public String getJugador() {
		return jugador;
	}

	public void setJugador(String jugador) {
		this.jugador = jugador;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getJugador1() {
		return jugador1;
	}

	public void setJugador1(String jugador1) {
		this.jugador1 = jugador1;
	}

	public String getJugador2() {
		return jugador2;
	}

	public void setJugador2(String jugador2) {
		this.jugador2 = jugador2;
	}

	public JButton getBoton() {
		return boton;
	}

	public void setBoton(JButton boton) {
		this.boton = boton;
	}

	public JLabel getLabel1() {
		return label1;
	}

	public void setLabel1(JLabel label1) {
		this.label1 = label1;
	}

	public JLabel getLabel2() {
		return label2;
	}

	public void setLabel2(JLabel label2) {
		this.label2 = label2;
	}
	
}
