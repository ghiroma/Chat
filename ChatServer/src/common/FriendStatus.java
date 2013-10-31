package common;

public class FriendStatus {
	private String username;
	private int estado;
	
	/* Constructor */
	public FriendStatus(String username, int estado){
		this.username = username;
		this.estado = estado;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
}
