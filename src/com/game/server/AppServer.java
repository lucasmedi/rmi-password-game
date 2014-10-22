package com.game.server;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class AppServer {

	public static void main (String[] args) {
		try {
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			System.out.println("RMI registry ready.");
		} catch (RemoteException e) {
			System.out.println("RMI registry already running.");
		}

		try {
			Naming.rebind("PasswordGame", new ControllerServer());
			System.out.println ("Game server is ready.");
		} catch (Exception e) {
			System.out.println ("Game server failed:");
			e.printStackTrace();
		}
	}
}