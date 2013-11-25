package common;

import interfaces.tateti.InterfazTateti;

import java.io.Serializable;

public class MensajePartida implements Serializable {

	private static final long serialVersionUID = 47158508574929960L;

	private String jugador1;
	private String jugador2;
	private InterfazTateti partida;

	/* Constructores */
	public MensajePartida(String j1,String j2, InterfazTateti partida) {
		this.jugador1 = j1;
		this.jugador2 = j2;
		this.partida = partida;
	}
	public MensajePartida() {
		this("","",null);
	}


	/* Metodos */


	/* Getters & Setters */
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
	public InterfazTateti getPartida() {
		return partida;
	}
	public void setPartida(InterfazTateti partida) {
		this.partida = partida;
	}
	
}
