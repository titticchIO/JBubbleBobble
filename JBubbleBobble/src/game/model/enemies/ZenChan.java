package game.model.enemies;

import static game.model.HelpMethods.isSolidVerticalLine;

import game.model.HelpMethods;
import game.model.interfaces.ChangeDirection;
import game.model.interfaces.Gravity;
import game.model.interfaces.Jumping;

import static game.model.HelpMethods.isEntityInsideWall;

public class ZenChan extends Enemy implements Gravity, Jumping, ChangeDirection {

	/**
	 * The {@code ZenChan} class represents an enemy that can move horizontally and
	 * jump, switching direction when hitting a wall. It extends the {@link Enemy}
	 * class.
	 */

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
		setJumpSpeed(-2.0f);
		setColorState(ColorState.NORMAL);
	}

	// Private Methods

	/**
	 * Switches the direction of ZenChan when it encounters a wall.
	 */
	@Override
	public void changeDirection() {
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
	
	
	/**
	 * Applies gravity to the entity, increasing its air speed up to a maximum value
	 * if the entity is not grounded. This method simulates the effect of gravity on
	 * the entity when it is in the air.
	 */
	@Override
	public void gravity() {
		if (!HelpMethods.isEntityGrounded(this) && airSpeed < MAX_FALLING_SPEED) {
			inAir = true;
			airSpeed += GRAVITY;
		}
	}

	// Override Methods

	/**
	 * Updates the state of ZenChan each game tick.
	 */
	@Override
	public void updateEntity() {
		if (isDead()) {
			updateYPos();
			removeEnemy();
		} else {
			if (!isStopped) {
				gravity();
				updateYPos();
				updateXPos();
				changeDirection();
				move(0.3f * movementSpeed);
				if (randomBoolean(500))
					jump();
			}
		}
	}

	@Override
	public void jump() {
		if (!inAir && !HelpMethods.isEntityInsideWall(this)) {
			inAir = true;
			airSpeed = jumpSpeed;
		}
	}

}
