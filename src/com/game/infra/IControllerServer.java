package com.game.infra;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IControllerServer extends Remote {
	
	public String start(String username) throws RemoteException;
	
}