package game.model.enemies;

import static game.model.HelpMethods.isSolidHorizontalLine;
import static game.model.HelpMethods.isSolidVerticalLine;

import game.model.HelpMethods;
import game.model.interfaces.ChangeDirection;

/**
 * The {@code Monsta} class represents a specific type of enemy in the game that
 * bounces off walls and floors while moving. It extends the {@link Enemy} class
 * and includes specific behavior like bouncing.
 */
public class Monsta extends Enemy implements ChangeDirection {

	// Static Fields
	public static final char CODE = 'M';
	public static final float FLIGHT_SPEED = 0.3f; // Minimum speed to prevent the enemy from getting stuck

	// Constructors

	/**
	 * Constructs a {@code Monsta} with specified position.
	 *
	 * @param x the x-coordinate of the monsta.
	 * @param y the y-coordinate of the monsta.
	 */
	public Monsta(float x, float y) {
		super(x, y, CODE);
		initializeMonsta();
	}

	/**
	 * Constructs a {@code Monsta} with specified position and size.
	 *
	 * @param x      the x-coordinate of the monsta.
	 * @param y      the y-coordinate of the monsta.
	 * @param width  the width of the monsta.
	 * @param height the height of the monsta.
	 */
	public Monsta(float x, float y, float width, float height) {
		super(x, y, width, height, CODE);
		initializeMonsta();
	}

	// Private Methods

	/**
	 * Initializes the common properties of a {@code Monsta}.
	 */
	private void initializeMonsta() {
		setxSpeed(FLIGHT_SPEED);
		setAirSpeed(FLIGHT_SPEED);
		setDirection(Direction.RIGHT);
		setColorState(ColorState.NORMAL);
	}

	/**
	 * Handles the bouncing behavior of the monsta when it hits solid surfaces.
	 */
	@Override
	public void changeDirection() {
		// GO DOWN
		if (isSolidHorizontalLine(x, x + width, y - 1)) {
			setAirSpeed(FLIGHT_SPEED * movementSpeed);
			if (randomBoolean(10) && HelpMethods.canMoveHere(x, y + 3, width, height))
				setY(y + 3);
			// GO UP
		} else if (isSolidHorizontalLine(x, x + width, y + height + 1)) {
			setAirSpeed(-FLIGHT_SPEED * movementSpeed);
			if (randomBoolean(10) && HelpMethods.canMoveHere(x, y - 3, width, height))
				setY(y - 3);
		}
		// GO RIGHT
		if (isSolidVerticalLine(x - 1, y, y + height)) {
			setxSpeed(FLIGHT_SPEED * movementSpeed);
			setDirection(Direction.RIGHT);
			if (randomBoolean(10) && HelpMethods.canMoveHere(x - 3, y, width, height))
				setX(x - 3);
			// GO LEFT
		} else if (isSolidVerticalLine(x + width + 1, y, y + height)) {
			setxSpeed(-FLIGHT_SPEED * movementSpeed);
			setDirection(Direction.LEFT);
			if (randomBoolean(10) && HelpMethods.canMoveHere(x + 3, y, width, height))
				setX(x + 3);
		}
	}

	/**
	 * Updates the state of the monsta each game tick.
	 */
	@Override
	public void updateEntity() {
		updateYPos();
		if (isDead()) {
			removeEnemy();
		} else if (!isStopped) {
			changeDirection();
			updateXPos();
		}
	}
}
