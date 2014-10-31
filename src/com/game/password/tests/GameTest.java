package com.game.password.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.game.infra.Color;
import com.game.password.Match;
import com.game.password.Result;

public class GameTest {

	@Test
	public void attemptIsValid() {
		String pattern = "[RYGBPK]{4}";
		String attempt = "";
		boolean res = false;
		
		System.out.print("attemptIsValidTest = ");
		
		// Invalid characters
		attempt = "EYOK";
		res = attempt.matches(pattern);
		System.out.print(attempt + " is " + (res ? "valid" : "invalid") + "; ");
		assertFalse(res);
		
		// Invalid number of right characters
		attempt = "RYGBK";
		res = attempt.matches(pattern);
		System.out.print(attempt + " is " + (res ? "valid" : "invalid") + "; ");
		assertFalse(res);
		
		// Valid characters
		attempt = "RYGB";
		res = attempt.matches(pattern);
		System.out.print(attempt + " is " + (res ? "valid" : "invalid") + "; ");
		assertTrue(res);
	}
	
	@Test
	public void winTest() {
		Match m = new Match(new Color[] { Color.Red, Color.Green, Color.Blue, Color.Purple });
		
		Color[] attempt = new Color[] { Color.Red, Color.Green, Color.Blue, Color.Purple };
		
		Result res = m.tryAnswer(attempt);
		
		System.out.println("winTest = " + res); 
		
		assertTrue(res.equals(new Result(4, 0, 0, 0)));
	}
	
	@Test
	public void almostWinTest() {
		Match m = new Match(new Color[] { Color.Red, Color.Green, Color.Blue, Color.Purple });
		
		Color[] attempt = new Color[] { Color.Red, Color.Green, Color.Blue, Color.Yellow };
		
		Result res = m.tryAnswer(attempt);
		
		System.out.println("almostWinTest = " + res);
		
		assertTrue(res.equals(new Result(3, 0, 1, 0)));
	}
	
	@Test
	public void noGreyTest() {
		Match m = new Match(new Color[] { Color.Red, Color.Green, Color.Blue, Color.Purple });
		
		Color[] attempt = new Color[] { Color.Red, Color.Green, Color.Purple, Color.Blue };
		
		Result res = m.tryAnswer(attempt);
		
		System.out.println("noGreyTest = " + res);
		
		assertTrue(res.equals(new Result(2, 2, 0, 0)));
	}
	
	@Test
	public void noWhiteTest() {
		Match m = new Match(new Color[] { Color.Purple, Color.Blue, Color.Yellow, Color.Red });
		
		Color[] attempt = new Color[] { Color.Red, Color.Purple, Color.Blue, Color.Yellow };
		
		Result res = m.tryAnswer(attempt);
		
		System.out.println("noWhiteTest = " + res);
		
		assertTrue(res.equals(new Result(0, 4, 0, 0)));
	}
	
	@Test
	public void halfWayThereTest() {
		Match m = new Match(new Color[] { Color.Red, Color.Green, Color.Blue, Color.Red });
		
		Color[] attempt = new Color[] { Color.Red, Color.Purple, Color.Yellow, Color.Red };
		
		Result res = m.tryAnswer(attempt);
		
		System.out.println("halfWayThereTest = " + res);
		
		assertTrue(res.equals(new Result(2, 0, 2, 0)));
	}
	
	@Test
	public void totalLossTest() {
		Match m = new Match(new Color[] { Color.Green, Color.Green, Color.Green, Color.Green });
		
		Color[] attempt = new Color[] { Color.Purple, Color.Blue, Color.Yellow, Color.Red };
		
		Result res = m.tryAnswer(attempt);
		
		System.out.println("totalLossTest = " + res);
		
		assertTrue(res.equals(new Result(0, 0, 4, 0)));
	}
}