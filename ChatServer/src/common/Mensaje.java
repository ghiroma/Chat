package common;

import java.io.*;

public class Mensaje implements Serializable{
	 
	/* Server */ 
	public static final int BANNED = 9; 
	public static final int ACCEPTED = 11;
	public static final int DENIED = 12;
	
	/* Client */
	public static final int ALTA_USUARIO = 1;
	public static final int MODIFICACION_USUARIO = 2;
	  public static final int BAJA_USUARIO = 3;			    //TODO este va?
	  public static final int REINICIO_CLAVE = 4;			//TODO   ^
	public static final int CAMBIO_ESTADO = 5;
	public static final int ENVIAR_MENSAJE = 6;
	public static final int BUSCAR_USUARIO = 7; 
	public static final int INVITAR_USUARIO = 8;
	public static final int LOG_IN = 10;
	public static final int READY = 13;

	 
	private int id;
	private Object cuerpo;
	
	/* Constructores */
	public Mensaje()
	{
		this(0,null);
	}
	
	public Mensaje(int id, Object cuerpo)
	{
		this.id = id;
		this.cuerpo = cuerpo;
	}
	
	
	/* Getters & Setters */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Object getCuerpo() {
		return cuerpo;
	}
	public void setCuerpo(Object cuerpo) {
		this.cuerpo = cuerpo;
	}
	
	
}
