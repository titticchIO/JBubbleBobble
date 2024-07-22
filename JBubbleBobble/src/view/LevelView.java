package view;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.enemies.Enemy;
import model.entities.Player;
import model.level.Level;
import model.tiles.Tile;

public class LevelView implements Observer {
	private MovingEntityView playerView;
	private List<StaticEntityView> tiles;
	private List<MovingEntityView> enemies;

	public LevelView(Level level) {
		playerView = new MovingEntityView("player");
		Player.getInstance().addObserver(playerView);
		enemies = new ArrayList<MovingEntityView>();
		spawnEnemies(level);
		tiles = new ArrayList<StaticEntityView>();
		for (Tile t : level.getTiles()) {
			StaticEntityView newTile = new StaticEntityView("tile"+t.getType());
			t.addObserver(newTile);
			t.notifyPosition();
			tiles.add(newTile);
		}
	}

	public void spawnEnemies(Level level) {
		for (Enemy e : level.getEnemies()) {
			MovingEntityView enemyView = new MovingEntityView(e.getName());
			e.addObserver(enemyView);
//			e.notifyPosition();
			enemies.add(enemyView);
		}
	}

	public MovingEntityView getPlayerView() {
		return playerView;
	}

	public void renderTiles(Graphics g) {
		for (StaticEntityView t : tiles) {
			t.render(g);
		}
	}

	public void updatePlayerAnimation() {
		playerView.updateAnimationImg();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

	public List<MovingEntityView> getEnemies() {
		return enemies;
	}
}
