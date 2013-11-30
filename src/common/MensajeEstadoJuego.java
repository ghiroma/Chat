package common;

import java.io.Serializable;

public class MensajeEstadoJuego implements Serializable{

	/* Atributos */
	private static final long serialVersionUID = 47158503571929960L;
	private boolean gameOver;
	private boolean hayGanador;
	private boolean hayEmpate;
	private String ganador;
	private String perdedor;
	
	/* Constructores */
	public MensajeEstadoJuego() {
		this(false,false,false,"","");
	}
	
	public MensajeEstadoJuego(boolean gameOver, boolean g, boolean e, String winner, String loser) {
		this.gameOver = gameOver;
		this.hayGanador = g;
		this.hayEmpate = e;
		this.ganador = winner;
		this.perdedor = loser;
	}

	/* Metodos */
	
	/* Getters && Setters */
	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public boolean isHayGanador() {
		return hayGanador;
	}

	public void setHayGanador(boolean hayGanador) {
		this.hayGanador = hayGanador;
	}

	public boolean isHayEmpate() {
		return hayEmpate;
	}

	public void setHayEmpate(boolean hayEmpate) {
		this.hayEmpate = hayEmpate;
	}

	public String getGanador() {
		return ganador;
	}

	public void setGanador(String ganador) {
		this.ganador = ganador;
	}

	public String getPerdedor() {
		return perdedor;
	}

	public void setPerdedor(String perdedor) {
		this.perdedor = perdedor;
	}
		
}
