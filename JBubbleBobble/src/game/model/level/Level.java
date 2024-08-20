package game.model.level;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.Random;

import game.model.bubbles.BubbleManager;
import game.model.bubbles.PlayerBubble;
import game.model.bubbles.Bubble;
import game.model.enemies.Enemy;
import game.model.enemies.EnemyManager;
import game.model.entities.Entity;
import game.model.entities.Player;

import game.model.powerups.Powerup;
import game.model.powerups.PowerupManager;
import game.model.tiles.Tile;

public class Level {
	public final static int NUM_HORIZONTAL_TILES = 30;
	public final static int NUM_VERTICAL_TILES = 24;
	public final static int GAME_WIDTH = NUM_HORIZONTAL_TILES * Tile.TILE_SIZE;
	public final static int GAME_HEIGHT = NUM_VERTICAL_TILES * Tile.TILE_SIZE;

	private Player player;
	private List<Tile> tiles;
	private EnemyManager enemyManager;
	private BubbleManager bubbleManager;
	private PowerupManager powerupManager;
	private List<float[]> powerupSpawns;
	private String[][] lvlData;


	public Level(int levelNum) {
		tiles = new ArrayList<Tile>();
		enemyManager = new EnemyManager();
		bubbleManager = new BubbleManager();
		powerupManager = new PowerupManager();
		lvlData = LevelLoader.loadLevel(this, levelNum);
	}

	public String[][] getLvlData() {
		return lvlData;
	}

	public void printMatrix() {
		// Verifica se la matrice è vuota o null
		if (lvlData == null || lvlData.length == 0) {
			System.out.println("lvlData è null!");
			return;
		}

		// Itera attraverso le righe della matrice
		for (String[] row : lvlData) {
			// Itera attraverso gli elementi di ciascuna riga
			for (String element : row) {
				// Stampa l'elemento con uno spazio di separazione
				System.out.print(element + " ");
			}
			// Aggiungi una nuova riga alla fine di ogni riga della matrice
			System.out.println();
		}
	}

	public List<Entity> getEntities() {
		List<Entity> entities = new ArrayList<Entity>();
		entities.add(player);
		entities.addAll(bubbleManager.getBubbles());
		entities.addAll(bubbleManager.getPlayerBubbles());
		entities.addAll(enemyManager.getEnemies());
		entities.addAll(enemyManager.getLasers());
		
		entities.addAll(powerupManager.getPowerups());
		return entities;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public EnemyManager getEnemyManager() {
		return enemyManager;
	}

	public void setEnemyManager(EnemyManager eManager) {
		this.enemyManager = eManager;
	}

	public BubbleManager getBubbleManager() {
		return bubbleManager;
	}

	public void setBubbleManager(BubbleManager bManager) {
		this.bubbleManager = bManager;
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}

	public List<float[]> getPowerupSpawns() {
		return powerupSpawns;
	}

	public void addPlayer(Player player) {
		this.player = player;
	}

	public void addEnemy(Enemy enemy) {
		enemyManager.addEnemy(enemy);
	}

	public void addTile(Tile tile) {
		tiles.add(tile);
	}

	public void addPowerup(Powerup powerup) {
		powerupManager.addPowerup(powerup);
	}

	public void addPowerupSpawns(float x, float y) {
		powerupSpawns.add(new float[] { x, y });
	}

	public Optional<Enemy> checkPlayerEnemyCollision() {
		return enemyManager.getEnemies().stream().filter(x -> x.hit(player)).findFirst();
	}

	public Optional<Bubble> checkPlayerBubbleCollision() {
		return bubbleManager.getBubbles().stream().filter(x -> x.hit(player)).findFirst();
	}

	public void captureEnemies() {
		if (checkCollisions(bubbleManager.getPlayerBubbles(), enemyManager.getEnemies()))
			bubbleManager.getPlayerBubbles().stream()
					.forEach(b -> enemyManager.getEnemies().stream().filter(b::isEnemyHit).forEach(e -> {
						if (!b.hasEnemy()) {
							b.setEnemy(e);
							removeEnemy(e);
						}
					}));
	}

	public void killEnemies() {
		Optional<PlayerBubble> bubbleWithEnemy = Entity.checkCollision(player, bubbleManager.getPlayerBubbles());
		if (bubbleWithEnemy.isPresent() && bubbleWithEnemy.get().hasEnemy())
			bubbleManager.getPlayerBubbles().stream().filter(PlayerBubble::hasEnemy).forEach(b -> {
				if (b.hit(player))
					b.popAndKill();
			});
	}

	public void spawnPowerup(Powerup powerup) {
		Random random = new Random();
		int x = random.nextInt(1, NUM_HORIZONTAL_TILES - 1);
		int y = random.nextInt(1, NUM_VERTICAL_TILES - 1);
		final int MAX_ATTEMPTS = (NUM_HORIZONTAL_TILES - 2) * (NUM_VERTICAL_TILES - 2);

		for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
			// Verifica se la posizione è valida per generare il powerup
			if (lvlData[y][x].equals(" ") && lvlData[y + 1][x].matches("[0-9]")) {
				powerup.setPosition(x*Tile.TILE_SIZE, y*Tile.TILE_SIZE);
				powerupManager.getPowerups().add(powerup);
				return;
			}

			// Passa al prossimo tile orizzontalmente
			x++;
			if (x >= NUM_HORIZONTAL_TILES - 1) {
				x = 1;
				y++;

				// Se y ha superato il limite, ricomincia dall'alto
				if (y >= NUM_VERTICAL_TILES - 1) {
					y = 1;
				}
			}
		}

	}

	public void removeEnemy(Enemy enemy) {
		enemyManager.removeEnemy(enemy);
	}

	public static <T extends Entity, U extends Entity> boolean checkCollisions(List<T> list1, List<U> list2) {
		return list1.stream().anyMatch(x -> list2.stream().anyMatch(x::hit));
	}

	public void updateLevel() {
		player.updateEntity();
		enemyManager.updateEnemies();
		bubbleManager.updateBubbles();
		captureEnemies();
		killEnemies();

		Optional<Enemy> oe = checkPlayerEnemyCollision();
//		if (oe.isPresent()) System.out.println("Hittato enemy");

		Optional<Bubble> ob = checkPlayerBubbleCollision();
//		if (ob.isPresent()) System.out.println("Hittato bolla");

	}

}
