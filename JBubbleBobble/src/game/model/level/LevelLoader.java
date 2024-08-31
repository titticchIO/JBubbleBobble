package game.model.level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import game.model.enemies.*;
import game.model.tiles.Tile;

import static game.model.tiles.Tile.TILE_SIZE;;

public class LevelLoader {

	private final static char PLAYER = 'P';
	private final static char ZEN_CHAN = 'Z';
	private final static char MONSTA = 'M';
	private final static char BANEBOU = 'N';
	private final static char PULPUL = 'U';
	private final static char SKELMONSTA = 'S';
	private final static char INVADER = 'I';
	private final static char BOSS = 'B';

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
			System.err.println("ERRORE: LIVELLO NON TROVATO");
		}

		return matrice;
	}

	// crea gli oggetti nel level in base alla matrice

	public static char[][] loadLevel(Level level, int levelNum) {
		char[][] matrice = readLevelFile(levelNum);
		int x = 0;
		int y = 0;
		for (char[] linea : matrice) {
			for (char c : linea) {
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
						case INVADER -> new Invader(x * TILE_SIZE, y * TILE_SIZE);
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

		return matrice;
	}

}
