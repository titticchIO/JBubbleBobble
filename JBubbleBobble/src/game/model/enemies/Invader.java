package game.model.enemies;

import static game.model.HelpMethods.isEntityInsideWall;
import static game.model.HelpMethods.isSolidVerticalLine;

import game.model.HelpMethods;
import game.model.Model;
import game.model.interfaces.ChangeDirection;
import game.model.interfaces.Gravity;
import game.model.interfaces.Shooting;

/**
 * The {@code Invader} class represents an invader enemy in the game. It extends
 * the {@link Enemy} class and includes behavior specific to invaders, such as
 * switching direction and shooting lasers.
 */
public class Invader extends Enemy implements Gravity, Shooting, ChangeDirection {

	// Static Fields
	public static final char CODE = 'I';
	public static final long ATTACK_INTERVAL = 1000; // Interval in milliseconds between attacks.

	// Instance Fields
	private boolean landed; // Indicates if the invader has landed on the ground.

	// Constructors

	/**
	 * Constructs an {@code Invader} with specified position and size.
	 *
	 * @param x      the x-coordinate of the invader.
	 * @param y      the y-coordinate of the invader.
	 * @param width  the width of the invader.
	 * @param height the height of the invader.
	 */
	public Invader(float x, float y, float width, float height) {
		super(x, y, width, height, CODE);
	}

	/**
	 * Constructs an {@code Invader} with specified position.
	 *
	 * @param x the x-coordinate of the invader.
	 * @param y the y-coordinate of the invader.
	 */
	public Invader(float x, float y) {
		super(x, y, CODE);
		setAirSpeed(1);
		setDirection(Direction.LEFT);
		setColorState(ColorState.NORMAL);
	}

	// Methods

	/**
	 * Switches the direction of the invader if it encounters a wall.
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
	 * Randomizes the direction of the invader between left and right.
	 */
	private void randomizeDirection() {
		if (randomBoolean(2))
			setDirection(Direction.RIGHT);
		else
			setDirection(Direction.LEFT);
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
	 * Makes the invader shoot a laser with a certain probability.
	 */
	@Override
	public void shoot() {
		if (randomBoolean(7))
			Model.getInstance().getCurrentLevel().getEnemyManager().addLaser(new Laser(x + 5, y + height, 6, 20));
	}

	/**
	 * Updates the state of the invader each game tick.
	 */
	@Override
	public void updateEntity() {
		updateYPos();
		if (isDead()) {
			removeEnemy();
		} else {
			if (!isStopped) {
				if (!HelpMethods.isEntityGrounded(this) && landed)
					landed = false;

				if (HelpMethods.isEntityGrounded(this) && !landed) {
					landed = true;
					randomizeDirection();
				}

				changeDirection();

				if (!inAir) {
					updateXPos();
				} else {
					setAirSpeed(0.5f);
				}

				move(0.5f * movementSpeed);
			}
		}
	}
}
