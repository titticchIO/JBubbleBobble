package game.model.entities;

import game.model.HelpMethods;
import game.model.Model;
import game.model.Tile;
import game.model.interfaces.Gravity;
import game.model.interfaces.Jumping;
import game.model.interfaces.Shooting;
import game.model.interfaces.Vulnerable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The {@code Player} class represents the player character in the game. It
 * extends the {@link MovingEntity} class and provides additional functionality
 * such as jumping, shooting bubbles, and managing player lives. This class
 * implements the singleton pattern to ensure only one player instance exists in
 * the game.
 */
public class Player extends MovingEntity implements Gravity, Jumping, Shooting, Vulnerable {

	// Static Fields
	public static final char CODE = 'P';
	public static final int NUMBER_OF_LIVES = 3; // The total number of lives the player starts with.
	public static final long INVULNERABILITY_INTERVAL = 5000; // The duration of invulnerability after losing a life.
	public static final long ATTACK_INTERVAL = 100; // The minimum time interval between bubble shots (in milliseconds).
	private static Player instance; // Singleton instance of the Player class.

	// Non-static Fields
	private Timer stunTimer;
	private Timer invulnerabilityTimer;

	private Direction bubbleDirection; // Direction in which the player will shoot bubbles.
	private int lives; // Current number of lives the player has.
	private boolean isStunned; // Indicates if the entity is stunned
	private long attackSpeed; // The speed at which the player can shoot bubbles.
	private float extraXSpeed; // Extra speed applied to the player's movement.
	private float previousX; // Previous x-coordinate used for distance tracking.
	private boolean isJumping; // Indicates whether the player is currently jumping.
	private boolean isInvulnerable; // Indicates whether the player is invulnerable.
	private boolean canShoot; // Indicates whether the player can shoot a bubble.
	private boolean specialBubbleActive; // Indicates whether the Special Bubble is affecting the player.
	private boolean crystalRingActive;
	private boolean amethystRingActive;
	private boolean rubyRingActive;

	/**
	 * Returns the singleton instance of the {@code Player} class.
	 *
	 * @return the singleton {@code Player} instance.
	 */
	public static Player getInstance() {
		return instance;
	}

	/**
	 * Returns the singleton instance of the {@code Player} class, initializing it
	 * if necessary.
	 *
	 * @param x      the initial x-coordinate of the player.
	 * @param y      the initial y-coordinate of the player.
	 * @param width  the width of the player.
	 * @param height the height of the player.
	 * @return the singleton {@code Player} instance.
	 */
	public static Player getInstance(float x, float y, float width, float height) {
		if (instance == null)
			instance = new Player(x, y, width, height);
		return instance;
	}

	// Constructor

	/**
	 * Private constructor to enforce singleton pattern.
	 *
	 * @param x      the initial x-coordinate of the player.
	 * @param y      the initial y-coordinate of the player.
	 * @param width  the width of the player.
	 * @param height the height of the player.
	 */
	private Player(float x, float y, float width, float height) {
		super(x, y, width, height, CODE);
		setBubbleDirection(Direction.RIGHT);
		lives = NUMBER_OF_LIVES;
		extraXSpeed = 1;
		setCanShoot(true);
		attackSpeed = 2;
		previousX = x;
	}

	// Getters

	/**
	 * Returns the current number of lives the player has.
	 *
	 * @return the player's current number of lives.
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * Returns current bubble direction.
	 *
	 * @return current bubble shooting direction.
	 */
	public Direction getBubbleDirection() {
		return bubbleDirection;
	}

	/**
	 * Returns whether the player is currently jumping.
	 *
	 * @return {@code true} if the player is jumping, {@code false} otherwise.
	 */
	public boolean isJumping() {
		return isJumping;
	}

	/**
	 * Returns whether the player is currently stunned.
	 *
	 * @return {@code true} if the player is jumping, {@code false} otherwise.
	 */
	public boolean isStunned() {
		return isStunned;
	}

	/**
	 * Returns whether the player can shoot.
	 *
	 * @return {@code true} if the player can shoot, {@code false} otherwise.
	 */
	public boolean canShoot() {
		return canShoot;
	}

	/**
	 * Returns whether the player is currently shooting.
	 *
	 * @return {@code true} if the player is shooting, {@code false} otherwise.
	 */
	public boolean getSpecialBubbleActive() {
		return specialBubbleActive;
	}

	/**
	 * Returns whether the player is currently under the effect of CrystalRing.
	 *
	 * @return {@code true} if the player is under the effect of CrystalRing,
	 *         {@code false} otherwise.
	 */
	public boolean isCrystalRingActive() {
		return crystalRingActive;
	}

	/**
	 * Returns whether the player is currently under the effect of AmethystRing.
	 *
	 * @return {@code true} if the player is under the effect of AmethystRing,
	 *         {@code false} otherwise.
	 */
	public boolean isAmethystRingActive() {
		return amethystRingActive;
	}

	/**
	 * Returns whether the player is currently under the effect of RubyRing.
	 *
	 * @return {@code true} if the player is under the effect of RubyRing,
	 *         {@code false} otherwise.
	 */
	public boolean isRubyRingActive() {
		return rubyRingActive;
	}

	/**
	 * Returns whether the player is currently invulnerable.
	 *
	 * @return {@code true} if the player is invulnerable, {@code false} otherwise.
	 */
	public boolean isInvulnerable() {
		return isInvulnerable;
	}

	// Setters

	/**
	 * Sets the player's movement direction and adjusts the bubble shooting
	 * direction accordingly.
	 *
	 * @param direction the new movement direction for the player.
	 */
	@Override
	public void setDirection(Direction direction) {
		super.setDirection(direction);
		if (direction == Direction.RIGHT || direction == Direction.LEFT)
			setBubbleDirection(direction);
	}

	/**
	 * Sets the player's x-coordinate and updates the distance traveled.
	 *
	 * @param x the new x-coordinate of the player.
	 */
	@Override
	public void setX(float x) {
		// Updates distance traveled according to the difference between x and
		// previousX.
		Model.getInstance().getCurrentLevel().getPowerupManager().getPowerupFactory()
				.increaseDistanceTraveled(Math.abs(x - previousX));
		if (crystalRingActive && previousX != x)
			Model.getInstance().getCurrentUser().addPoints(1);

		// Updates previousX with new x value.
		previousX = x;
		this.x = x;
	}

	/**
	 * Sets the extra speed applied to the player's movement.
	 *
	 * @param extraXSpeed the additional speed to be applied to the player's
	 *                    movement.
	 */
	public void setExtraXSpeed(float extraXSpeed) {
		this.extraXSpeed = extraXSpeed;
	}

	/**
	 * Sets whether the player is currently jumping.
	 *
	 * @param isJumping {@code true} if the player is jumping, {@code false}
	 *                  otherwise.
	 */
	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	/**
	 * Sets the number of lives the player has.
	 *
	 * @param lives the new number of lives.
	 */
	public void setLives(int lives) {
		this.lives = lives;
	}

	/**
	 * Sets whether the player is currently shooting.
	 *
	 * @param isShooting {@code true} if the player is shooting, {@code false}
	 *                   otherwise.
	 */
	public void setSpecialBubbleActive(boolean specialBubbleActive) {
		this.specialBubbleActive = specialBubbleActive;
	}

	/**
	 * Sets whether the player is affected by CrystalRing power up.
	 *
	 * @param crystalRingActive {@code true} if the power up is active,
	 *                          {@code false} otherwise.
	 */
	public void setCrystalRingActive(boolean crystalRingActive) {
		this.crystalRingActive = crystalRingActive;
	}

	public Timer getInvulnerabilityTimer() {
		return invulnerabilityTimer;
	}

	public void setInvulnerabilityTimer(Timer invincibilityTimer) {
		this.invulnerabilityTimer = invincibilityTimer;
	}

	/**
	 * Sets whether the player is affected by AmethystRing power up.
	 *
	 * @param amethystRingActive {@code true} if the power up is active,
	 *                           {@code false} otherwise.
	 */
	public void setAmethystRingActive(boolean amethystRingActive) {
		this.amethystRingActive = amethystRingActive;
	}

	/**
	 * Sets whether the player is affected by RubyRing power up.
	 *
	 * @param rubyRingActive {@code true} if the power up is active, {@code false}
	 *                       otherwise.
	 */
	public void setRubyRingActive(boolean rubyRingActive) {
		this.rubyRingActive = rubyRingActive;
	}

	/**
	 * Sets player invulnerability.
	 * 
	 * @param isInvulnerable {@code true} if the player is invulnerable,
	 *                       {@code false} otherwise.
	 */
	public void setInvulnerable(boolean isInvulnerable) {
		this.isInvulnerable = isInvulnerable;
		if (invulnerabilityTimer != null)
			invulnerabilityTimer.cancel();
	}

	/**
	 * Sets player stun
	 * 
	 * @param isStunned
	 */
	public void setStunned(boolean isStunned) {
		this.isStunned = isStunned;
	}

	/**
	 * Sets player capacity to kill enemies by shooting at them.
	 * 
	 * @param canShoot {@code true} if the player is able to shoot down enemies,
	 *                 {@code false} otherwise.
	 */
	public void setCanShoot(boolean canShoot) {
		this.canShoot = canShoot;
	}

	/**
	 * Sets shooting bubble direction.
	 * 
	 * @param bubbleDirection indicates in which directions bubbles are going to be
	 *                        shot.
	 */
	public void setBubbleDirection(Direction bubbleDirection) {
		this.bubbleDirection = bubbleDirection;
	}

	/**
	 * Increases the player's firing rate by decreasing the time between shots.
	 *
	 * @param delta the amount of time to decrease between shots.
	 */
	public void increaseFiringRate(long delta) {
		attackSpeed -= delta;
	}

	/**
	 * Decreases the player's firing rate by increasing the time between shots.
	 *
	 * @param delta the amount of time to increase between shots.
	 */
	public void decreaseFiringRate(long delta) {
		attackSpeed += delta;
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
	 * Makes the player jump, setting them into the air with a negative vertical
	 * speed. Also increments the number of jumps in the power-up manager.
	 */
	@Override
	public void jump() {
		inAir = true;
		airSpeed = jumpSpeed;
		Model.getInstance().sendNotification("jump");
		if (amethystRingActive)
			Model.getInstance().getCurrentUser().addPoints(100);
	}

	/**
	 * Handles the player's loss of life, decrementing its lives.
	 */
	@Override
	public void looseLife() {
		Model.getInstance().sendNotification("lifeLost");
		lives--;
		stun(2);
	}

	public void heal() {
		Model.getInstance().sendNotification("heal");
		lives = NUMBER_OF_LIVES;
	}

	/**
	 * Moves the player in the current direction at a specified speed, adjusted by
	 * any extra speed.
	 *
	 * @param speed the base speed at which to move the player.
	 */
	@Override
	public void move(float speed) {
		speed *= extraXSpeed;
		switch (direction) {
		case LEFT -> setxSpeed(-1 * speed);
		case RIGHT -> setxSpeed(speed);
		default -> throw new IllegalArgumentException("Unexpected value: " + direction);
		}
	}

	/**
	 * Handles the player's ability to shoot bubbles. The player can shoot in the
	 * current direction if not obstructed by a wall, and shooting is temporarily
	 * disabled after each shot.
	 */
	@Override
	public void shoot() {
		// Checks if the player can shoot.
		if (canShoot) {
			Model.getInstance().getCurrentLevel().getPowerupManager().getPowerupFactory().increaseNumberOfBubbles();
			if (bubbleDirection == Direction.RIGHT
					&& !HelpMethods.isEntityInsideWall(x + Tile.TILE_SIZE, y, width, height)) {
				Model.getInstance().getCurrentLevel().getBubbleManager().createPlayerBubble(x + Tile.TILE_SIZE, y, 2);
			} else if (bubbleDirection == Direction.LEFT
					&& !HelpMethods.isEntityInsideWall(x - Tile.TILE_SIZE, y, width, height)) {
				Model.getInstance().getCurrentLevel().getBubbleManager().createPlayerBubble(x - Tile.TILE_SIZE, y, -2);
			}
			
			if (rubyRingActive)
				Model.getInstance().getCurrentUser().addPoints(50);

			Model.getInstance().sendNotification("bubble");
			// Disables shooting until the end of the waiting time.
			canShoot = false;

			// Creates a new attack timer.
			new Timer("Shoot Bubble").schedule(new TimerTask() {
				@Override
				public void run() {
					// After the specified attack time, the player can shoot again.
					canShoot = true;
				}
			}, ATTACK_INTERVAL * attackSpeed); // Sets the timer based on the attack speed.
		}
	}

	/**
	 * Stuns the player for time specified in the parameter.
	 * 
	 * @param stunTime duration of the player stun
	 */
	public void stun(int stunTime) {
		if (!isStunned) {
			setxSpeed(0);
			setAirSpeed(0);
			isStunned = true;
			stunTimer = new Timer("Stun Timer");
			stunTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					isStunned = false;
					this.cancel();
					stunTimer = null;
				}
			}, stunTime * 1000);
		}
	}

	/**
	 * Updates the player's state each game tick. This includes checking for
	 * collisions with bubbles, handling jumping, popping bubbles, updating
	 * position, applying gravity, and checking for life loss.
	 */
	@Override
	public void updateEntity() {
		gravity();
		if (!isStunned())
			updateXPos();
		updateYPos();
	}
}
