package com.game.infra;

public interface IResult {
	
	public boolean succeeded();
	
	public int getBlacks();
	
	public int getWhites();
	
	public int getGreys();
	
	public int getScore();
	
}