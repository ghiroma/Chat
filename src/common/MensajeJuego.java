package common;

import java.io.Serializable;

public class MensajeJuego implements Serializable {

	private String rival;
	private String proxTurno;
	
	public MensajeJuego(String rival, String proxTurno){
		this.rival = rival;
		this.proxTurno = proxTurno;
	}

	public String getRival() {
		return rival;
	}

	public void setRival(String rival) {
		this.rival = rival;
	}

	public String getProxTurno() {
		return proxTurno;
	}

	public void setProxTurno(String proxTurno) {
		this.proxTurno = proxTurno;
	}
	
}
