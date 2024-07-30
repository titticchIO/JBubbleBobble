package game.model.bubbles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import game.model.Settings;

public class BubbleManager {
	private List<Bubble> bubbles;

	public BubbleManager() {
		bubbles = new ArrayList<Bubble>();
	}

	public BubbleManager(PlayerBubble... bubbles) {
		this.bubbles = new ArrayList<Bubble>(Arrays.asList(bubbles));
	}

	public void createBubble(float x, float y) {
		bubbles.add(new PlayerBubble(x, y, Settings.TILE_SIZE, Settings.TILE_SIZE));
	}

	public void updateBubbles() {
		for (Bubble b : bubbles) {
			b.updateEntity();
		}

	}
}
