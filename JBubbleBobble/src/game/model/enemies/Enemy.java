package game.model.enemies;

import java.util.Random;
import game.model.entities.MovingEntity;

/**
 * The {@code Enemy} class represents an enemy entity in the game. It is an
 * abstract class that extends {@code MovingEntity}, and provides functionality
 * specific to enemies such as the ability to "pop" and generate random boolean
 * outcomes.
 */
public abstract class Enemy extends MovingEntity {

	/**
	 * Indicates whether the enemy has been "popped".
	 */
	protected boolean isPopped;
	protected boolean isStopped;

	/**
	 * Constructs an {@code Enemy} object with the specified position and entity
	 * code.
	 *
	 * @param x    The x-coordinate of the enemy.
	 * @param y    The y-coordinate of the enemy.
	 * @param code The unique code representing the enemy.
	 */
	public Enemy(float x, float y, String code) {
		super(x, y, code);
	}

	/**
	 * Constructs an {@code Enemy} object with the specified position, dimensions,
	 * and entity code.
	 *
	 * @param x      The x-coordinate of the enemy.
	 * @param y      The y-coordinate of the enemy.
	 * @param width  The width of the enemy.
	 * @param height The height of the enemy.
	 * @param code   The unique code representing the enemy.
	 */
	public Enemy(float x, float y, float width, float height, String code) {
		super(x, y, width, height, code);
	}

	/**
	 * Returns whether the enemy has been "popped".
	 *
	 * @return {@code true} if the enemy has been popped, {@code false} otherwise.
	 */
	public boolean isPopped() {
		return isPopped;
	}

	/**
	 * Sets the enemy's isPopped state to {@code true}.
	 */
	public void pop() {
		isPopped = true;
	}

	/**
	 * Generates a random boolean outcome based on the given chances.
	 * 
	 * @param chances The number of possible outcomes, where the method returns
	 *                {@code true} if the random number generated is 0.
	 * @return {@code true} if the randomly generated number is 0, {@code false}
	 *         otherwise.
	 */
	public boolean randomBoolean(int chances) {
		return new Random().nextInt(0, chances) == 0;
	}

	/**
	 * Returns whether the enemy is stopped.
	 *
	 * @return {@code true} if the enemy has been stopped, {@code false} otherwise.
	 */
	public boolean isStopped() {
		return isStopped;
	}

	/**
	 * Sets whether the player is currently stopped.
	 *
	 * @param isStopped {@code true} if the player is stopped, {@code false}
	 *                  otherwise.
	 */
	public void setStopped(boolean isStopped) {
		this.isStopped = isStopped;
	}

}
