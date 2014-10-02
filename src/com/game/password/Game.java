package com.game.password;

import java.util.ArrayList;

public class Game {
	
	private int answerLength = 4;
	private int triesLeft;
	
	private Color[] answer; 
	
	public Game() {
		answer = new Color[] { Color.Red, Color.Green, Color.Red, Color.Green };
		triesLeft = 5;
	}
	
	public Game(Color[] answer) {
		this.answer = answer;
		triesLeft = 5;
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
		
		Result r = new Result(blacks, whites, (answerLength - blacks - whites));
		return r;
	}
}