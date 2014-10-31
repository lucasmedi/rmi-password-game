package com.game.infra;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IControllerServer extends Remote {
	
	public String login(String username) throws RemoteException;
	
	public void logout(String userId) throws RemoteException;
	
	public void startGame(String userId) throws RemoteException;
	
	public void startGame(String userId, Color[] answer) throws RemoteException;
	
	public void finishGame(String userId) throws RemoteException;
	
	public IResult tryAnswer(String userId, Color[] attempt) throws RemoteException;
	
	public String[] getLeaderboard() throws RemoteException;

}