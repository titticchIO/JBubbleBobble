package game.model.enemies;

import game.model.Bouncing;
import game.model.level.Level;
import game.model.tiles.Tile;

/**
 * The {@code SkelMonsta} class represents an enemy that can move horizontally
 * and vertically, changing direction upon hitting the boundaries of the game
 * area. It extends the {@link Enemy} class.
 */
public class SkelMonsta extends Enemy implements Bouncing{

	// Static Fields
	public static final char CODE = 'S';
	public static final float FLIGHT_SPEED = 0.4f;

	// Constructors

	/**
	 * Constructs a {@code SkelMonsta} with the specified position.
	 *
	 * @param x the x-coordinate of the SkelMonsta.
	 * @param y the y-coordinate of the SkelMonsta.
	 */
	public SkelMonsta(float x, float y) {
		super(x, y, CODE);
		initializeSkelMonsta();
	}

	/**
	 * Constructs a {@code SkelMonsta} with the specified position and size.
	 *
	 * @param x      the x-coordinate of the SkelMonsta.
	 * @param y      the y-coordinate of the SkelMonsta.
	 * @param width  the width of the SkelMonsta.
	 * @param height the height of the SkelMonsta.
	 */
	public SkelMonsta(float x, float y, float width, float height) {
		super(x, y, width, height, CODE);
		initializeSkelMonsta();
	}

	// Private Methods

	/**
	 * Initializes the common properties of a {@code SkelMonsta}.
	 */
	private void initializeSkelMonsta() {
		setxSpeed(0.3f);
		setAirSpeed(0.3f);
		setDirection(Direction.RIGHT);
		setColorState(ColorState.NORMAL);
	}

	/**
	 * Controls the bouncing movement of the SkelMonsta. Changes direction when it
	 * hits the game boundaries.
	 */
	@Override
	public void bounce() {
		// Move Down
		if (y - 1 <= Tile.TILE_SIZE) {
			setAirSpeed(FLIGHT_SPEED);
			if (randomBoolean(10))
				setY(y + 3);
			// Move Up
		} else if (y + height + 1 >= Level.GAME_HEIGHT - Tile.TILE_SIZE) {
			setAirSpeed(-FLIGHT_SPEED);
			if (randomBoolean(10))
				setY(y - 3);
		}

		// Move Right
		if (x - 1 <= Tile.TILE_SIZE) {
			setxSpeed(FLIGHT_SPEED);
			setDirection(Direction.RIGHT);
			if (randomBoolean(10))
				setX(x - 3);
			// Move Left
		} else if (x + width + 1 >= Level.GAME_WIDTH - Tile.TILE_SIZE) {
			setxSpeed(-FLIGHT_SPEED);
			setDirection(Direction.LEFT);
			if (randomBoolean(10))
				setX(x + 3);
		}
	}

	// Override Methods

	/**
	 * Updates the horizontal position of the SkelMonsta based on its current speed
	 * and direction.
	 */
	@Override
	public void updateXPos() {
		setX(x + xSpeed);
	}

	/**
	 * Updates the vertical position of the SkelMonsta based on its current speed
	 * and direction.
	 */
	@Override
	public void updateYPos() {
		setY(y + airSpeed);
	}

	/**
	 * Updates the state of the SkelMonsta each game tick.
	 */
	@Override
	public void updateEntity() {
		updateYPos();
		if (isDead())
			removeEnemy();
		else if (!isStopped) {
			bounce();
			updateXPos();
		}
	}
}
