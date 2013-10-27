package common;

import java.io.Serializable;

public class MensajeChat implements Serializable {
	private String destinatario;
	private String texto;
	
	/* Constructores */
	public MensajeChat(String destinatario,String texto)
	{
		this.destinatario = destinatario;
		this.texto = texto;
	}
	
	public MensajeChat()
	{
		this("","");
	}
	/* Metodos */
	
	/* Getters & Setters */
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
}
