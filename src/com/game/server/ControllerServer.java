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
	public void startGame(String userId, Color[] answer) throws RemoteException {
		Game g = getGame(userId);
		g.startGame(answer);
	}
	
	@Override
	public void finishGame(String userId) throws RemoteException {
		Game g = getGame(userId);
		g.finishGame();
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
	public String[] getLeaderboard() throws RemoteException {
		String[] topPlayers = new String[5];
		
		if (this.leaderboard.isEmpty()) {
			return new String[] { "Empty leaderboard :(" };
		}
		
		for (int i = 0; i < this.leaderboard.size(); i++) {
			if (i == 5) {
				i = this.leaderboard.size();
				continue;
			}
			
			Score score = this.leaderboard.get(i);
			if (score != null) {
				topPlayers[i] = score.toString();
			}
		}
		
		return topPlayers;
	}
	
	private Game getGame(String userId) {
		for (Game game : games) {
			if (game.getUserId().equals(userId)) {
				return game;
			}
		}
		
		return null;
	}
	
	private synchronized void addToLeaderboard(String username, int score) {
		if (leaderboard.isEmpty()) {
			leaderboard.add(0, new Score(username, score));
			return;
		}
		
		boolean added = false;
		for (int i = 0; i < leaderboard.size(); i++) {
			if (score > leaderboard.get(i).getScore()) {
				leaderboard.add(i, new Score(username, score));
				added = true;
				break;
			}
		}
		
		if (!added) {
			leaderboard.add(new Score(username, score));
		}
	}
}