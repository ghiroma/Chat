package common;

import java.io.Serializable;

public class MensajeInvitacion implements Serializable {

	private static final long serialVersionUID = 47158500574929960L;

	private String solicitante;
	private String invitado;

	/* Constructores */
	public MensajeInvitacion(String solicitante,String invitado) {
		this.solicitante = solicitante;
		this.invitado = invitado;
	}
	public MensajeInvitacion() {
		this("","");
	}

	/* Metodos */


	/* Getters & Setters */
	public String getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	public String getInvitado() {
		return invitado;
	}
	public void setInvitado(String invitado) {
		this.invitado = invitado;
	}
}
