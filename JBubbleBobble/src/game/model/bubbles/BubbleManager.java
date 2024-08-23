package game.model.bubbles;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import game.model.Model;
import game.model.entities.Player;
import game.model.tiles.Tile;

public class BubbleManager {
	private List<Bubble> bubbles;
	private List<PlayerBubble> playerBubbles;

	private boolean doOnce = true;

	public BubbleManager() {
		bubbles = new CopyOnWriteArrayList<>();
		playerBubbles = new CopyOnWriteArrayList<>();
	}

	public void createBubble(float x, float y, float xSpeed) {
		Bubble newBubble = new PlayerBubble.Builder(x, y, Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1, "B1").xSpeed(xSpeed)
				.build();
		bubbles.add(newBubble);
	}

	public void createPlayerBubble(float x, float y, float xSpeed) {
		PlayerBubble newBubble = new PlayerBubble.Builder(x, y, Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1, "B1")
				.xSpeed(xSpeed).build();
		playerBubbles.add(newBubble);
	}

	public void createSpecialBubble() {
		Random random = new Random();

		if (doOnce) {
			doOnce = false;
			Bubble fireBubble = new FireBubble();
			Model.getInstance().getCurrentLevel().spawnBubble(fireBubble);
			bubbles.add(fireBubble);
		}
	}

	public void addBubble(Bubble bubble) {
		bubbles.add(bubble);
	}

	public void removeBubble(Bubble bubble) {
		bubbles.remove(bubble);
	}

	public void removePlayerBubble(PlayerBubble playerBubble) {
		playerBubbles.remove(playerBubble);
	}

	public List<Bubble> getBubbles() {
		return bubbles;
	}

	public List<PlayerBubble> getPlayerBubbles() {
		return playerBubbles;
	}

	public void updateBubbles() {
		bubbles.forEach(b -> b.updateEntity());
		playerBubbles.forEach(pb -> pb.updateEntity());
		createSpecialBubble();
	}
}
