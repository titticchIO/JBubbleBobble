package model.level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import model.Player;
import model.tiles.Tile;
import view.GameFrame;
import view.StaticEntityView;
import static view.GameFrame.TILE_SIZE;

public class LevelLoader {

	private final static String BLOCK_TILE = "#";
	private final static String PLAYER = "P";
	private final static String ENEMY_1 = "1";
	private final static String ENEMY_2 = "2";
	private final static String ENEMY_3 = "3";
	private final static String ENEMY_4 = "4";
	private final static String ENEMY_5 = "5";
	private final static String ENEMY_6 = "6";

	private static String[][] levelData;

	private static String[][] readLevelFile(int levelNum) {

		String[][] matrice = new String[GameFrame.VERTICAL_TILES][GameFrame.HORIZONTAL_TILES];

		String levelPath = "resources/levels/Livello" + levelNum + ".txt";

		try (BufferedReader br = new BufferedReader(new FileReader(levelPath))) {
			int i = 0;
			while (br.ready()) {
				matrice[i] = br.readLine().split("\\|");
				i++;
			}
		} catch (IOException e) {
			System.err.println("ERRORE: LIVELLO NON TROVATO");
		}

		return matrice;
	}

	// crea gli oggetti nel level in base alla matrice

	public static void loadLevel(Level level, int levelNum) {
		String[][] matrice = readLevelFile(levelNum);
		int x = 0;
		int y = 0;
		for (String[] linea : matrice) {
			for (String c : linea) {
				switch (c.substring(0, 1)) {
				case PLAYER:
					level.addPlayer(Player.getInstance(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE));
					break;
				case BLOCK_TILE:
					Tile t = new Tile(x * 16, y * 16, 16, 16);
					StaticEntityView tv = new StaticEntityView("tile");
					level.addTile(t);
//					level.addStaticEntitiesView(tv);
					break;
				}
				x++;
			}
			x = 0;
			y++;
		}
		levelData = matrice;
	}

	public static String[][] getLevelData() {
//		for (int y=0;y<levelData.length-1;y++) {
//			for (int x=0;x<levelData[0].length-1;x++) {
//				if (levelData[y][x].equals("1"))
//					levelData[y][x]=" ";
//			}
//		}
		return levelData;
	}
}
