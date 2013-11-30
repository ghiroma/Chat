package interfaces.tateti;

import java.io.Serializable;

/* Esta clase es la que posee informacion general de la pizarra, el numero de jugadas, la matriz de la tabla y si finalizo o no la partida */

public class Blackboard implements Serializable {

	/* Atributos  */
	private int nroJugadas;
	private int table[][] = new int[3][3];
	public boolean gameOver;
	private boolean hayGanador = false;
	private String user;
	private String rival;
	private String proxturno;
	private int id;
	
	/* Constructores */
	public Blackboard(){
		nroJugadas = 0;
		gameOver = false;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				table[i][j] = -1;
			}
		}
	}
	
	/* Metodos */
	public int update(int i, int j, int id) {
		// 1 - Hay Ganador
		// 0 - Empate 
		// -1 - No paso nada
		nroJugadas++;		
		table[i][j] = id;						
		if (hayGanador(id) || nroJugadas == 9) {
			gameOver = true;
			if(hayGanador(id) == true)
				return 1;
			else
				return 0;
		}		
		return -1;
	}
	
	public boolean inspect(int i, int j, int id) {
		if((!gameOver) && (table[i][j] == -1))	// Pregunto por si puedo realizar movimiento, para eso el juego no debe estar terminado y la posicion nunca se tuvo que haber elegido
			return true;	
		else
			return false;
	}
	
	public boolean hayGanador(int id) {			// Verifico que alguna de las soluciones es true
		if (nroJugadas >= 5) {   				//empiezo a controlar si hay ganador despues de la 5ta jugada
			if (solucion1(id) || solucion2(id) || solucion3(id) || solucion4(id) || solucion5(id) || solucion6(id) || solucion7(id) || solucion8(id))
				return true;
			else 
				return false;
		}
		else
			return false;
	}
	
	public boolean getHayGanador() {
		return hayGanador;
	}
	
	/* Soluciones */
	private boolean solucion1(int id) {
		if ((table[0][0] == id) && (table[0][1] == id) && (table[0][2] == id))
			return true;
		else 
			return false;
	}	
	
	private boolean solucion2(int id) {
		if ((table[1][0] == id) && (table[1][1] == id) && (table[1][2] == id))
			return true;
		else 
			return false;
	}	
	
	private boolean solucion3(int id) {
		if ((table[2][0] == id) && (table[2][1] == id) && (table[2][2] == id))
			return true;
		else 
			return false;
	}
	
	private boolean solucion4(int id) {
		if ((table[0][0] == id) && (table[1][0] == id) && (table[2][0] == id))
			return true;
		else 
			return false;
	}	
	
	private boolean solucion5(int id) {
		if ((table[0][1] == id) && (table[1][1] == id) && (table[2][1] == id))
			return true;
		else 
			return false;
	}
	
	private boolean solucion6(int id) {
		if ((table[0][2] == id) && (table[1][2] == id) && (table[2][2] == id))
			return true;
		else 
			return false;
	}
	
	private boolean solucion7(int id) {
		if ((table[0][0] == id) && (table[1][1] == id) && (table[2][2] == id))
			return true;
		else 
			return false;
	}	
	
	private boolean solucion8(int id) {
		if ((table[0][2] == id) && (table[1][1] == id) && (table[2][0] == id)) 
			return true;
		else 
			return false;
	}	
	/* Fin Soluciones */

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getRival() {
		return rival;
	}

	public void setRival(String rival) {
		this.rival = rival;
	}

	public String getProxturno() {
		return proxturno;
	}

	public void setProxturno(String proxturno) {
		this.proxturno = proxturno;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNroJugadas() {
		return nroJugadas;
	}

	public void setNroJugadas(int nroJugadas) {
		this.nroJugadas = nroJugadas;
	}

}
