package game.model;

import java.util.Random;

import game.model.Fruit.FruitType;

/**
 * The {@code Enemy} class represents an abstract enemy in the game. It extends
 * the {@link MovingEntity} class and provides additional functionality such as
 * changing color, being killed, and dropping fruit.
 */
public abstract class Enemy extends MovingEntity {

	// Static Fields
	public static final int RED_TIME = 10000; // Time in milliseconds before the enemy turns red.

	// Non-static Fields
	private ColorState colorState; // Current color of the entity.
	protected float redXSpeed; // Speed when the enemy is in the red state.
	protected float movementSpeed; // Current movement speed of the enemy.
	private boolean dead; // Indicates if the enemy is dead.
	private boolean isPopped; // Indicates if the enemy is popped.
	protected boolean isStopped; // Indicates if the enemy is stopped.

	/**
	 * Enum representing possible colors for the enemy.
	 */
	public enum ColorState {
		NORMAL, RED
	}

	// Constructor

	/**
	 * Constructs an {@code Enemy} with specified position and character code.
	 *
	 * @param x    the x-coordinate of the enemy.
	 * @param y    the y-coordinate of the enemy.
	 * @param code the character code representing the enemy.
	 */
	public Enemy(float x, float y, char code) {
		super(x, y, code);
		redXSpeed = 2.0f;
		colorState = ColorState.NORMAL;
		movementSpeed = 1.0f;
	}

	/**
	 * Constructs an {@code Enemy} with specified position, dimensions, and
	 * character code.
	 *
	 * @param x      the x-coordinate of the enemy.
	 * @param y      the y-coordinate of the enemy.
	 * @param width  the width of the enemy.
	 * @param height the height of the enemy.
	 * @param code   the character code representing the enemy.
	 */
	public Enemy(float x, float y, float width, float height, char code) {
		super(x, y, width, height, code);
		redXSpeed = 2.0f;
		colorState = ColorState.NORMAL;
		movementSpeed = 1.0f;
	}

	// Getters

	/**
	 * Returns the current movement speed of the enemy.
	 *
	 * @return the movement speed of the enemy.
	 */
	public float getMovementSpeed() {
		return movementSpeed;
	}

	/**
	 * Returns whether the enemy is dead.
	 *
	 * @return {@code true} if the enemy is dead, {@code false} otherwise.
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * Returns whether the enemy is popped.
	 *
	 * @return {@code true} if the enemy is popped, {@code false} otherwise.
	 */
	public boolean isPopped() {
		return isPopped;
	}

	/**
	 * Returns whether the enemy is stopped.
	 *
	 * @return {@code true} if the enemy is stopped, {@code false} otherwise.
	 */
	public boolean isStopped() {
		return isStopped;
	}

	/**
	 * Returns the current color state of the enemy.
	 *
	 * @return the color state of the enemy.
	 */
	public ColorState getColorState() {
		return colorState;
	}

	// Setters

	/**
	 * Sets the movement speed of the enemy.
	 *
	 * @param speed the new movement speed of the enemy.
	 */
	public void setMovementSpeed(float speed) {
		this.movementSpeed = speed;
	}

	/**
	 * Sets whether the enemy is stopped.
	 *
	 * @param isStopped {@code true} if the enemy is stopped, {@code false}
	 *                  otherwise.
	 */
	public void setStopped(boolean isStopped) {
		this.isStopped = isStopped;
	}

	/**
	 * Sets the color state of the enemy.
	 *
	 * @param colorState the new color state of the enemy.
	 */
	public void setColorState(ColorState colorState) {
		this.colorState = colorState;
		if (colorState == ColorState.RED)
			movementSpeed = redXSpeed;
	}

	// Other Methods

	/**
	 * Kills the enemy by setting its state to dead and adjusting its speed.
	 */
	public void kill() {
		dead = true;
		setxSpeed(0);
		setAirSpeed(0.7f);
	}

	/**
	 * Removes the enemy from the game if it is grounded, dropping a random fruit.
	 */
	public void removeEnemy() {
		if (HelpMethods.isEntityGrounded(this)) {
			Fruit fruit = new Fruit(x, y, switch (new Random().nextInt(5)) {
			case 0 -> FruitType.BANANA;
			case 1 -> FruitType.ORANGE;
			case 2 -> FruitType.PEACH;
			case 3 -> FruitType.PEAR;
			default -> FruitType.WATERMELON;
			});
			Model.getInstance().getCurrentLevel().getFruitManager().addFruit(fruit);
			Model.getInstance().getCurrentLevel().getEnemyManager().removeEnemy(this);
		}
	}

	/**
	 * Returns a random boolean based on a given chance.
	 *
	 * @param chances the number of chances to get {@code true}.
	 * @return {@code true} with a 1 in {@code chances} probability, otherwise
	 *         {@code false}.
	 */
	public boolean randomBoolean(int chances) {
		return new Random().nextInt(chances) == 0;
	}

	/**
	 * Updates the state of the enemy each game tick.
	 */
	@Override
	public void updateEntity() {
		super.updateEntity();
	}
}
