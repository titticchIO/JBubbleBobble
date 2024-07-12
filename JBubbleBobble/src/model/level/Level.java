package model.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import model.Player;
import model.bubbles.BubbleManager;
import model.enemies.Enemy;
import model.entity.Entity;
import model.tiles.Tile;
import view.EntityView;
import view.tiles.TileView;

public class Level extends Observable {

	private Player player;
	private List<Enemy> enemies;
	private BubbleManager bManager;
	private List<Tile> tiles;
	private List<EntityView> entitiesView;

	public Level(int levelNum) {
		tiles = new ArrayList<Tile>();
		enemies = new ArrayList<Enemy>();
		entitiesView = new ArrayList<EntityView>();		
		bManager = new BubbleManager();
		// String textFile=LevelLoader.readLevelFile(levelNum);
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
	
	public void addEntityView(EntityView entityView) {
		entitiesView.add(entityView);
	}
	
	public void loadLevelTiles() {
		for (int i = 0; i < tiles.size(); i++) {
			tiles.get(i).addObserver(entitiesView.get(i));
			tiles.get(i).notifyPosition();
		}
	}

}
