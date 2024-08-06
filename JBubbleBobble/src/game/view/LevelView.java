package game.view;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
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
	private List<EntityView> tiles;
	private List<MovingEntityView> enemies;
	private List<MovingEntityView> bubbles;

	public LevelView(Level level) {
		playerView = new MovingEntityView("P1");
		Player.getInstance().addObserver(playerView);

		enemies = new ArrayList<MovingEntityView>();
		spawnEnemies(level);

		tiles = new ArrayList<EntityView>();
		for (Tile t : level.getTiles()) {
			EntityView newTile = new EntityView(t.getImageCode());
			t.addObserver(newTile);
			t.notifyPosition();
			tiles.add(newTile);
		}

		bubbles = new ArrayList<MovingEntityView>();
		
		Iterator<Bubble> iterator = BubbleManager.getInstance().getBubbles().iterator();
		while (iterator.hasNext()) {
		    Bubble b = iterator.next();
		    MovingEntityView bubble = new MovingEntityView(b.getImageCode());
		    b.addObserver(bubble);
		    bubbles.add(bubble);
		}
	}

	public void spawnEnemies(Level level) {
		for (Enemy e : level.geteManager().getEnemies()) {
			MovingEntityView enemyView = new MovingEntityView(e.getImageCode());
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
				MovingEntityView newBubbleView = new MovingEntityView("B1");
				newBubbleView.setObservedEntity(bubble);
				bubble.addObserver(newBubbleView);
				bubbles.add(newBubbleView);
			} else {
				bubbles.removeIf(b -> b.isObserving(bubble));
			}
		}

	}

}
