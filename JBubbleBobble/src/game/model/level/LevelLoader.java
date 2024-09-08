package game.model.level;

import static game.model.Tile.TILE_SIZE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import game.model.Tile;
import game.model.enemies.*;;

/**
 * The LevelLoader class is responsible for loading level data from files and
 * creating objects such as tiles and enemies based on the level's layout. It
 * reads level files and converts the data into game objects that populate the
 * game level.
 */
public class LevelLoader {

	// Character codes representing different entities in the level file
	private final static char PLAYER = 'P';
	private final static char ZEN_CHAN = 'Z';
	private final static char MONSTA = 'M';
	private final static char BANEBOU = 'N';
	private final static char PULPUL = 'U';
	private final static char SKELMONSTA = 'S';
	private final static char INVADER = 'I';
	private final static char BOSS = 'B';

	/**
	 * Reads the level file for the specified level number and returns a 2D
	 * character array representing the layout of the level.
	 * 
	 * @param levelNum The number of the level to be loaded.
	 * @return A 2D character array representing the level's layout.
	 */
	public static char[][] readLevelFile(int levelNum) {

		char[][] matrice = new char[Level.NUM_VERTICAL_TILES][Level.NUM_HORIZONTAL_TILES];

		String levelPath = "resources/levels/Livello" + levelNum + ".txt";

		try (BufferedReader br = new BufferedReader(new FileReader(levelPath))) {
			int i = 0;
			while (br.ready()) {
				matrice[i] = br.readLine().toCharArray();
				i++;
			}
		} catch (IOException e) {
			System.err.println("ERROR: LEVEL FILE NOT FOUND");
		}

		return matrice;
	}

	/**
	 * Loads a level from the specified level file and creates the necessary objects
	 * (tiles, player spawn point, enemies) based on the level layout. The layout is
	 * determined by reading a level file and parsing the characters to create
	 * appropriate objects.
	 * 
	 * @param level    The Level object to populate with game entities.
	 * @param levelNum The number of the level to be loaded.
	 * @return A 2D character array representing the level's layout.
	 */

	public static char[][] loadLevel(Level level, int levelNum) {
		char[][] matrix = readLevelFile(levelNum);
		int x = 0;
		int y = 0;
		for (char[] row : matrix) {
			for (char c : row) {
				if (!(c == ' '))
					switch (c) {
					case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ->
						level.addTile(new Tile(x * TILE_SIZE, y * TILE_SIZE, c));
					case PLAYER -> {
						level.setPlayerSpawnPoint(x * TILE_SIZE, y * TILE_SIZE);
					}

					default -> {
						level.addEnemy(switch (c) {
						case ZEN_CHAN -> new ZenChan(x * TILE_SIZE, y * TILE_SIZE);
						case MONSTA -> new Monsta(x * TILE_SIZE, y * TILE_SIZE);
						case BANEBOU -> new Banebou(x * TILE_SIZE, y * TILE_SIZE);
						case PULPUL -> new Pulpul(x * TILE_SIZE, y * TILE_SIZE);
						case INVADER -> new Invader(x * TILE_SIZE, y * TILE_SIZE - 1);
						case SKELMONSTA -> new SkelMonsta(x * TILE_SIZE, y * TILE_SIZE);
						case BOSS -> new Boss(x * TILE_SIZE, y * TILE_SIZE);
						default -> throw new IllegalArgumentException("Unexpected value: " + c);
						});
					}

					}
				x++;
			}

			x = 0;
			y++;
		}

		return matrix;
	}

}
