package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LevelLoader {
	private final static char CEILING_TILE = '_';
	private final static char WALL_TILE = '|';
	private final static char PLATFORM_TILE = '#';
	private final static char PLAYER = 'P';
	private final static char ENEMY_1 = '1';
	private final static char ENEMY_2 = '2';
	private final static char ENEMY_3 = '3';
	private final static char ENEMY_4 = '4';
	private final static char ENEMY_5 = '5';
	private final static char ENEMY_6 = '6';

	public static String loadLevel(String levelName) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader("resources/levels/" + levelName+".txt"))) {
			int ch;
			while ((ch = br.read()) != -1) {
				sb.append(ch);
			}
		} catch (IOException e) {
			System.err.println("LIVELLO NON TROVATO");
		}
		return sb.toString();
	}
}
