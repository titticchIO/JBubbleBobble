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
			instance = new Player(30, 30, 16 * SCALE, 16 * SCALE, "P1");
		return instance;
	}
	
	@Override
	public void updateImage() {
		toChange = false;
		if (xSpeed == 0) {
			if (!positionCode.equals("static")) {
				toChange = true;
				setPositionCode("static");
			}
		} else {
			super.updateImage();
		}
	}

	public static Player getInstance(float x, float y, float width, float height, String positionCode) {
		if (instance == null)
			instance = new Player(x, y, width, height, positionCode);
		return instance;
	}

	private Player(float x, float y, float width, float height, String positionCode) {
		super(x, y, width, height, positionCode);
		currentBubble = new PlayerBubble(x, y, width, height, positionCode);
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

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public String getType() {
		return type;
	}

}
