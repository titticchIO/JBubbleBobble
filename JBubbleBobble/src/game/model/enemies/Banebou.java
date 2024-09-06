package game.model.enemies;

import game.model.HelpMethods;
import game.model.interfaces.ChangeDirection;
import game.model.interfaces.Gravity;
import game.model.interfaces.Jumping;

/**
 * Represents a type of enemy called {@code Banebou}. The {@code Banebou} class
 * extends {@link Enemy} and features behavior such as changing direction at
 * random intervals and jumping.
 * 
 * <p>
 * {@code Banebou} changes its direction between left and right at random
 * intervals, providing dynamic movement patterns. The enemy also performs
 * jumping actions.
 */

public class Banebou extends Enemy implements Gravity, Jumping, ChangeDirection {
	public static final char CODE = 'N';

	/**
	 * Constructs a {@code Banebou} with the specified position. Initializes the
	 * enemy with default speed, jump speed, direction, and color.
	 * 
	 * @param x the initial x-coordinate of the {@code Banebou}
	 * @param y the initial y-coordinate of the {@code Banebou}
	 */
	public Banebou(float x, float y) {
		super(x, y, CODE);
		setxSpeed(0.7f);
		setJumpSpeed(-1.5f);
		setDirection(Direction.RIGHT);
		setColorState(ColorState.NORMAL);
	}

	/**
	 * Constructs a {@code Banebou} with the specified position, width, and height.
	 * Initializes the enemy with default speed, jump speed, direction, and color.
	 * 
	 * @param x      the initial x-coordinate of the {@code Banebou}
	 * @param y      the initial y-coordinate of the {@code Banebou}
	 * @param width  the width of the {@code Banebou}
	 * @param height the height of the {@code Banebou}
	 */
	public Banebou(float x, float y, float width, float height) {
		super(x, y, width, height, CODE);
		setxSpeed(0.7f);
		setJumpSpeed(-1.5f);
		setDirection(Direction.RIGHT);
		setColorState(ColorState.NORMAL);
	}

	/**
	 * Randomly changes the direction of the {@code Banebou} between left and right.
	 * Also updates the {@code xSpeed} to match the new direction.
	 * 
	 * <p>
	 * The interval for changing direction is randomly set between 8000 and 10000
	 * milliseconds.
	 */
	@Override
	public void changeDirection() {
		if ((!HelpMethods.canMoveHere(x + xSpeed, y, width, height) || randomBoolean(1000))
				&& !HelpMethods.isEntityInsideWall(this)) {
			switch (direction) {
			case LEFT:
				setDirection(Direction.RIGHT);
				setxSpeed(0.5f * movementSpeed);
				break;
			case RIGHT:
				setDirection(Direction.LEFT);
				setxSpeed(-0.5f * movementSpeed);
				break;
			default:
				break;
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

	/**
	 * Updates the state of the {@code Banebou}. This includes moving, checking for
	 * collisions, and possibly changing direction. The enemy also performs jumping
	 * actions.
	 * 
	 * <p>
	 * If the {@code Banebou} is not stopped and either cannot move to its current
	 * x-position or a random boolean check succeeds, the direction will be changed.
	 */
	@Override
	public void updateEntity() {
		if (isDead()) {
			removeEnemy();
			updateYPos();
		} else {
			gravity();
			if (!isStopped) {
				updateXPos();
				updateYPos();
				changeDirection();
				jump();
			}
		}
	}

}
