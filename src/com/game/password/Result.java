package com.game.password;

public class Result {
	private int blacks;
	private int whites;
	private int greys;
	
	public Result(int blacks, int whites, int greys) {
		this.blacks = blacks;
		this.whites = whites;
		this.greys = greys;
	}
	
	public int getBlacks() {
		return blacks;
	}
	
	public int getWhites() {
		return whites;
	}
	
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