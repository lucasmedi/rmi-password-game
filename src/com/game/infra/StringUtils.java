package com.game.infra;

public class StringUtils {
	public static boolean isEmpty(String s) {
		return (s == null || s.isEmpty() || s.trim().isEmpty());
	}
}