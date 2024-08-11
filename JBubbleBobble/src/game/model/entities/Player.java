package game.model.entities;

import game.model.bubbles.BubbleManager;
import game.model.bubbles.PlayerBubble;
import game.model.entities.MovingEntity.Direction;
import game.model.tiles.Tile;

import game.model.HelpMethods;
import game.model.Settings;

public class Player extends MovingEntity {

	private final String type = "P";
	private Direction bubbleDirection;

	// bolla attuale
	private PlayerBubble currentBubble;
	// singleton
	private static Player instance;

	public static Player getInstance() {
		if (instance == null)
			instance = new Player(30, 30, 16, 16);

		return instance;
	}

	public static Player getInstance(float x, float y, float width, float height) {
		if (instance == null)
			instance = new Player(x, y, width, height);
		return instance;
	}

	private Player(float x, float y, float width, float height) {
		super(x, y, width, height, "P");
		currentBubble = new PlayerBubble(x, y, width, height);
		bubbleDirection = Direction.RIGHT;
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

	@Override
	public void setDirection(Direction direction) {
		super.setDirection(direction);
		if (direction == Direction.RIGHT || direction == direction.LEFT)
			bubbleDirection = direction;
	}

	public void shootBubble(BubbleManager bubbleManager) {
		if (bubbleDirection == Direction.RIGHT
				&& !HelpMethods.isEntityInsideWall(x + Tile.TILE_SIZE, y, width, height)) {
			bubbleManager.createBubble(x + Tile.TILE_SIZE, y, 2);
		} else if (!HelpMethods.isEntityInsideWall(x - Tile.TILE_SIZE, y, width, height)) {
			bubbleManager.createBubble(x - Tile.TILE_SIZE, y, -2);
		}
	}
}
