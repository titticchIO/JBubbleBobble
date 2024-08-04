package game.model.entities;

import game.model.bubbles.BubbleManager;
import game.model.bubbles.PlayerBubble;
import game.model.entities.MovingEntity.Directions;

import static game.model.Settings.SCALE;

import game.model.Settings;

public class Player extends MovingEntity {

	protected Directions direction;
	
	
	// bolla attuale
	private PlayerBubble currentBubble;
	// singleton
	private static Player instance;

	public static Player getInstance() {
		if (instance == null)
			instance = new Player(30, 30, 16 * SCALE, 16 * SCALE, "P1");
		return instance;
	}

	public static Player getInstance(float x, float y, float width, float height, String imageCode) {
		if (instance == null)
			instance = new Player(x, y, width, height, imageCode);
		return instance;
	}

	private Player(float x, float y, float width, float height, String imageCode) {
		super(x, y, width, height, imageCode);
		currentBubble = new PlayerBubble(x, y, width, height, imageCode);
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
		if (direction == Directions.RIGHT) {
			bubbleManager.createBubble(x + Settings.TILE_SIZE, y, 1 + xSpeed);
			System.out.println("right");
		} else {
			bubbleManager.createBubble(x - Settings.TILE_SIZE, y, -1 + xSpeed);
			System.out.println("left");
		}
	}
	
	public void setDirections(Directions direction) {
		this.direction = direction;
	}


}
