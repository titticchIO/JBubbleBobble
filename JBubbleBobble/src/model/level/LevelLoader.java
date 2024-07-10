package model.level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import model.Player;
import model.entity.Entity;
import model.tiles.Tile;

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

	public static String readLevelFile(int levelNum) {
		String levelPath = "resources/levels/Livello" + levelNum + ".txt";

		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(levelPath))) {
			while (br.ready()) {
				sb.append(br.readLine() + "\n");
			}
		} catch (IOException e) {
			System.err.println("ERRORE: LIVELLO NON TROVATO");
		}
		return sb.toString();
	}

	public static void loadLevel(Level level, String textFile) {
		int x = 0;
		int y = 0;
		for (String s : textFile.split("\n")) {
			for (char c : s.toCharArray()) {
				switch (c) {
				case PLAYER -> level.addPlayer(Player.getInstance(x, y, 16, 16));
				case PLATFORM_TILE -> level.addTile(new Tile(x, y, 16, 16));
				}
				x++;
			}
			x = 0;
			y++;
		}
	}

}
