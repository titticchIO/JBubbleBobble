package game.model.bubbles;

import java.util.Timer;
import java.util.TimerTask;

import game.model.Model;
import game.model.enemies.Enemy;
import game.model.level.Level;

/**
 * The {@code PlayerBubble} class represents a bubble generated by the player.
 * This bubble can trap enemies and has a specific life span, speed, and travel
 * time.
 */
public class PlayerBubble extends Bubble {

	// Static Fields
	private static long lastKillTime;
	public static final char CODE = '°';
	private static float extraTimeHorizontalMoving = 1;
	private static float extraXSpeed = 1;
	private Timer popTimer;
	// Instance Fields
	private Enemy enemy;
	private boolean hasEnemy;
	private float timeHorizontalMoving;
	private boolean isPopped;

	// Constructors

	/**
	 * Constructs a {@code PlayerBubble} with the specified parameters.
	 * 
	 * @param x                    the x-coordinate of the bubble
	 * @param y                    the y-coordinate of the bubble
	 * @param width                the width of the bubble
	 * @param height               the height of the bubble
	 * @param xSpeed               the horizontal speed of the bubble
	 * @param airSpeed             the vertical speed of the bubble
	 */
	public PlayerBubble(float x, float y, float width, float height, float xSpeed, float airSpeed) {
		super(x, y, width, height, CODE);
		this.xSpeed = xSpeed * extraXSpeed;
		this.airSpeed = airSpeed;
		lifeSpan = 8000;
		timeHorizontalMoving = 600;
	}

	// Static Methods

	/**
	 * Gets the extra travel time multiplier for all player bubbles.
	 *
	 * @return the extra travel time multiplier
	 */
	public static float getExtraTravelTime() {
		return extraTimeHorizontalMoving;
	}

	/**
	 * Sets the extra travel time multiplier for all player bubbles.
	 *
	 * @param extraTravelTime the new extra travel time multiplier
	 */
	public static void setExtraTravelTime(float extraTravelTime) {
		PlayerBubble.extraTimeHorizontalMoving = extraTravelTime;
	}

	/**
	 * Gets the extra horizontal speed multiplier for all player bubbles.
	 *
	 * @return the extra horizontal speed multiplier
	 */
	public static float getExtraXSpeed() {
		return extraXSpeed;
	}

	/**
	 * Sets the extra horizontal speed multiplier for all player bubbles.
	 *
	 * @param extraXSpeed the new extra horizontal speed multiplier
	 */
	public static void setExtraXSpeed(float extraXSpeed) {
		PlayerBubble.extraXSpeed = extraXSpeed;
	}

	// Getters and Setters

	/**
	 * Gets the enemy trapped in this bubble.
	 *
	 * @return the trapped enemy, or {@code null} if no enemy is trapped
	 */
	public Enemy getEnemy() {
		return enemy;
	}

	/**
	 * Sets the enemy to be trapped in this bubble.
	 *
	 * @param enemy the enemy to trap in the bubble
	 */
	public void setEnemy(Enemy enemy) {
		if (this.enemy == null)
			this.enemy = enemy;
	}

	/**
	 * Checks if this bubble has a trapped enemy.
	 *
	 * @return {@code true} if an enemy is trapped, {@code false} otherwise
	 */
	public boolean hasEnemy() {
		return hasEnemy;
	}

	/**
	 * Gets the time this bubble will continue moving horizontally before starting
	 * to rise.
	 *
	 * @return the time remaining for horizontal movement
	 */
	public float getTimeHorizontalMoving() {
		return timeHorizontalMoving;
	}

	/**
	 * Sets the time this bubble will continue moving horizontally before starting
	 * to rise.
	 *
	 * @param timeHorizontalMoving the new time for horizontal movement
	 */
	public void setTimeHorizontalMoving(float timeHorizontalMoving) {
		this.timeHorizontalMoving = timeHorizontalMoving;
	}

	public boolean isHasEnemy() {
		return hasEnemy;
	}

	public void setHasEnemy(boolean hasEnemy) {
		this.hasEnemy = hasEnemy;
	}

	public boolean isPopped() {
		return isPopped;
	}

	// Other Methods

	public void resetLifeSpan() {
		lifeSpan = 8000;
		if (popTimer != null)
			popTimer.cancel();
	}

	/**
	 * Decreases the remaining horizontal movement time by a specified amount.
	 * 
	 * @param k the amount by which to decrease the horizontal movement time
	 */
	protected void decreaseTimeHorizontalMoving(float k) {
		setTimeHorizontalMoving(getTimeHorizontalMoving() - k);
	}

	/**
	 * Makes the bubble rise with the specified vertical speed.
	 * 
	 * @param airSpeed the vertical speed at which the bubble rises
	 */
	@Override
	protected void rise(float airSpeed) {
		setAirSpeed(airSpeed);
		setxSpeed(0);
	}

	/**
	 * Pops the bubble, releasing any trapped enemy and removing the bubble from the
	 * game.
	 */
	@Override
	public void pop() {
		isPopped = true;
		setAirSpeed(0);
		if (hasEnemy && !enemy.isDead()) {
			enemy.setPosition(getX(), getY());
			Model.getInstance().getCurrentLevel().getEnemyManager().addEnemy(enemy);
			hasEnemy = false;
		}
		if (popTimer == null) {
			popTimer = new Timer("Pop Timer");
			popTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					Model.getInstance().getCurrentLevel().getBubbleManager().removePlayerBubble(PlayerBubble.this);
					this.cancel();
				}
			}, 400);
		}

	}

	/**
	 * Pops the bubble, kills the trapped enemy, and awards points to the player.
	 * Also causes nearby bubbles to pop and kill their trapped enemies.
	 */
	public void popAndKill() {
		if (isPopped)
			return;
		if (hasEnemy) {
			enemy.setPosition(getX(), getY());
			Model.getInstance().getCurrentLevel().getEnemyManager().addEnemy(enemy);
			hasEnemy = false;
			enemy.kill();
			if (System.currentTimeMillis() - lastKillTime < 500)
				Level.setSimultaneousKills(Level.getSimultaneousKills() + 1);
			lastKillTime = System.currentTimeMillis();
		}
		pop();
		Model.getInstance().getCurrentUser().addPoints(100);
		Model.getInstance().getCurrentLevel().getBubbleManager().getPlayerBubbles().forEach(pb -> {
			if (getDistanceFrom(pb) < 7) {
				pb.popAndKill();
				Model.getInstance().getCurrentLevel().getPowerupManager().getPowerupFactory().increaseNumberOfBubblesPopped();
			}
		});
	}

	/**
	 * Updates the bubble's position and state based on its remaining life span and
	 * travel time.
	 */
	@Override
	public void updateEntity() {
		if (lifeSpan <= 0) {
			pop();
		} else {
			decreaseLifeSpan(10.0f); // Decrease the bubble's life span
			decreaseTimeHorizontalMoving(10.0f * extraXSpeed / extraTimeHorizontalMoving); // Decrease horizontal
																							// movement time
		}

		if (lifeSpan <= 500) {
			airSpeed = 0;
			isPopped = true;
		}

		if (timeHorizontalMoving <= 0)
			updateYPos();
		else
			updateXPos();
	}
}
