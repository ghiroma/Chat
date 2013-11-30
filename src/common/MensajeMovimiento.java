package common;

import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JLabel;

public class MensajeMovimiento implements Serializable {

	private static final long serialVersionUID = 47158503574929960L;
	
	/* Atributos */
	private String origen;				// jugador1 y jugador2 van a ser mis identificadores de interfaz y me ayudaran a vincular con las pizarras
	private String destinatario;
	private int x;
	private int y;
	
	/* Constructores */
	public MensajeMovimiento() {
		this("","",0,0);
	}
	
	public MensajeMovimiento(String origen, String destinatario, int x, int y) {
		this.origen = origen;
		this.destinatario = destinatario;
		this.x = x;
		this.y = y;
	}

	/* Metodos */
	
	/* Getters && Setters */
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

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	
}
