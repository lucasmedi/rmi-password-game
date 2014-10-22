package com.game.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.game.infra.IControllerServer;
import com.game.password.*;

public class ControllerServer extends UnicastRemoteObject implements IControllerServer {

	private static final long serialVersionUID = 1L;

	private ArrayList<Game> games;
	private ArrayList<Score> leaderboard; 
	
	protected ControllerServer() throws RemoteException {
		super();
		
		games = new ArrayList<Game>();
		leaderboard = new ArrayList<Score>();
	}
	
	public String start(String username) {
		Game g = new Game();
		
		games.add(g);
		
		return g.getUserId();
	}
	
	public void leave(String userId) {
		Game g = null;
		
		for (Game game : games) {
			if (game.getUserId() == userId) {
				g = game;
				break;
			}
		}
		
		if (g != null) {
			games.remove(g);
		} 
	}
	
	private void finish(int userId) {
		
	}
}