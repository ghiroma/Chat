package server;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;

import common.FriendStatus;
import common.UserMetaData;

import dataTier.DataAccess;
import events.StatusChangedEvent;

public class ClientEventListener implements EventListener {

	//Lista de los handlers del resto de las conexiones
	private HashMap<String, ClientHandler> handlerList;
	private ArrayList<ClientHandler> friendsOnline;
	private UserMetaData user;

	/* Constructores */
	public ClientEventListener(HashMap<String,ClientHandler> handlerList, UserMetaData user) {
		this.handlerList = handlerList;
		this.user = user;
		setFriendsOnline();
	}

	/* Metodos */
	public void statusChanged(StatusChangedEvent e){
		//TODO Nico: Avisa a todos los amigos conectados

		/* A cada handler de amigo conectado le envio se�al de cambio de estado */
		Iterator<ClientHandler> it = friendsOnline.iterator();
		while(it.hasNext()) {
			ClientHandler clientHandler = it.next();
			clientHandler.friendStatusUpdate(user.getUser(), e.getEstado());
		}
	}

	private void setFriendsOnline(){
		//Cargo los amigos online en una lista
		ArrayList<FriendStatus> friendList = DataAccess.getInstance().getFriends(user);
		Iterator<FriendStatus> itFriend = friendList.iterator();

		while(itFriend.hasNext()){
			String friend = ((FriendStatus)itFriend.next()).getUsername();
			ClientHandler clientHandler = handlerList.get(friend);
			friendsOnline.add(clientHandler);
		}
	}

}
