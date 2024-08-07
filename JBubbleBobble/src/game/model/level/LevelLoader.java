package game.model.level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import game.model.enemies.*;
import game.model.entities.Player;
import game.model.tiles.Tile;

import static game.model.Settings.TILE_SIZE;
import static game.model.Settings.VERTICAL_TILES;
import static game.model.Settings.HORIZONTAL_TILES;
import static game.model.Settings.SCALE;

public class LevelLoader {

	private final static String BLOCK = "#";
	private final static String PLAYER = "P";
	private final static String ENEMY = "E";
	private final static String BUBBLE = "B";
	
	
	private final static String ZEN_CHAN = "Z";
	private final static String MONSTA = "M";
	private final static String BANEBOU = "N";
	private final static String MIGHTA = "I";
	private final static String PULPUL = "U";
	private final static String SKELMONSTA = "S";
	
	/*
	private final static String ENEMY_4 = "4";
	private final static String ENEMY_5 = "5";
	private final static String ENEMY_6 = "6";
	*/

	private static String[][] levelData;

	public static String[][] readLevelFile(int levelNum) {

		String[][] matrice = new String[VERTICAL_TILES][HORIZONTAL_TILES];

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
				String type = c.substring(0, 1);
				if (!type.equals(" ")) {
					switch (type) {
					case PLAYER:
						level.addPlayer(
								Player.getInstance(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE - SCALE, TILE_SIZE - SCALE, "right"));
						break;
						
					case BLOCK:
						level.addTile(new Tile(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, "1"));
						break;
						
					case ZEN_CHAN:
						level.addEnemy(new Zen_chan(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1, "right"));
						break;
					case MONSTA:
						level.addEnemy(new Monsta(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1, "right"));
						break;
					case BANEBOU:
						level.addEnemy(new Banebou(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1, "right"));
						break;
					case PULPUL:
						level.addEnemy(new Pulpul(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1, "right"));
						break;		
					case SKELMONSTA:
						level.addEnemy(new SkelMonsta(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1, "right"));
					}
				}
				
				x++;
			}
			x = 0;
			y++;
		}
		levelData = matrice;
	}

	public static String[][] getLevelData() {
		return levelData;
	}
}
