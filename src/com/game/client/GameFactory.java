package com.game.client;

import java.rmi.Naming;

import com.game.infra.IControllerServer;

public class GameFactory {
	
	public static IControllerServer createGame() {
		IControllerServer game = null;
		
		try {
			game = (IControllerServer)Naming.lookup("//localhost/PasswordGame");
		} catch (Exception e) {
			System.out.println("Game server failed.");
			e.printStackTrace();
		}
		
		return game;
	}
	
}