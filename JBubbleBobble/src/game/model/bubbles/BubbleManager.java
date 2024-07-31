package game.model.bubbles;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import game.model.Settings;

public class BubbleManager extends Observable {
	private List<Bubble> bubbles;
	private static BubbleManager instance;

	public static BubbleManager getInstance() {
		if (instance == null)
			instance = new BubbleManager();
		return instance;
	}

	private BubbleManager() {
		bubbles = new ArrayList<Bubble>();
	}

	public void createBubble(float x, float y) {
		Bubble newBubble=new PlayerBubble(x, y, Settings.TILE_SIZE, Settings.TILE_SIZE);
		bubbles.add(newBubble);
		setChanged();
		notifyObservers(newBubble);
	}
	
	public void removeBubble(Bubble bubble) {
		bubbles.remove(bubble);
	}

	public List<Bubble> getBubbles() {
		return bubbles;
	}

	public void updateBubbles() {
		for (Bubble b : bubbles) {
			b.updateEntity();
		}
	}
}
