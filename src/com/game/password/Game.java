package com.game.password;

import java.util.ArrayList;
import java.util.UUID;

public class Game {
	
	private int answerLength = 4;
	private int triesLeft;
	
	private Color[] answer;
	
	public UUID userId;
	public String username;
	
	public Game() {
		answer = new Color[] { Color.Red, Color.Green, Color.Red, Color.Green };
		triesLeft = 5;
		
		userId = UUID.randomUUID();
	}
	
	public Game(Color[] answer) {
		this.answer = answer;
		triesLeft = 5;
	}
	
	public String getUserId() {
		return userId.toString();
	}
	
	public String getUsername() {
		return username;
	}
	
	public Result Try(Color[] attempt) {
		int blacks = 0;
		int whites = 0;
		
		ArrayList<Integer> excluded = new ArrayList<Integer>();
		
		// search for matches
		for (int i = 0; i < attempt.length; i++) {
			if (attempt[i] == answer[i]) {
				blacks++;
				excluded.add(i);
			}
		}
		
		// search for near
		for (int i = 0; i < attempt.length; i++) {
			for (int j = 0; j < answer.length; j++) {
				if (excluded.contains(j)) {
					continue;
				}
				
				if (attempt[i] == answer[j]) {
					whites++;
					excluded.add(j);
					continue;
				}
			}
		}
		
		triesLeft--;
		
		Result res = new Result(blacks, whites, (answerLength - blacks - whites));
		res.setTriesLeft(triesLeft);
		
		return res;
	}
}