package game.model.level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import game.model.enemies.*;
import game.model.entities.Player;
import game.model.powerups.Powerup;
import game.model.tiles.Tile;

import static game.model.tiles.Tile.TILE_SIZE;;

public class LevelLoader {

	private final static String PLAYER = "P";
	private final static String BUBBLE = "B";
	private final static String POWERUP = "W";

	private final static String ZEN_CHAN = "Z";
	private final static String MONSTA = "M";
	private final static String BANEBOU = "N";
	private final static String PULPUL = "U";
	private final static String SKELMONSTA = "S";
	private final static String INVADER = "I";

	public static String[][] readLevelFile(int levelNum) {

		String[][] matrice = new String[Level.NUM_VERTICAL_TILES][Level.NUM_HORIZONTAL_TILES];

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

	public static String[][] loadLevel(Level level, int levelNum) {
		String[][] matrice = readLevelFile(levelNum);
		int x = 0;
		int y = 0;
		for (String[] linea : matrice) {
			for (String c : linea) {
				if (!c.equals(" "))
					switch (c) {
					case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" ->
						level.addTile(new Tile(x * TILE_SIZE, y * TILE_SIZE, c));
					case PLAYER ->
						level.addPlayer(Player.getInstance(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1));
					case POWERUP -> level.addPowerupSpawns(x, y);
					default -> {
						level.addEnemy(switch (c) {
						case ZEN_CHAN -> new ZenChan(x * TILE_SIZE, y * TILE_SIZE);
						case MONSTA -> new Monsta(x * TILE_SIZE, y * TILE_SIZE);
						case BANEBOU -> new Banebou(x * TILE_SIZE, y * TILE_SIZE);
						case PULPUL -> new Pulpul(x * TILE_SIZE, y * TILE_SIZE);
						case INVADER -> new Invader(x * TILE_SIZE, y * TILE_SIZE);
						default -> new SkelMonsta(x * TILE_SIZE, y * TILE_SIZE);
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
