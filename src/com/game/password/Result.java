package com.game.password;

import java.io.Serializable;

import com.game.infra.IResult;

public class Result implements IResult, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int blacks;
	private int whites;
	private int greys;
	
	private int score;
	
	public Result(int blacks, int whites, int greys, int score) {
		this.blacks = blacks;
		this.whites = whites;
		this.greys = greys;
		
		this.score = score;
	}
	
	@Override
	public boolean succeeded() {
		return (this.blacks == 4);
	}
	
	@Override
	public int getScore() {
		return this.score;
	}
	
	@Override
	public int getBlacks() {
		return blacks;
	}
	
	@Override
	public int getWhites() {
		return whites;
	}
	
	@Override
	public int getGreys() {
		return greys;
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof Result)) {
			return false;
		}

		Result otherRes = (Result)other;

		return (this.getBlacks() == otherRes.getBlacks()
			&& this.getWhites() == otherRes.getWhites()
			&& this.getGreys() == otherRes.getGreys()
		);
	}
	
	public String toString() {
		return "Result: (" + this.getBlacks() + ", " + this.getWhites() + ", " + this.getGreys() + ")"; 
	}
}