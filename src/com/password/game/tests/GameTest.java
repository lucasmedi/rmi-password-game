package com.password.game.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import com.game.password.Color;
import com.game.password.Game;
import com.game.password.Result;

public class GameTest {

	@Test
	public void WinTest() {
		Game g = new Game(new Color[] { Color.Red, Color.Green, Color.Blue, Color.Purple });
		
		Color[] attempt = new Color[] { Color.Red, Color.Green, Color.Blue, Color.Purple };
		
		Result res = g.Try(attempt);
		
		System.out.println("WinTest = " + res); 
		
		assertTrue(res.equals(new Result(4, 0, 0)));
	}
	
	@Test
	public void AlmostWinTest() {
		Game g = new Game(new Color[] { Color.Red, Color.Green, Color.Blue, Color.Purple });
		
		Color[] attempt = new Color[] { Color.Red, Color.Green, Color.Blue, Color.Yellow };
		
		Result res = g.Try(attempt);
		
		System.out.println("AlmostWinTest = " + res);
		
		assertTrue(res.equals(new Result(3, 0, 1)));
	}
	
	@Test
	public void NoGreyTest() {
		Game g = new Game(new Color[] { Color.Red, Color.Green, Color.Blue, Color.Purple });
		
		Color[] attempt = new Color[] { Color.Red, Color.Green, Color.Purple, Color.Blue };
		
		Result res = g.Try(attempt);
		
		System.out.println("NoGreyTest = " + res);
		
		assertTrue(res.equals(new Result(2, 2, 0)));
	}
	
	@Test
	public void NoWhiteTest() {
		Game g = new Game(new Color[] { Color.Purple, Color.Blue, Color.Yellow, Color.Red });
		
		Color[] attempt = new Color[] { Color.Red, Color.Purple, Color.Blue, Color.Yellow };
		
		Result res = g.Try(attempt);
		
		System.out.println("NoWhiteTest = " + res);
		
		assertTrue(res.equals(new Result(0, 4, 0)));
	}
	
	@Test
	public void HalfWayThereTest() {
		Game g = new Game(new Color[] { Color.Red, Color.Green, Color.Blue, Color.Red });
		
		Color[] attempt = new Color[] { Color.Red, Color.Purple, Color.Yellow, Color.Red };
		
		Result res = g.Try(attempt);
		
		System.out.println("HalfWayThereTest = " + res);
		
		assertTrue(res.equals(new Result(2, 0, 2)));
	}
	
	@Test
	public void TotalLossTest() {
		Game g = new Game(new Color[] { Color.Green, Color.Green, Color.Green, Color.Green });
		
		Color[] attempt = new Color[] { Color.Purple, Color.Blue, Color.Yellow, Color.Red };
		
		Result res = g.Try(attempt);
		
		System.out.println("TotalLossTest = " + res);
		
		assertTrue(res.equals(new Result(0, 0, 4)));
	}
}