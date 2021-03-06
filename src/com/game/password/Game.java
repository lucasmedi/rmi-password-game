package com.game.password;

import java.util.ArrayList;
import java.util.UUID;

import com.game.infra.Color;
import com.game.infra.IResult;

public class Game {
	
	private UUID userId;
	private String username;
	
	private Match current;
	private ArrayList<Match> history;
	
	public Game(String username) {
		userId = UUID.randomUUID();
		this.username = username;
		
		this.history = new ArrayList<Match>();
	}
	
	public String getUserId() {
		return userId.toString();
	}
	
	public String getUsername() {
		return username;
	}
	
	public boolean startGame() {
		if (current != null) {
			history.add(current);
		}
		
		current = new Match();
		
		return true;
	}
	
	public boolean startGame(Color[] answer) {
		if (current != null) {
			history.add(current);
		}
		
		current = new Match(answer);
		
		return true;
	}
	
	public boolean finishGame() {
		if (current != null) {
			history.add(current);
		}
		
		current = null;
		
		return true;
	}
	
	public IResult tryAnswer(Color[] attempt) {
		if (current == null) {
			current = new Match();
		}
		
		return current.tryAnswer(attempt);
	}
}