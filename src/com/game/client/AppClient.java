package com.game.client;

import java.rmi.RemoteException;
import java.util.Scanner;

import com.game.infra.Color;
import com.game.infra.IControllerServer;
import com.game.infra.IResult;
import com.game.infra.StringUtils;

public class AppClient {
	private static IControllerServer game;
	private static String userId;
	
	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(System.in);
			
			showConsole(sc);
			
			sc.close();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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
		
		System.out.println("Creating game session, please hold on a sec...");
		
		game = GameFactory.createGame();
		userId = game.login(name);
		
		System.out.println("OK " + name + ", we are ready to go!");
		showMenu(sc);
	}
	
	private static void showMenu(Scanner sc) throws RemoteException {
		String option = null;
		boolean confirm = false;
		do {
			System.out.println("1- Start a new game :)");
			System.out.println("2- Take a look at the leaderboards");
			System.out.println("3- Rules of the game");
			System.out.println("4- Say good bye and leave :(");
			System.out.print("What would you like to do? ");
			option = sc.nextLine();
			
			if (!StringUtils.isEmpty(option)) {
				confirm = option.equals("1")
					|| option.equals("2")
					|| option.equals("3")
					|| option.equals("4");
			}
			
			if (!confirm) {
				continue;
			}
		
			confirm = false;
			switch (option) {
				case "1":
					boolean leave = false;
					game.startGame(userId);
					
					do {
						leave = playGame(sc);
						
						if (!leave) {
							System.out.print("Wanna play again? (S/N) ");
							String input = sc.nextLine();
							
							if (input.toUpperCase().equals("N")) {
								leave = true;
							} else {
								game.startGame(userId);
							}
						}
					} while(!leave);
					
					game.finishGame(userId);
					
					break;
				case "2":
					seeLeaderboard();
					break;
				case "3":
					showRules();
					break;
				case "4":
					leaveGame();
					confirm = true;
					break;
				default:
					System.out.println("I think you should choose a valid option. :)");
					break;
			}
			
		} while (!confirm);
	}
	
	private static boolean playGame(Scanner sc) throws RemoteException {
		howToPlay();
		
		System.out.println("Let's play the game then!");
		
		boolean leave = false;
		Integer tries = 1;
		String attempt = "";
		
		do {
			System.out.println("> Try " + tries + ":");
			System.out.print("Say it (or 'quit' finish current match): ");
			attempt = sc.nextLine();
			
			if (StringUtils.isEmpty(attempt)) {
				System.out.println("If you don't make an attempt, it's quite logical that you won't ever win the game.");
				System.out.println("Let's try again...");
				continue;
			}
			
			if (attempt.equals("quit")) {
				game.finishGame(userId);
				System.out.println("Ok then. Back to the menu...");
				leave = true;
				continue;
			}
			
			if (attempt.length() > 4) {
				System.out.println("That's not quite what we expected, take a look at this attemp example: RYGB.");
				System.out.println("Let's try again...");
				continue;
			}
			
			if (!attempt.matches("[RYGBPK]{4}")) {
				System.out.println("That's not quite what we expected, take a look at this attemp example: RYGB.");
				System.out.println("Let's try again...");
				continue;
			}
			
			IResult res = game.tryAnswer(userId, colorize(attempt));
			if (res.succeeded()) {
				System.out.println("Congrats! You nailed it! Your score was: " + res.getScore());
				leave = true;
			} else {
				System.out.println("Let's see, you got: " + res.getBlacks() + " blacks and " + res.getWhites() + " whites. Try again...");
			}
				
			tries++;
		} while(!leave);
		
		return (attempt.toLowerCase().equals("quit"));
	}
	
	private static void seeLeaderboard() throws RemoteException {
		System.out.println("Who are the top 10?");
		String[] leaderboard = game.getLeaderboard();
		for (String score : leaderboard) {
			if (score != null) {
				System.out.println(score);
			}
		}
		System.out.println("And that's it!");
	}
	
	private static void leaveGame() throws RemoteException {
		game.logout(userId);
		System.out.println("See ya!");
	}
	
	private static void showRules() {
		System.out.println("Rules are:");
		System.out.println("1- The answer is a combination of 4 colors of the 6 colors available.");
		System.out.println("2- The colors do not repeat themselves in the same answer.");
		System.out.println("3- The available colors are: (R)ed, (Y)ellow, (G)reen, (B)lue, (P)urple and Pin(K)");
		System.out.println("4- You have infinite amout of tries.");
		System.out.println("And that's it! Simple as that!");
	}
	
	private static void howToPlay() {
		System.out.println("Just in case you feel a little bit lost:");
		System.out.println("Colors are: (R)ed, (Y)ellow, (G)reen, (B)lue, (P)urple and Pin(K)");
		System.out.println("Attempt example: RYGB");
	}
	
	private static Color[] colorize(String attempt) {
		Color[] colors = new Color[4];
		
		char[] cArray = attempt.toCharArray();
		
		for (int i = 0; i < cArray.length; i++) {
			switch (cArray[i]) {
				case 'R':
					colors[i] = Color.Red;
					break;
				case 'Y':
					colors[i] = Color.Yellow;
					break;
				case 'G':
					colors[i] = Color.Green;
					break;
				case 'B':
					colors[i] = Color.Blue;
					break;
				case 'P':
					colors[i] = Color.Purple;
					break;
				case 'K':
					colors[i] = Color.Pink;
					break;
				default:
					break;
			}
		}
		
		return colors;
	}
}