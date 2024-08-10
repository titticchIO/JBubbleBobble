package game.view;

import java.awt.Graphics;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

import game.model.bubbles.Bubble;
import game.model.bubbles.BubbleManager;
import game.model.enemies.Enemy;
import game.model.entities.Player;
import game.model.level.Level;
import game.model.tiles.Tile;

public class LevelView implements Observer {
	private MovingEntityView playerView;
	private List<EntityView> tiles;
	private List<MovingEntityView> enemies;
	private List<MovingEntityView> bubbles;

	public LevelView(Level level) {
		Player p = Player.getInstance();
		playerView = new MovingEntityView(p.getCode());
		p.addObserver(playerView);

		enemies = new CopyOnWriteArrayList<>();
		spawnEnemies(level);

		tiles = new CopyOnWriteArrayList<>();
		for (Tile t : level.getTiles()) {
			EntityView newTile = new EntityView(t.getCode(), t.getType());
			t.addObserver(newTile);
			t.notifyPosition();
			tiles.add(newTile);
		}

		bubbles = new CopyOnWriteArrayList<>();

		for (Bubble b : BubbleManager.getInstance().getBubbles()) {
			MovingEntityView bubble = new MovingEntityView(b.getCode());
			b.addObserver(bubble);
			bubbles.add(bubble);
		}
	}

	public void spawnEnemies(Level level) {
		for (Enemy e : level.geteManager().getEnemies()) {
			MovingEntityView enemyView = new MovingEntityView(e.getCode());
			e.addObserver(enemyView);
			enemies.add(enemyView);
		}
	}

	public MovingEntityView getPlayerView() {
		return playerView;
	}

	public void renderTiles(Graphics g) {
		for (EntityView t : tiles) {
			t.render(g);
		}
	}

	/*
	 * public void updatePlayerAnimation() { playerView.updateAnimationImg(); }
	 */

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
				MovingEntityView newBubbleView = new MovingEntityView(bubble.getCode());
				newBubbleView.setObservedEntity(bubble);
				bubble.addObserver(newBubbleView);
				bubbles.add(newBubbleView);
			} else {
				bubbles.removeIf(b -> b.isObserving(bubble));
			}
		}

		if (arg instanceof Enemy) {
			Enemy enemy = (Enemy) arg;
			if (!enemy.isPopped()) {
				MovingEntityView newEnemyView = new MovingEntityView(enemy.getCode());
				newEnemyView.setObservedEntity(enemy);
				enemy.addObserver(newEnemyView);
				enemies.add(newEnemyView);
			} else {
				enemies.removeIf(e -> e.isObserving(enemy));
			}
		}
	}
}
