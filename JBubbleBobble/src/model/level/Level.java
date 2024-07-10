package model.level;

import java.util.ArrayList;
import java.util.List;

import model.Player;
import model.bubbles.BubbleManager;
import model.enemies.Enemy;
import model.tiles.Tile;

public class Level {
	private Player player;
	private List<Enemy> enemies;
	private BubbleManager bManager;
	private List<Tile> tiles;

	public Level() {
		tiles = new ArrayList<Tile>();
		enemies = new ArrayList<Enemy>();
		bManager = new BubbleManager();
		// Costruisci livello
//		LevelLoader.loadLevel();

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



}
