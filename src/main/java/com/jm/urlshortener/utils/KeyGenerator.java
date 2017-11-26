package com.jm.urlshortener.utils;

import java.util.Random;

/**
 * Key generator
 * @author jmugico
 */
public class KeyGenerator {

	private static Random RANDOM = new Random();
	private static char CHARS[] = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
	public static int KEY_LENGTH = 6;
    
	public static String generateKey() {
	
		String key = "";
		for (int i = 0; i < KEY_LENGTH; i++) {
			key += CHARS[RANDOM.nextInt(62)];
		}
		return key;
	}
}