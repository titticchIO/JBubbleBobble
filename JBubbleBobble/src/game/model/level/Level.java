package game.model.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;

import game.model.bubbles.BubbleManager;
import game.model.bubbles.Bubble;
import game.model.enemies.Enemy;
import game.model.enemies.EnemyManager;
import game.model.entities.Player;
import game.model.tiles.Tile;

public class Level {

	private Player player;
	private List<Tile> tiles;
	private EnemyManager eManager;
	private BubbleManager bManager;

	public Level(int levelNum) {
		tiles = new ArrayList<Tile>();
		eManager = EnemyManager.getInstance();
		bManager = BubbleManager.getInstance();
		LevelLoader.loadLevel(this, levelNum);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public EnemyManager geteManager() {
		return eManager;
	}

	public void seteManager(EnemyManager eManager) {
		this.eManager = eManager;
	}

	public BubbleManager getbManager() {
		return bManager;
	}

	public void setbManager(BubbleManager bManager) {
		this.bManager = bManager;
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}

	public void addPlayer(Player player) {
		this.player = player;
	}

	public void addEnemy(Enemy enemy) {
		eManager.addEnemy(enemy);
	}

	public void addTile(Tile tile) {
		tiles.add(tile);
	}

	public Optional<Enemy> checkEnemiesCollisions() {
		return eManager.getEnemies().stream()
				.filter(x -> x.hit(player))
				.findFirst();
	}
	
	public Optional<Bubble> checkBubblesCollisions() {
		return bManager.getBubbles().stream()
				.filter(x -> x.hit(player))
				.findFirst();
	}

	public void updateLevel() {
		player.updateEntity();
		eManager.updateEnemies();
		bManager.updateBubbles();
		Optional<Enemy> oe = checkEnemiesCollisions();
//		if (oe.isPresent()) System.out.println("Hittato enemy");
		
		Optional<Bubble> ob = checkBubblesCollisions();
//		if (ob.isPresent()) System.out.println("Hittato bolla");
		
	}

}
