package game.model;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The {@code Powerup} class represents a power-up entity in the game. This
 * abstract class handles common functionalities such as checking collisions
 * with the player, applying effects, and managing the duration of the power-up.
 */
public abstract class Powerup extends Entity {

	// Instance Fields
	protected final int points;
	protected long duration;
	protected boolean toRemove;

	// Constructor

	/**
	 * Constructs a new Powerup with the specified position, code, points, and
	 * duration.
	 *
	 * @param x        the x-coordinate of the power-up.
	 * @param y        the y-coordinate of the power-up.
	 * @param code     the character code representing the power-up.
	 * @param points   the points awarded to the player upon collecting the
	 *                 power-up.
	 * @param duration the duration for which the power-up effect lasts.
	 */
	public Powerup(float x, float y, char code, int points, long duration) {
		super(x, y, code);
		this.points = points;
		this.duration = duration;
		this.toRemove = false; // Initialization moved to constructor
	}

	// Getters

	/**
	 * Returns the points awarded by this power-up.
	 *
	 * @return the points awarded by the power-up.
	 */
	public long getPoints() {
		return points;
	}

	/**
	 * Returns the duration of this power-up's effect.
	 *
	 * @return the duration of the power-up's effect.
	 */
	public float getDuration() {
		return duration;
	}

	/**
	 * Returns whether this power-up is marked for removal.
	 *
	 * @return {@code true} if the power-up is marked for removal, {@code false}
	 *         otherwise.
	 */
	public boolean isToRemove() {
		return toRemove;
	}

	// Other Methods

	/**
	 * Checks if the player collides with this power-up.
	 *
	 * @return {@code true} if the player collides with the power-up, {@code false}
	 *         otherwise.
	 */
	private boolean checkPlayerCollision() {
		return Entity.checkCollision(Player.getInstance(), this).isPresent();
	}

	/**
	 * Applies the effect of the power-up to the player.
	 */
	public void effect() {
		setX(-2000); // Moves the power-up off-screen
		Model.getInstance().getCurrentUser().addPoints(points); // Points are added directly to the player instance
	}

	/**
	 * Resets the power-up to its normal state, marking it for removal.
	 */
	public void resetToNormal() {
		toRemove = true;
	}

	/**
	 * Updates the state of the power-up, checking for collisions and applying
	 * effects if necessary.
	 */
	public void updatePowerup() {
		if (checkPlayerCollision()) {
			Model.getInstance().getCurrentUser().addPoints(points);

			switch (this.getClass().getSimpleName()) {
			case "PinkCandy" -> Model.getInstance().getCurrentLevel().getPowerupManager().increaseNumberOfPinkCandies();
			case "BlueCandy" -> Model.getInstance().getCurrentLevel().getPowerupManager().increaseNumberOfBlueCandies();
			case "YellowCandy" ->
				Model.getInstance().getCurrentLevel().getPowerupManager().increaseNumberOfYellowCandies();
			}

			effect();

			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					resetToNormal();
				}
			}, duration);
		}
	}
}
