package com.game.client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

import com.game.infra.IControllerServer;
import com.game.infra.StringUtils;

public class AppClient {
	private static IControllerServer game;
	
	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(System.in);
			
			showConsole(sc);
			
			sc.close();
		} catch (RemoteException e) {
			// TODO: handle RemoteException
		} catch (Exception e) {
			// TODO: handle generic exception
		}
	}
	
	private static void showConsole(Scanner sc) throws RemoteException {
		boolean first = true;
		boolean confirm = false;
		
		String name = null;
		
		System.out.println("***** Welcome to the awesome password game! *****");
		
		do {
			do {
				if (first) {
					if (StringUtils.isEmpty(name)) {
						System.out.print("Please tell us your name: ");
					} else {
						System.out.print("So, tell us your real name then: ");
					}
					
					first = false;
				} else {
					System.out.print("You did not tell us your name. Please tell us your name: ");
				}
				
				name = sc.nextLine();
			} while (StringUtils.isEmpty(name));
		
			if (name.equals("Doctor")) {
				System.out.print("Doctor? Doctor Who? (S/N) ");				
			} else {
				System.out.print("So your name is " + name + ", right? (S/N) ");
			}
			
			String input = sc.nextLine();
			if (input.toUpperCase().equals("S")) {
				confirm = true;
			}
		} while (!confirm);
		
		System.out.println("OK " + name + ", we are ready to go!");
		showMenu(sc);
	}
	
	private static void showMenu(Scanner sc) {
		String option = null;
		boolean confirm = false;
		do {
			System.out.println("1- Start a new game :)");
			System.out.println("2- Take a look at the leaderboards");
			System.out.println("3- Say good bye and leave :(");
			System.out.print("What would you like to do? ");
			option = sc.nextLine();
			
			if (!StringUtils.isEmpty(option)) {
				confirm = option.equals("1")
					|| option.equals("2")
					|| option.equals("3");
			}
			
			if (!confirm) {
				continue;
			}
		
			confirm = false;
			switch (option) {
				case "1":
					playGame();
					break;
				case "2":
					seeLeaderboards();
					break;
				case "3":
					leaveGame();
					confirm = true;
					break;
				default:
					System.out.println("I think you should choose a valid option. :)");
					break;
			}
			
		} while (!confirm);
	}
	
	private static void playGame() {
		System.out.println("Let's play a game then!");
		
		
		//game = GameFactory.createGame(); 
		//String userId = game.start(name);
		
		//System.out.println(userId);
		
		//n = game.obtemNota(args[1]);
		
		//Game game = new Game();
		//Color[] attempt = new Color[] { Color.Green, Color.Red, Color.Green, Color.Red };
		//game.Try(attempt);
		
		//System.out.println ("Name: " + args[1]);
		//if	(n < -1.0)
		//	System.out.println ("Resultado: nome nao encontrado!\n");
		//else
		//	System.out.println ("Nota: "+n);
	}
	
	private static void seeLeaderboards() {
		System.out.println("Who's the best?");
		
	}
	
	private static void leaveGame() {
		System.out.println("See ya!");
		
	}
}