package com.game.password;

import java.util.ArrayList;
import java.util.Random;

import com.game.infra.Color;

public class Match {
	
	private int answerLength = 4;
	private int score;
	
	private Color[] answer;
	
	public Match() {
		ArrayList<Color> t = new ArrayList<Color>();
		
		Random r = new Random();
		boolean leave = false;
		do {
			int n = r.nextInt(6) + 1;
			
			Color color;
			switch (n) {
				case 1:
					color = Color.Red;
					break;
				case 2:
					color = Color.Green;
					break;
				case 3:
					color = Color.Blue;
					break;
				case 4:
					color = Color.Yellow;
					break;
				case 5:
					color = Color.Purple;
					break;
				case 6:
					color = Color.Pink;
					break;
				default:
					color = null;
					break;
			}
			
			if (color != null && !t.contains(color)) {
				t.add(color);
			}
			
			if (t.size() == 4) {
				leave = true;
			}
		} while(!leave);
		
		answer = new Color[4];
		for (int i = 0; i < t.size(); i++) {
			answer[i] = t.get(i);
		}
		
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
		
		// updates the score
		if (blacks != 4) {
			if (score > 0) {
				score -= 10;
			}
		}
		
		return new Result(blacks, whites, (answerLength - blacks - whites), score);
	}
}