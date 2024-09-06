package game.model.enemies;

import java.util.Random;

import game.model.HelpMethods;
import game.model.interfaces.ChangeDirection;
import game.model.level.Level;

/**
 * The {@code Pulpul} class represents a unique enemy that can move in four
 * directions and changes direction upon hitting obstacles. It extends the
 * {@link Enemy} class.
 */
public class Pulpul extends Enemy implements ChangeDirection {

	// Static Fields
	public static final char CODE = 'U';

	// Constructors

	/**
	 * Constructs a {@code Pulpul} with the specified position.
	 *
	 * @param x the x-coordinate of the pulpul.
	 * @param y the y-coordinate of the pulpul.
	 */
	public Pulpul(float x, float y) {
		super(x, y, CODE);
		initializePulpul();
	}

	/**
	 * Constructs a {@code Pulpul} with the specified position and size.
	 *
	 * @param x      the x-coordinate of the pulpul.
	 * @param y      the y-coordinate of the pulpul.
	 * @param width  the width of the pulpul.
	 * @param height the height of the pulpul.
	 */
	public Pulpul(float x, float y, float width, float height) {
		super(x, y, width, height, CODE);
		initializePulpul();
	}

	// Private Methods

	/**
	 * Initializes the common properties of a {@code Pulpul}.
	 */
	private void initializePulpul() {
		setxSpeed(0.5f);
		setAirSpeed(0);
		setColorState(ColorState.NORMAL);
	}

	/**
	 * Changes the direction of the pulpul when it encounters an obstacle.
	 */
	@Override
	public void changeDirection() {
		if (randomBoolean(10))
			randomizeDirection();

		if (xSpeed != 0) {
			// Switch between left and right
			setxSpeed(-xSpeed);
			setAirSpeed(0);
		} else if (airSpeed != 0) {
			// Switch between up and down
			setxSpeed(0);
			setAirSpeed(-airSpeed);
		} else {
			// If both speeds are zero, randomize direction again
			randomizeDirection();
		}
	}

	/**
	 * Randomizes the direction of the pulpul's movement.
	 */
	private void randomizeDirection() {
		int randomInt = new Random().nextInt(4);
		switch (randomInt) {
		case 0 -> {
			setAirSpeed(-0.5f * movementSpeed);
			setxSpeed(0);
		}
		case 1 -> {
			setAirSpeed(0.5f * movementSpeed);
			setxSpeed(0);
		}
		case 2 -> {
			direction = Direction.LEFT;
			setxSpeed(-0.5f * movementSpeed);
			setAirSpeed(0);
		}
		case 3 -> {
			direction = Direction.RIGHT;
			setxSpeed(0.5f * movementSpeed);
			setAirSpeed(0);
		}
		}
	}

	// Override Methods

	/**
	 * Updates the horizontal position of the pulpul based on its current speed and
	 * direction.
	 */
	@Override
	public void updateXPos() {
		if (HelpMethods.canMoveHere(x + xSpeed, y, width, height))
			setX(x + xSpeed);
		else
			changeDirection(); // Change direction if it hits an obstacle
	}

	/**
	 * Updates the vertical position of the pulpul based on its current speed and
	 * direction.
	 */
	@Override
	public void updateYPos() {
		if (y > Level.GAME_HEIGHT + 1)
			setY(-1);
		else if (y < -2)
			setY(Level.GAME_HEIGHT);
		else {
			if (HelpMethods.canMoveHere(x, y + airSpeed, width, height))
				setY(y + airSpeed);
			else
				changeDirection(); // Change direction if it hits an obstacle
		}
	}

	/**
	 * Updates the state of the pulpul each game tick.
	 */
	@Override
	public void updateEntity() {
		if (isDead()) {
			updateYPos();
			removeEnemy();
		} else if (!isStopped) {
			if (randomBoolean(200))
				randomizeDirection();
			updateYPos();
			updateXPos(); // Update position based on current direction
		}
	}
}
