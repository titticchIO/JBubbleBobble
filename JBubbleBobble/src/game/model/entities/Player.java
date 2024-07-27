package game.model.entities;

import game.model.bubbles.PlayerBubble;
import static game.view.GameFrame.SCALE;

public class Player extends MovingEntity {

	// bolla attuale
	private PlayerBubble currentBubble;
	// singleton
	private static Player instance;

	public static Player getInstance() {
		if (instance == null)
			instance = new Player(30, 30, 16 * SCALE, 16 * SCALE);
		return instance;
	}

	public static Player getInstance(float x, float y, float width, float height) {
		if (instance == null)
			instance = new Player(x, y, width, height);
		return instance;
	}

	private Player(float x, float y, float width, float height) {
		super(x, y, width, height);
		currentBubble = new PlayerBubble(x, y, width, height);
	}

	/**
	 * Getters and Setters
	 */
	public PlayerBubble getCurrentBubble() {
		return currentBubble;
	}

	public void setCurrentBubble(PlayerBubble currentBubble) {
		this.currentBubble = currentBubble;
	}
}
