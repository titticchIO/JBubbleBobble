package game.view;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import game.model.bubbles.Bubble;
import game.model.bubbles.BubbleManager;
import game.model.enemies.Enemy;
import game.model.entities.Player;
import game.model.level.Level;
import game.model.tiles.Tile;

public class LevelView implements Observer {
	private MovingEntityView playerView;
	private List<StaticEntityView> tiles;
	private List<MovingEntityView> enemies;
	private List<MovingEntityView> bubbles;

	public LevelView(Level level) {
		playerView = new MovingEntityView("player");
		Player.getInstance().addObserver(playerView);

		enemies = new ArrayList<MovingEntityView>();
		spawnEnemies(level);

		tiles = new ArrayList<StaticEntityView>();
		for (Tile t : level.getTiles()) {
			StaticEntityView newTile = new StaticEntityView("tile" + t.getType());
			t.addObserver(newTile);
			t.notifyPosition();
			tiles.add(newTile);
		}

		bubbles = new ArrayList<MovingEntityView>();
		for (Bubble b : BubbleManager.getInstance().getBubbles()) {
			MovingEntityView bubble = new MovingEntityView("Bubble");
			b.addObserver(bubble);
			bubbles.add(bubble);
		}
	}

	public void spawnEnemies(Level level) {
		for (Enemy e : level.getEnemies()) {
			MovingEntityView enemyView = new MovingEntityView(e.getName());
			e.addObserver(enemyView);
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

	public List<MovingEntityView> getEnemies() {
		return enemies;
	}

	public List<MovingEntityView> getBubbles() {
		return bubbles;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Bubble) {
			Bubble bubble = (Bubble) arg;
			if (!bubble.isPopped()) {
				MovingEntityView newBubbleView = new MovingEntityView("Bubble");
				bubble.addObserver(newBubbleView);
				bubbles.add(newBubbleView);
			}else {
				bubbles.remove(bubble);
			}
		}

	}

}
