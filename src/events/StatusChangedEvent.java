package events;

import java.util.EventObject;

public class StatusChangedEvent extends EventObject {

	private static final long serialVersionUID = -7224647642445948416L;

	private String username;
	private int estado;

	/* Constructores */
	public StatusChangedEvent(Object source) {
		super(source);
	}

	public StatusChangedEvent(Object source, String username, int estado) {
		super(source);
		this.username = username;
		this.estado = estado;
	}

	public String getUsername() {
		return username;
	}
	public int getEstado() {
		return estado;
	}

}
