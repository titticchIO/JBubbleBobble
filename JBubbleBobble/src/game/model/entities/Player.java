package game.model.entities;

import game.model.bubbles.BubbleManager;
import game.model.bubbles.PlayerBubble;
import game.model.entities.MovingEntity.Direction;

import static game.model.Settings.SCALE;

import game.model.HelpMethods;
import game.model.Settings;

public class Player extends MovingEntity {
	
	private final String type = "P";



	// bolla attuale
	private PlayerBubble currentBubble;
	// singleton
	private static Player instance;

	public static Player getInstance() {
		if (instance == null)
			instance = new Player(30, 30, 16 * SCALE, 16 * SCALE);
		return instance;
	}
	
//	@Override
//	public void updateImage() {
//		toChange = false;
//		if (xSpeed == 0) {
//			if (!positionCode.equals("static")) {
//				toChange = true;
//				setPositionCode("static");
//			}
//		} else {
//			super.updateImage();
//		}
//	}

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
		if (direction == Direction.RIGHT) {
			if (!HelpMethods.isEntityInsideWall(x + Settings.TILE_SIZE, y, width, height)) {
				bubbleManager.createBubble(x + Settings.TILE_SIZE, y-1, 2);
			}
		} else {
			if (!HelpMethods.isEntityInsideWall(x - Settings.TILE_SIZE, y, width, height)) {
				bubbleManager.createBubble(x - Settings.TILE_SIZE, y-1, -2);
			}
		}
	}

	@Override
	public String getType() {
		return type;
	}

}
