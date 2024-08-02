package game.model.entities;

import game.model.bubbles.BubbleManager;
import game.model.bubbles.PlayerBubble;
import static game.model.Settings.SCALE;

import game.model.Settings;

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

	public void shootBubble(BubbleManager bubbleManager) {
		if (xSpeed > 0) {
			bubbleManager.createBubble(x + Settings.TILE_SIZE, y, 1);
			System.out.println("right");
		} else {
			bubbleManager.createBubble(x - Settings.TILE_SIZE, y, -1);
			System.out.println("left");
		}
	}

}
