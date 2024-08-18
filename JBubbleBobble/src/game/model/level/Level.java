package game.model.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;

import game.model.bubbles.BubbleManager;
import game.model.bubbles.PlayerBubble;
import game.model.bubbles.Bubble;
import game.model.enemies.Enemy;
import game.model.enemies.EnemyManager;
import game.model.entities.Entity;
import game.model.entities.Player;
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
	private List<float[]> powerupSpawns;
	private String[][] lvlData;

	public Level(int levelNum) {
		tiles = new ArrayList<Tile>();
		enemyManager = new EnemyManager();
		bubbleManager = new BubbleManager();
		lvlData = LevelLoader.loadLevel(this, levelNum);
	}

	public String[][] getLvlData() {
		return lvlData;
	}

	public List<Entity> getEntities() {
		List<Entity> entities = new ArrayList<Entity>();
		entities.add(player);
		entities.addAll(bubbleManager.getBubbles());
		entities.addAll(enemyManager.getEnemies());
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

	public void addPowerupSpawns(float x, float y) {
		powerupSpawns.add(new float[] { x, y });
	}

	public Optional<Enemy> checkPlayerEnemyCollision() {
		return enemyManager.getEnemies().stream().filter(x -> x.hit(player)).findFirst();
	}

	public Optional<Bubble> checkPlayerBubbleCollision() {
		return bubbleManager.getBubbles().stream().filter(x -> x.hit(player)).findFirst();
	}

//	public boolean checkcheckBubbleEnemyCollision() {
//		return 
//	}

	public void captureEnemies() {
		bubbleManager.getBubbles().stream()
				.forEach(b -> enemyManager.getEnemies().stream().filter(b::isEnemyHit).forEach(e -> {
					if (b instanceof PlayerBubble pb)
						pb.setEnemy(e);
					removeEnemy(e);
				}));
	}

	public void removeEnemy(Enemy enemy) {
		enemyManager.removeEnemy(enemy);
	}

	public static <T extends Entity, U extends Entity> boolean checkCollisions(List<T> list1,
			List<U> list2) {
		return list1.stream().anyMatch(x -> list2.stream().anyMatch(x::hit));
	}
	
	public void updateLevel() {
		player.updateEntity();
		enemyManager.updateEnemies();
		bubbleManager.updateBubbles();

		if (checkCollisions(bubbleManager.getBubbles(), enemyManager.getEnemies()))
			captureEnemies();

		Optional<Enemy> oe = checkPlayerEnemyCollision();
//		if (oe.isPresent()) System.out.println("Hittato enemy");

		Optional<Bubble> ob = checkPlayerBubbleCollision();
//		if (ob.isPresent()) System.out.println("Hittato bolla");

	}

}
