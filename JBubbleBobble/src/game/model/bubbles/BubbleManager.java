package game.model.bubbles;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import game.model.Model;
import game.model.tiles.Tile;

public class BubbleManager {
	private List<Bubble> bubbles;
	private List<PlayerBubble> playerBubbles;

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

	public void createBubble() {
		Random random = new Random();
		
		if (random.nextInt(0, 200) == 0) {
			switch (random.nextInt()) {
			case 0 -> Model.getInstance().getCurrentLevel().spawnBubble(new ThunderBubble());
			}
			
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
		for (Bubble bubble : bubbles) {
			if (bubble.isPopped()) {
				bubbles.remove(bubble); // Sicuro, poiché CopyOnWriteArrayList gestisce la concorrenza
			} else {
				bubble.updateEntity();
			}
		}
		for (PlayerBubble playerBubble : playerBubbles) {
			if (playerBubble.isPopped()) {
				playerBubbles.remove(playerBubble); // Sicuro, poiché CopyOnWriteArrayList gestisce la concorrenza
			} else {
				playerBubble.updateEntity();
			}
		}
	}
}
