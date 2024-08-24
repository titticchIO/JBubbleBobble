package game.model.entities;

import game.model.bubbles.PlayerBubble;
import game.model.bubbles.ThunderBubble;
import game.model.bubbles.WaterBubble;
import game.model.level.Level;
import game.model.bubbles.Bubble;
import game.model.bubbles.FireBubble;
import game.model.tiles.Tile;
import game.view.AnimationLoader;
import game.controller.AudioManager;
import game.model.HelpMethods;
import game.model.Model;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The {@code Player} class represents the player character in the game. It
 * extends the {@link MovingEntity} class and provides additional functionality
 * such as jumping, shooting bubbles, and managing player lives. This class
 * implements the singleton pattern to ensure only one player instance exists in
 * the game.
 */
public class Player extends MovingEntity {

	/**
	 * Enum representing the various states the player can be in.
	 */
	public enum State {
		WALK, JUMP, SHOOT
	}

	public static final int NUMBER_OF_LIVES = 200; // The total number of lives the player starts with.
	public static final long INVULNERABILITY_INTERVAL = 5000; // The duration of invulnerability after losing a life.
	public static final long ATTACK_INTERVAL = 100; // The minimum time interval between bubble shots (in milliseconds).

	private static Player instance; // Singleton instance of the Player class.

	private Direction bubbleDirection; // Direction in which the player will shoot bubbles.
	private State state; // Current state of the player.
	private int lives; // Current number of lives the player has.
	private long attackSpeed; // The speed at which the player can shoot bubbles.
	private float extraXSpeed; // Extra speed applied to the player's movement.
	private float previousX; // Previous x-coordinate used for distance tracking.
	private boolean isJumping; // Indicates whether the player is currently jumping.
	private boolean isInvulnerable; // Indicates whether the player is invulnerable.
	private boolean isShooting; // Indicates whether the player is currently shooting.
	private boolean canShoot; // Indicates whether the player can shoot a bubble.
	private boolean crystalRingActive;

	public boolean isCrystalRingActive() {
		return crystalRingActive;
	}

	public void setCrystalRingActive(boolean crystalRingActive) {
		this.crystalRingActive = crystalRingActive;
	}

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

	/**
	 * Private constructor to enforce singleton pattern.
	 *
	 * @param x      the initial x-coordinate of the player.
	 * @param y      the initial y-coordinate of the player.
	 * @param width  the width of the player.
	 * @param height the height of the player.
	 */
	private Player(float x, float y, float width, float height) {
		super(x, y, width, height, "P");
		state = State.WALK;
		bubbleDirection = Direction.RIGHT;
		lives = NUMBER_OF_LIVES;
		extraXSpeed = 1;
		canShoot = true;
		attackSpeed = 2;
		previousX = x;
	}

	/**
	 * Returns the current number of lives the player has.
	 *
	 * @return the player's current number of lives.
	 */
	public int getLives() {
		return lives;
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
	 * Returns whether the player is currently shooting.
	 *
	 * @return {@code true} if the player is shooting, {@code false} otherwise.
	 */
	public boolean isShooting() {
		return isShooting;
	}

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
			bubbleDirection = direction;
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
		Model.getInstance().getCurrentLevel().getPowerupManager().increaseDistanceTraveled(Math.abs(x - previousX));
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
	public void setShooting(boolean isShooting) {
		this.isShooting = isShooting;
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
	 * Makes the player jump, setting them into the air with a negative vertical
	 * speed. Also increments the number of jumps in the power-up manager.
	 */
	@Override
	public void jump() {
		inAir = true;
		airSpeed = jumpSpeed;
		Model.getInstance().getCurrentLevel().getPowerupManager().increaseNumberOfJumps();
	}

	/**
	 * Handles the player's loss of life. If the player is not invulnerable, this
	 * method decreases the player's lives by one and activates a period of
	 * invulnerability.
	 */
	public void looseLife() {
		// Checks if the player is invulnerable; if not, the player can lose a life.
		if (!isInvulnerable
				&& Entity.checkCollision(this, Model.getInstance().getCurrentLevel().getEnemyManager().getHazards())
						.isPresent()) {
			lives--;
			// Activates invulnerability.
			isInvulnerable = true;

			// Sets a new invulnerability timer.
			new Timer("Invulnerability").schedule(new TimerTask() {
				@Override
				public void run() {
					// When the timer ends, the player becomes vulnerable again.
					isInvulnerable = false;
				}
			}, INVULNERABILITY_INTERVAL); // Sets the timer for the invulnerability interval.
		}
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
	public void shootBubble() {
		// Checks if the player can shoot.
		if (canShoot) {
			Model.getInstance().getCurrentLevel().getPowerupManager().increaseNumberOfBubbles();
			if (bubbleDirection == Direction.RIGHT
					&& !HelpMethods.isEntityInsideWall(x + Tile.TILE_SIZE, y, width, height)) {
				Model.getInstance().getCurrentLevel().getBubbleManager().createPlayerBubble(x + Tile.TILE_SIZE, y, 2);
			} else if (bubbleDirection == Direction.LEFT
					&& !HelpMethods.isEntityInsideWall(x - Tile.TILE_SIZE, y, width, height)) {
				Model.getInstance().getCurrentLevel().getBubbleManager().createPlayerBubble(x - Tile.TILE_SIZE, y, -2);
			}

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

	public void checkJump() {

		if (isJumping()) {
			Optional<PlayerBubble> bounceBubble = Entity.checkBottomCollision(this,
					Model.getInstance().getCurrentLevel().getBubbleManager().getPlayerBubbles());
			if (HelpMethods.isEntityGrounded(this))
				jump();
			if (bounceBubble.isPresent() && bounceBubble.get().getEnemy() == null) {
				jump();
				Model.getInstance().getCurrentLevel().getPowerupManager().increaseNumberOfJumpsOnBubbles();
			}
		}
	}

	private void checkBubbleCollisions() {
		Optional<PlayerBubble> playerPopBubble = Entity.checkTopCollision(this,
				Model.getInstance().getCurrentLevel().getBubbleManager().getPlayerBubbles());
		if (playerPopBubble.isPresent()) {
			playerPopBubble.get().popAndKill();
			Model.getInstance().getCurrentLevel().getPowerupManager().increaseNumberOfBubblesPopped();
		}
		Optional<Bubble> specialPopBubble = Entity.checkCollision(this,
				Model.getInstance().getCurrentLevel().getBubbleManager().getBubbles());
		if (specialPopBubble.isPresent()) {
			specialPopBubble.get().pop();
			if (specialPopBubble.get() instanceof FireBubble)
				Model.getInstance().getCurrentLevel().getPowerupManager().increaseNumberOfFireBubblesPopped();
			else if (specialPopBubble.get() instanceof WaterBubble)
				Model.getInstance().getCurrentLevel().getPowerupManager().increaseNumberOfWaterBubblesPopped();
			else if (specialPopBubble.get() instanceof ThunderBubble)
				Model.getInstance().getCurrentLevel().getPowerupManager().increaseNumberOfThunderBubblesPopped();
		}
	}

	/**
	 * Updates the player's state each game tick. This includes checking for
	 * collisions with bubbles, handling jumping, popping bubbles, updating
	 * position, applying gravity, and checking for life loss.
	 */
	@Override
	public void updateEntity() {
		checkJump();

		checkBubbleCollisions();
		updateXPos();
		updateYPos();
		gravity();
		looseLife();
	}
}
