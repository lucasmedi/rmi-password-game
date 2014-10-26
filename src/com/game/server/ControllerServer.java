package com.game.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.game.infra.Color;
import com.game.infra.IControllerServer;
import com.game.infra.IResult;
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
	
	@Override
	public String login(String username) throws RemoteException {
		Game g = new Game(username);
		
		games.add(g);
		
		return g.getUserId();
	}
	
	@Override
	public void logout(String userId) throws RemoteException {
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
	
	@Override
	public void startGame(String userId) throws RemoteException {
		Game g = getGame(userId);
		g.startGame();
	}
	
	@Override
	public IResult tryAnswer(String userId, Color[] attempt) throws RemoteException {
		Game g = getGame(userId);
		
		IResult res = g.tryAnswer(attempt);
		
		if (res.succeeded()) {
			addToLeaderboard(g.getUsername(), res.getScore());
		}
		
		return res;
	}
	
	@Override
	public void finishGame(String userId) throws RemoteException {
		
	}
	
	private Game getGame(String userId) {
		for (Game game : games) {
			if (game.getUserId().equals(userId)) {
				return game;
			}
		}
		
		return null;
	}
	

	private void addToLeaderboard(String username, int score) {
		for (int i = 0; i < leaderboard.size(); i++) {
			if (score > leaderboard.get(i).getScore()) {
				leaderboard.add(i, new Score(username, score));
			}
		}
	}
}