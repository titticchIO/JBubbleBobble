package game.model.bubbles;

import java.util.ArrayList;
import java.util.Iterator;
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

	public void createBubble(float x, float y,float xSpeed) {
		Bubble newBubble=new PlayerBubble.Builder(x, y, Settings.TILE_SIZE - 1, Settings.TILE_SIZE - 1, "B1").xSpeed(xSpeed).build();
		bubbles.add(newBubble);
		setChanged();
		notifyObservers(newBubble);
	}

	public void removeBubble(Bubble bubble) {
		bubbles.remove(bubble);
		setChanged();
		notifyObservers(bubble);
	}

	public List<Bubble> getBubbles() {
		return bubbles;
	}

	public void updateBubbles() {
		Iterator<Bubble> iterator = bubbles.iterator();
		while (iterator.hasNext()) {
			Bubble bubble = iterator.next();
			if (bubble.isPopped()) {
				iterator.remove();
				setChanged();
				notifyObservers(bubble);
			} else {
				bubble.updateEntity();
			}
		}
	}
}
