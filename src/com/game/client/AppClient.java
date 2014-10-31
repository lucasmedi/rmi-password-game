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
	private static String name;
	
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
		
		name = null;
		
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
			System.out.println("2- Start a new test game");
			System.out.println("3- Take a look at the leaderboards");
			System.out.println("4- Rules of the game");
			System.out.println("5- Say good bye and leave :(");
			System.out.print("What would you like to do? ");
			option = sc.nextLine();
			
			if (!StringUtils.isEmpty(option)) {
				confirm = option.equals("1")
					|| option.equals("2")
					|| option.equals("3")
					|| option.equals("4")
					|| option.equals("5");
			}
			
			if (!confirm) {
				continue;
			}
		
			boolean leave = false;
			
			confirm = false;
			switch (option) {
				case "1":
					leave = false;
					
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
					leave = false;
					
					int times = 0;
					
					Color[] answer = askForAnswer(sc, times);
					game.startGame(userId, answer);
					times++;
					
					do {
						leave = playGame(sc);
						
						if (!leave) {
							System.out.print("Wanna play again? (S/N) ");
							String input = sc.nextLine();
							
							if (input.toUpperCase().equals("N")) {
								leave = true;
							} else {
								answer = askForAnswer(sc, times);
								game.startGame(userId, answer);
								times++;
							}
						}
					} while(!leave);
					
					game.finishGame(userId);
					
					break;
				case "3":
					seeLeaderboard();
					break;
				case "4":
					showRules();
					break;
				case "5":
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
		
		System.out.println("\nLet's play the game then!");
		
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
		
		System.out.println("OK, cheater, let's begin");
		return (attempt.toLowerCase().equals("quit"));
	}
	
	private static Color[] askForAnswer(Scanner sc, int times) {
		Color[] answer = null;
		String pswdString = null;
		
		if (times == 0) {
			System.out.println("\n** Test game **");
		} else if (times == 1) {
			System.out.println("\n** Test game ** Don't you believe me?");
		} else if (times == 2) {
			System.out.println("\n** Test game ** You are cheating, aren't you?");
		} else if (times >= 3 && times < 6) {
			System.out.println("\n** Test game ** Keep going like that and you'll win a cheater trophy");
		} else if (times == 6) {
			System.out.println("\n** Test game ** Cheater trophy earned!");
		} else {
			System.out.println("\n** Test game ** Welcome back, " + name + ", The Legendary Cheater!");
		}
		
		boolean leave = false;
		
		do {
			System.out.print("Tell me the password you want to play with: ");
			pswdString = sc.nextLine();
					
			if (StringUtils.isEmpty(pswdString)) {
				System.out.println("You need to give me something to work with.");
				System.out.println("Let's try again...");
				continue;
			}
			
			if (pswdString.length() > 4) {
				System.out.println("That's not quite what we expected, take a look at this attemp example: RYGB.");
				System.out.println("Let's try again...");
				continue;
			}
			
			if (!pswdString.matches("[RYGBPK]{4}")) {
				System.out.println("That's not quite what we expected, take a look at this attemp example: RYGB.");
				System.out.println("Let's try again...");
				continue;
			}
			
			answer = colorize(pswdString);
			
			leave = (answer != null);
		} while(!leave);
		
		return answer;
	}
	
	private static void seeLeaderboard() throws RemoteException {
		System.out.println("\nWho are the top 10?");
		String[] leaderboard = game.getLeaderboard();
		for (String score : leaderboard) {
			if (score != null) {
				System.out.println(score);
			}
		}
		System.out.println("And that's it!\n");
	}
	
	private static void leaveGame() throws RemoteException {
		game.logout(userId);
		System.out.println("Till next time! See ya!");
	}
	
	private static void showRules() {
		System.out.println("\nRules are:");
		System.out.println("1- The answer is a combination of 4 colors of the 6 colors available.");
		System.out.println("2- The colors do not repeat themselves in the same answer.");
		System.out.println("3- The available colors are: (R)ed, (Y)ellow, (G)reen, (B)lue, (P)urple and Pin(K)");
		System.out.println("4- You have infinite amout of tries.");
		System.out.println("And that's it! Simple as that!");
	}
	
	private static void howToPlay() {
		System.out.println("** Just in case you feel a little bit lost **");
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