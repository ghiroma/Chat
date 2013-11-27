package groups;

import java.io.Serializable;

public class ClienteGrupo implements Serializable{

	private static final long serialVersionUID = -3164209432590580019L;

	private String nombre;
	private boolean baneado;
	private boolean mod;
	private boolean online;


	public ClienteGrupo(String nombre, boolean mod, boolean online) {
		this.nombre = nombre;
		this.baneado = false;
		this.mod = mod;
		this.online = true;
	}

	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}

	public boolean isMod() {
		return mod;
	}
	public void setMod(boolean mod) {
		this.mod = mod;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isBaneado() {
		return baneado;
	}
	public void setBaneado(boolean baneado) {
		this.baneado = baneado;
	}

}
