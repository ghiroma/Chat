package groups;

import java.io.Serializable;
import java.util.List;

public class Grupo implements Serializable {

	private static final long serialVersionUID = 101439956426392483L;

	private String nombre;
	private String moderador;
	private List<String> usuarios;

	public Grupo(String nombre, String moderador, List<String> usuarios) {
		this.nombre = nombre;
		this.moderador = moderador;
		this.usuarios = usuarios;
	}

	public Grupo(String nombre, List<String> usuarios) {
		this.nombre = nombre;
		this.usuarios = usuarios;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getModerador() {
		return moderador;
	}
	public void setModerador(String moderador) {
		this.moderador = moderador;
	}

	public List<String> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<String> usuarios) {
		this.usuarios = usuarios;
	}

}
