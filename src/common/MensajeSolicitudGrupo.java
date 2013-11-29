package common;

import java.io.Serializable;

public class MensajeSolicitudGrupo implements Serializable {

	private static final long serialVersionUID = 43672049852252829L;

	private String grupo;
	private String user;

	public MensajeSolicitudGrupo(String grupo, String user){
		this.grupo=grupo;
		this.user=user;
	}

	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}

}
