package common;

import java.io.Serializable;

public class Mensaje implements Serializable {

	private static final long serialVersionUID = -161453018045089683L;

	/* Server */
	public static final int BANNED = 9; 
	public static final int ACCEPTED = 11;
	public static final int DENIED = 12;
	public static final int USUARIO_CONECTADO = 22;

	/* Client */
	public static final int ALTA_USUARIO = 1;
	public static final int MODIFICACION_USUARIO = 2;
	public static final int OBTENER_USUARIO = 3;
	public static final int CAMBIO_ESTADO = 5;
	public static final int ENVIAR_MENSAJE = 6;
	public static final int BUSCAR_USUARIO = 7; 
	public static final int INVITAR_USUARIO = 8;
	public static final int LOG_IN = 10;
	public static final int READY = 13;
	public static final int ALIVE = 14;
	public static final int ALERTA = 15;
	public static final int VERIFICAR_USUARIO = 16;
	public static final int ACEPTACION_INVITACION_AMIGO = 17;
	public static final int CERRAR_SESION = 18;

	// Inicio: TATETI
	public static final int INVITACION_JUEGO = 19;
	public static final int RESPUESTA_INVITACION_JUEGO = 20;
	public static final int INICIO_PARTIDA = 21;
	public static final int ENVIO_PARTIDA = 22;
	public static final int MOVIMIENTO = 23;
	public static final int CANTIDAD_PARTIDAS_VALIDA = 24;
	public static final int RESPUESTA_CONSULTA_PARTIDAS = 25;
	public static final int ENVIO_PIZARRA = 26;
	public static final int RESPUESTA_PIZARRA = 27;
	public static final int ACTUALIZACION_PIZARRA = 28;
	public static final int RESPUESTA_ACTUALIZACION_PIZARRA = 29;
	// Fin: TATETI

	// Inicio: GRUPOS
	public static final int CREAR_GRUPO = 100;
	// Fin: GRUPOS


	private int id;
	private Object cuerpo;

	/* Constructores */
	public Mensaje() {
		this(0,null);
	}
	public Mensaje(int id, Object cuerpo) {
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
