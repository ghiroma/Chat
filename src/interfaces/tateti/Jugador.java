package interfaces.tateti;

public class Jugador {

	/* Atributos */
	private String nombre;
	private int idTurno;
	
	/* Constructores */
	public Jugador() {
		this("",-1);
	}
	
	public Jugador(String nombre, int id) {
		this.nombre = nombre;
		this.idTurno = id;
	}

	/* Metodos */
	
	
	
	/* Getters && Setters */
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(int idTurno) {
		this.idTurno = idTurno;
	}
	
}
