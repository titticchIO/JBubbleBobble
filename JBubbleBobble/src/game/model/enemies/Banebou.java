package game.model.enemies;

import java.util.Random;
import game.model.HelpMethods;

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

public class Banebou extends Enemy {
	public static final char CODE = 'N';

	private long lastChangeTime;
	private long changeInterval;

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
		lastChangeTime = System.currentTimeMillis();
		changeInterval = 8000;
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
		lastChangeTime = System.currentTimeMillis();
		changeInterval = 8000;
	}

	/**
	 * Randomly changes the direction of the {@code Banebou} between left and right.
	 * Also updates the {@code xSpeed} to match the new direction.
	 * 
	 * <p>
	 * The interval for changing direction is randomly set between 8000 and 10000
	 * milliseconds.
	 */
	private void changeDirection() {
		changeInterval = new Random().nextLong(8000, 10000);
		switch (direction) {
		case LEFT:
			setDirection(Direction.RIGHT);
			setxSpeed(0.7f * movementSpeed);
			break;
		case RIGHT:
			setDirection(Direction.LEFT);
			setxSpeed(-0.7f * movementSpeed);
			break;
		default:
			break;
		}
	}

	/**
	 * Checks if the direction of the {@code Banebou} needs to be changed based on
	 * the elapsed time.
	 * 
	 * <p>
	 * If the time elapsed since the last direction change exceeds the
	 * {@code changeInterval}, the direction is changed.
	 */
	public void checkAndChangeDirection() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastChangeTime > changeInterval) {
			changeDirection();
			lastChangeTime = currentTime;
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
		super.updateEntity();
		if (!isStopped) {
			super.updateEntity();
			if ((!HelpMethods.canMoveHere(x + xSpeed, y, width, height) || randomBoolean(1000))
					&& !HelpMethods.isEntityInsideWall(x, y, width, height))
				changeDirection();
			jump();
		}
	}
}
