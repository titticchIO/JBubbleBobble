package game.model.enemies;

import static game.model.HelpMethods.isSolidVerticalLine;
import static game.model.HelpMethods.isEntityInsideWall;

/**
 * The {@code ZenChan} class represents an enemy that can move horizontally and
 * jump, switching direction when hitting a wall. It extends the {@link Enemy}
 * class.
 */
public class ZenChan extends Enemy {

	// Static Fields
	public static final char CODE = 'Z';

	// Constructors

	/**
	 * Constructs a {@code ZenChan} enemy with the specified position.
	 *
	 * @param x the x-coordinate of the ZenChan.
	 * @param y the y-coordinate of the ZenChan.
	 */
	public ZenChan(float x, float y) {
		super(x, y, CODE);
		setDirection(Direction.RIGHT);
		setJumpSpeed(-1.5f);
		setColorState(ColorState.NORMAL);
	}

	// Private Methods

	/**
	 * Switches the direction of ZenChan when it encounters a wall.
	 */
	private void switchDirection() {
		if (isEntityInsideWall(this))
			return;
		switch (direction) {
		case LEFT -> {
			if (isSolidVerticalLine(x - 1, y, y + height))
				setDirection(Direction.RIGHT);
		}
		case RIGHT -> {
			if (isSolidVerticalLine(x + width + 1, y, y + height))
				setDirection(Direction.LEFT);
		}
		default -> throw new IllegalArgumentException("Unexpected value: " + direction);
		}
	}

	// Override Methods

	/**
	 * Updates the state of ZenChan each game tick.
	 */
	@Override
	public void updateEntity() {
		updateYPos();
		if (isDead()) {
			removeEnemy();
		} else {
			updateXPos();
			gravity();
			if (!isStopped) {
				switchDirection();
				move(0.4f * movementSpeed);
				if (randomBoolean(600))
					jump();
			}
		}
	}

}
