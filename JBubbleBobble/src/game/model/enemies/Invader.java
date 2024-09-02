package game.model.enemies;

import static game.model.HelpMethods.isEntityInsideWall;
import static game.model.HelpMethods.isSolidVerticalLine;
import game.model.HelpMethods;
import game.model.Model;

/**
 * The {@code Invader} class represents an invader enemy in the game. It extends
 * the {@link Enemy} class and includes behavior specific to invaders, such as
 * switching direction and shooting lasers.
 */
public class Invader extends Enemy {

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
	public void switchDirection() {
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
	 * Makes the invader shoot a laser with a certain probability.
	 */
	public void shootLaser() {
		if (randomBoolean(10))
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

				switchDirection();

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
