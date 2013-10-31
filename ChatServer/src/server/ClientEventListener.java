package server;

import java.util.*;

public class ClientEventListener implements EventListener {
	ArrayList<ClientHandler> handlerList;		//Lista de los handlers del resto de las conexiones
	private String username;
	
	/* Constructores */
	public ClientEventListener(ArrayList<ClientHandler> handlerList, String username){
		this.handlerList = handlerList;
		this.username = username;
	}
	
	/* Metodos */
	public void statusChanged(EventObject e){
		//TODO Nico: Avisa a todos los amigos conectados
		
		/* Obtengo lista de amigos conectados */
		/* A cada handler de amigo conectado le envio señal de cambio de estado */
		
	}
}
