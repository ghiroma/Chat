package common;

import java.io.Serializable;

public class MensajeRespuestaMovimiento implements Serializable {

	/* Atributos */
	private String jugador1;
	private String jugador2;
	private boolean valido;
	private int x;
	private int y;
	private int nrojugada;
	
	/* Constructores */
	public MensajeRespuestaMovimiento() {
		this("","",false,-1,-1,-1);
	}
	
	public MensajeRespuestaMovimiento(String j1, String j2, boolean v, int x, int y, int j) {
		this.jugador1 = j1;
		this.jugador2 = j2;
		this.valido = v;
		this.x = x;
		this.y = y;
		this.nrojugada = j;		
	}

	/* Metodos */
	
	/* Getters && Setters */
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

	public boolean isValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
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

	public int getNrojugada() {
		return nrojugada;
	}

	public void setNrojugada(int nrojugada) {
		this.nrojugada = nrojugada;
	}
		
}
