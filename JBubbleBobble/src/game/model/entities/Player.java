package game.model.entities;

import game.model.bubbles.PlayerBubble;
import game.model.tiles.Tile;
import game.model.HelpMethods;
import game.model.Model;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class Player extends MovingEntity {

	public enum State {
		WALK, JUMP, SHOOT
	}

	public static final int NUMBER_OF_LIVES = 100;

	public static final long INVULNERABILITY_INTERVAL = 5000;

	public static final long ATTACK_INTERVAL = 100;

	private static Player instance;

	/**
	 * @return player object
	 */
	public static Player getInstance() {
		return instance;
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return player object
	 */
	public static Player getInstance(float x, float y, float width, float height) {
		if (instance == null)
			instance = new Player(x, y, width, height);
		return instance;
	}

	private Direction bubbleDirection;
	private State state;
	private int lives;
	private long attackSpeed;
	private float extraXSpeed;
	private float previousX;
	private boolean isJumping;
	private boolean isInvulnerable;
	private boolean isShooting;
	private boolean canShoot;

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
	 * @return number of lives
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * @return if the player is jumping
	 */
	public boolean isJumping() {
		return isJumping;
	}

	/**
	 * @return if the player is shooting
	 */
	public boolean isShooting() {
		return isShooting;
	}

	/**
	 * Sets bubble direction according to player direction
	 */
	@Override
	public void setDirection(Direction direction) {
		super.setDirection(direction);
		if (direction == Direction.RIGHT || direction == Direction.LEFT)
			bubbleDirection = direction;
	}

	@Override
	public void setX(float x) {
		// Updates distanceTravelled according to the difference between x a previousX
		Model.getInstance().getCurrentLevel().getPowerupManager().increaseDistanceTraveled(Math.abs(x - previousX));

		// Updates previousX with new x value
		previousX = x;
		this.x = x;
	}

	/**
	 * @param extraXSpeed
	 */
	public void setExtraXSpeed(float extraXSpeed) {
		this.extraXSpeed = extraXSpeed;
	}

	/**
	 * @param isJumping
	 */
	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	/**
	 * @param lives
	 */
	public void setLives(int lives) {
		this.lives = lives;
	}

	/**
	 * @param isShooting
	 */
	public void setShooting(boolean isShooting) {
		this.isShooting = isShooting;
	}


	/**
	 * Increases firing rate
	 * 
	 * @param delta
	 */
	public void increaseFiringRate(long delta) {
		attackSpeed -= delta;
	}

	/**
	 * Decreases firing rate
	 * 
	 * @param delta
	 */
	public void decreaseFiringRate(long delta) {
		attackSpeed += delta;
	}

	/**
	 * Handles player jump
	 */
	@Override
	public void jump() {
		inAir = true;
		airSpeed = jumpSpeed;
		Model.getInstance().getCurrentLevel().getPowerupManager().increaseNumberOfJumps();
	}

	/**
	 * Handles player life loss
	 */
	
	public void looseLife() {
		// Checks if player is invulnerable if it is not it can loose a life
		if (!isInvulnerable
				&& Entity.checkCollision(this, Model.getInstance().getCurrentLevel().getEnemyManager().getHazards())
						.isPresent()) {
			lives--;
			// Activates invulnerability
			isInvulnerable = true;

			// Sets new invulnerability timer
			Timer invulnerabilityTimer = new Timer();
			invulnerabilityTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					// When the timer ends player is vulnerable again
					isInvulnerable = false;
					invulnerabilityTimer.cancel(); // Stops the timer once completed
				}
			}, INVULNERABILITY_INTERVAL); // Sets the timer for the invulnerability interval
		}
	}

	/**
	 * Handles player movement
	 */
	@Override
	public void move(float speed) {
		speed *= extraXSpeed;
		System.out.println("velocità:" + speed);
		switch (direction) {
		case LEFT -> setxSpeed(-1 * speed);
		case RIGHT -> setxSpeed(speed);
		default -> throw new IllegalArgumentException("Unexpected value: " + direction);
		}
	}

	/**
	 * Handles bubble shooting
	 */
	public void shootBubble() {
		// Checks if the player can shoot
		if (canShoot) {
			Model.getInstance().getCurrentLevel().getPowerupManager().increaseNumberOfBubbles();
			if (bubbleDirection == Direction.RIGHT
					&& !HelpMethods.isEntityInsideWall(x + Tile.TILE_SIZE, y, width, height)) {
				Model.getInstance().getCurrentLevel().getBubbleManager().createPlayerBubble(x + Tile.TILE_SIZE, y, 2);
			} else if (bubbleDirection == Direction.LEFT
					&& !HelpMethods.isEntityInsideWall(x - Tile.TILE_SIZE, y, width, height)) {
				Model.getInstance().getCurrentLevel().getBubbleManager().createPlayerBubble(x - Tile.TILE_SIZE, y, -2);
			}

			// Disables shooting till the end of waiting time
			canShoot = false;

//			Creates new attack timer
			Timer attackTimer = new Timer();
			attackTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					// After milliseconds specified in attack time player can shoot again
					canShoot = true;
					attackTimer.cancel(); // Stops timer once completed
				}
			}, ATTACK_INTERVAL * attackSpeed); // Sets timer according to attack speed
		}
	}

	/**
	 * Updates the player
	 */
	@Override
	public void updateEntity() {
		Optional<PlayerBubble> bounceBobble = Entity.checkBottomCollision(this,
				Model.getInstance().getCurrentLevel().getBubbleManager().getPlayerBubbles());
		if (isJumping() && ((bounceBobble.isPresent() && bounceBobble.get().getEnemy() == null)
				|| HelpMethods.isEntityGrounded(this))) {
			jump();
			if (bounceBobble.isPresent() && bounceBobble.get().getEnemy() == null && isJumping)
				Model.getInstance().getCurrentLevel().getPowerupManager().increaseNumberOfJumpsOnBubbles();
		}

		Optional<PlayerBubble> popBobble = Entity.checkTopCollision(this,
				Model.getInstance().getCurrentLevel().getBubbleManager().getPlayerBubbles());
		if (popBobble.isPresent()) {
			popBobble.get().popAndKill();
			Model.getInstance().getCurrentLevel().getPowerupManager().increaseNumberOfBubblesPopped();
		}
		updateXPos();
		updateYPos();
		gravity();
		looseLife();
	}

}
