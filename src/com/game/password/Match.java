package com.game.password;

import java.util.ArrayList;

import com.game.infra.Color;

public class Match {
	
	private int answerLength = 4;
	private int score;
	
	private Color[] answer;
	
	public Match() {
		answer = new Color[] { Color.Red, Color.Green, Color.Red, Color.Green };
		score = 100;
	}
	
	public Match(Color[] answer) {
		this.answer = answer;
		score = 100;
	}
	
	public Result tryAnswer(Color[] attempt) {
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
		
		// updates de score
		if (blacks != 4) {
			if (score > 0) {
				score -= 10;
			}
		}
		
		return new Result(blacks, whites, (answerLength - blacks - whites), score);
	}
}