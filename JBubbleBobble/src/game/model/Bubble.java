package game.model;

/**
 * The {@code Bubble} class represents an abstract bubble entity that can move
 * and interact with enemies and the game environment. This class provides base
 * functionality for specific bubble types.
 */
public abstract class Bubble extends MovingEntity {

	// Instance Fields
	protected float lifeSpan;

	// Constructors

	/**
	 * Constructs a {@code Bubble} entity with specified position, dimensions, and a
	 * default lifespan.
	 *
	 * @param x      the x-coordinate of the bubble.
	 * @param y      the y-coordinate of the bubble.
	 * @param width  the width of the bubble.
	 * @param height the height of the bubble.
	 * @param code   the character code representing the bubble.
	 */
	public Bubble(float x, float y, float width, float height, char code) {
		super(x, y, width, height, code);
		this.lifeSpan = 10000;
	}

	/**
	 * Constructs a {@code Bubble} entity with specified position, dimensions,
	 * lifespan, and code.
	 *
	 * @param x        the x-coordinate of the bubble.
	 * @param y        the y-coordinate of the bubble.
	 * @param width    the width of the bubble.
	 * @param height   the height of the bubble.
	 * @param lifespan the lifespan of the bubble.
	 * @param code     the character code representing the bubble.
	 */
	public Bubble(float x, float y, float width, float height, float lifeSpan, char code) {
		super(x, y, width, height, code);
		this.lifeSpan = lifeSpan;
	}

	// Getters and Setters

	/**
	 * Returns the lifespan of the bubble.
	 *
	 * @return the bubble's lifespan.
	 */
	public float getLifeSpan() {
		return lifeSpan;
	}

	/**
	 * Sets the lifespan of the bubble.
	 *
	 * @param lifespan the new lifespan of the bubble.
	 */
	public void setLifeSpan(float lifeSpan) {
		this.lifeSpan = lifeSpan;
	}

	// Other Methods

	/**
	 * Decreases the lifespan of the bubble by a specified value.
	 *
	 * @param k the value to decrease the lifespan by.
	 */
	protected void decreaseLifeSpan(float k) {
		setLifeSpan(getLifeSpan() - k);
	}

	/**
	 * Checks if the bubble has hit a specified enemy.
	 *
	 * @param enemy the enemy to check for collision with.
	 * @return true if the bubble has hit the enemy, false otherwise.
	 */
	public boolean hasHitEnemy(Enemy enemy) {
		return enemy.hit(this);
	}

	/**
	 * Abstract method that defines how the bubble pops. Must be implemented by
	 * subclasses.
	 */
	abstract public void pop();

	/**
	 * Causes the bubble to rise at a specified air speed.
	 *
	 * @param airSpeed the speed at which the bubble rises.
	 */
	protected void rise(float airSpeed) {
		setAirSpeed(airSpeed);
		setxSpeed(0);
	}

	/**
	 * Updates the vertical position of the bubble, handling boundary conditions.
	 */
	@Override
	protected void updateYPos() {
		if (y < 0) {
			setY(Level.GAME_HEIGHT);
		} else if (y > Tile.TILE_SIZE || HelpMethods.canMoveHere(x, y + airSpeed, width, height)) {
			setY(y + airSpeed);
		} else {
			setY(y + 15);
			setAirSpeed(-0.2f);
		}
	}

	/**
	 * Updates the bubble's state, including its lifespan and position.
	 */
	@Override
	public void updateEntity() {
		if (lifeSpan <= 0)
			pop();
		else
			decreaseLifeSpan(10.0f); // Decrease the bubble's lifespan (value to be calibrated with the view)

		updateYPos();
		updateXPos();
	}

}
