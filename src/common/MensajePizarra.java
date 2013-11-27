package common;

import interfaces.tateti.Blackboard;

import java.io.Serializable;

public class MensajePizarra implements Serializable {
	
	/* Atributos */
	private static final long serialVersionUID = 47158548574929960L;

	private String jugador1;
	private String jugador2;
	private Blackboard pizarra;
	
	/* Constructores */
	public MensajePizarra() {
		this("","",null);
	}
	
	public MensajePizarra(String j1, String j2, Blackboard bb) {
		this.jugador1 = j1;
		this.jugador2 = j2;
		this.pizarra = bb;
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

	public Blackboard getPizarra() {
		return pizarra;
	}

	public void setPizarra(Blackboard pizarra) {
		this.pizarra = pizarra;
	}
	
}
