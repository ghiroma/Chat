package common;

import groups.Grupo;

public class MensajeGrupo {
	private Grupo grupo;
	private String emisor;
	private String mensaje;
	public MensajeGrupo(Grupo grupo,String emisor,String mensaje){
		this.grupo=grupo;
		this.emisor=emisor;
		this.mensaje=mensaje;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	public String getEmisor() {
		return emisor;
	}
	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}
}
