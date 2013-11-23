package dataTier;

import java.io.Serializable;

public class BanInfo implements Serializable{
	private String motivo;
	private int dias;
	
	/* Constructores */
	public BanInfo(int dias, String motivo){
		this.dias = dias;
		this.motivo = motivo;
	}
	
	/* Getters & Setters */
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public int getDias() {
		return dias;
	}
	public void setDias(int dias) {
		this.dias = dias;
	}
}
