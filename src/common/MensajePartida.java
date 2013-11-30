package common;

import interfaces.tateti.InterfazTateti;

import java.io.Serializable;

public class MensajePartida implements Serializable {

	private static final long serialVersionUID = 47158508574929960L;

	private String jugador1;
	private String jugador2;
	private String proxTurno;
	
	/* Constructores */
	public MensajePartida(String j1,String j2, String pt) {
		this.jugador1 = j1;
		this.jugador2 = j2;
		this.proxTurno = pt;
	}
	public MensajePartida() {
		this("","","");
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
	public String getProxTurno() {
		return proxTurno;
	}
	public void setProxTurno(String proxTurno) {
		this.proxTurno = proxTurno;
	}
	
}
