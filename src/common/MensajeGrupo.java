package common;

import java.io.Serializable;

import groups.Grupo;

public class MensajeGrupo implements Serializable {

	private static final long serialVersionUID = -2930447901164300526L;

	private String nombreGrupo;
	private Grupo grupo;
	private String emisor;
	private String mensaje;
	private String destinatarioIndividual; // Solo para mensajes individuales
	private int codigoMensaje; // Solo para uso individual

	public MensajeGrupo(Grupo grupo, String emisor, String mensaje) {
		this.grupo = grupo;
		this.emisor = emisor;
		this.mensaje = mensaje;
	}

	public MensajeGrupo(String nombreGrupo, String emisor, String mensaje) {
		this.setNombreGrupo(nombreGrupo);
		this.emisor = emisor;
		this.mensaje = mensaje;
	}

	public MensajeGrupo(String nombreGrupo, String emisor, String receptor, int mensaje) {
		this.setNombreGrupo(nombreGrupo);
		this.emisor = emisor;
		this.codigoMensaje = mensaje;
		this.destinatarioIndividual = receptor;
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

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}
	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public String getDestinatarioIndividual() {
		return destinatarioIndividual;
	}
	public void setDestinatarioIndividual(String destinatarioIndividual) {
		this.destinatarioIndividual = destinatarioIndividual;
	}

	public int getCodigoMensaje() {
		return codigoMensaje;
	}
	public void setCodigoMensaje(int codigoMensaje) {
		this.codigoMensaje = codigoMensaje;
	}

}
