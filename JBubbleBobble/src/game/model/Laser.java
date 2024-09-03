package game.model;

/**
 * The {@code Laser} class represents a laser entity that moves vertically
 * across the screen. It extends the {@link MovingEntity} class.
 */
public class Laser extends MovingEntity {

	// Static Fields
	public static final char CODE = '"';

	// Constructors

	/**
	 * Constructs a {@code Laser} entity with specified position and dimensions.
	 *
	 * @param x      the x-coordinate of the laser.
	 * @param y      the y-coordinate of the laser.
	 * @param width  the width of the laser.
	 * @param height the height of the laser.
	 */
	public Laser(float x, float y, float width, float height) {
		super(x, y, width, height, CODE);
		setAirSpeed(2);
	}

	// Override Methods

	/**
	 * Updates the vertical position of the laser based on its air speed.
	 */
	@Override
	protected void updateYPos() {
		setY(y + airSpeed);
	}

	/**
	 * Updates the laser's position and checks if it is out of bounds. If out of
	 * bounds, the laser is removed from the game.
	 */
	@Override
	public void updateEntity() {
		updateYPos();
		if (y > Level.GAME_HEIGHT)
			Model.getInstance().getCurrentLevel().getEnemyManager().removeLaser(this);
	}

}
