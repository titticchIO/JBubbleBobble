package game.model.bubbles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BubbleManager {
	private List<Bubble> bubbles;

	public BubbleManager() {
		bubbles = new ArrayList<Bubble>();
	}

	public BubbleManager(PlayerBubble... bubbles) {
		this.bubbles = new ArrayList<Bubble>(Arrays.asList(bubbles));
	}
	
	private void updateBubbles() {
		for(Bubble b:bubbles) {
			b.updateEntity();
		}
		
	}
}
