package game.model.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import game.model.bubbles.BubbleManager;
import game.model.enemies.Enemy;
import game.model.entities.Player;
import game.model.tiles.Tile;
import utils.LevelLoader;

public class Level {

	private Player player;
	private List<Enemy> enemies;
	private List<Tile> tiles;
	private BubbleManager bManager;

	public Level(int levelNum) {
		tiles = new ArrayList<Tile>();
		enemies = new ArrayList<Enemy>();
		bManager = BubbleManager.getInstance();
		LevelLoader.loadLevel(this, levelNum);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(List<Enemy> enemies) {
		this.enemies = enemies;
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
		enemies.add(enemy);
	}

	public void addTile(Tile tile) {
		tiles.add(tile);
	}
	
	public void updateLevel() {
		player.updateEntity();
		for (Enemy e: enemies) {
			e.updateEntity();
		}
		bManager.updateBubbles();
	}
	
}
