package game.model.entities;

import java.util.Timer;
import java.util.TimerTask;

import game.model.HelpMethods;
import game.model.level.Level;
import game.model.tiles.Tile;

/**
 * The {@code MovingEntity} class represents an entity in the game that can
 * move, jump, and interact with the environment. It extends the {@link Entity}
 * class and adds additional properties and methods for movement, collision
 * handling, gravity effects, and more.
 */
public abstract class MovingEntity extends Entity {

	/**
	 * Enum representing possible movement directions for the entity.
	 */
	public enum Direction {
		LEFT, RIGHT, STATIC
	}

	private Timer stunTimer;

	// Direction of movement
	protected Direction direction;

	// Speed along the x-axis
	protected float xSpeed;

	// Speed along the y-axis when the entity is in the air
	protected float airSpeed;

	// Speed at which the entity jumps
	protected float jumpSpeed;

	// Gravity force applied to the entity
	protected float gravity;

	// Speed applied after a collision to prevent sticking
	protected float fallSpeedAfterCollision;

	// Maximum falling speed
	protected float maxFallingSpeed;

	// Indicates if the entity is in the air
	protected boolean inAir;

	// Indicates if the entity is stunned
	protected boolean isStunned;

	/**
	 * Constructs a {@code MovingEntity} with specified position and unique code.
	 * Default values are assigned for gravity, jump speed, fall speed after
	 * collision, and maximum falling speed.
	 * 
	 * @param x    The x-coordinate of the entity.
	 * @param y    The y-coordinate of the entity.
	 * @param code The unique code representing the entity.
	 */
	public MovingEntity(float x, float y, char code) {
		super(x, y, code);
		direction = Direction.STATIC;
		gravity = 0.02f;
		jumpSpeed = -2.0f;
		fallSpeedAfterCollision = 0.3f;
		maxFallingSpeed = 2;
	}

	/**
	 * Constructs a {@code MovingEntity} with specified position, dimensions, and
	 * unique code. Default values are assigned for gravity, jump speed, fall speed
	 * after collision, and maximum falling speed.
	 * 
	 * @param x      The x-coordinate of the entity.
	 * @param y      The y-coordinate of the entity.
	 * @param width  The width of the entity.
	 * @param height The height of the entity.
	 * @param code   The unique code representing the entity.
	 */
	public MovingEntity(float x, float y, float width, float height, char code) {
		super(x, y, width, height, code);
		direction = Direction.STATIC;
		gravity = 0.02f;
		jumpSpeed = -2.0f;
		fallSpeedAfterCollision = 0.3f;
		maxFallingSpeed = 2;
	}

	/**
	 * Gets the current speed of the entity along the x-axis.
	 * 
	 * @return The x-axis speed of the entity.
	 */
	public float getxSpeed() {
		return xSpeed;
	}

	/**
	 * Gets the current speed of the entity while it is in the air.
	 * 
	 * @return The air speed of the entity.
	 */
	public float getAirSpeed() {
		return airSpeed;
	}

	/**
	 * Gets the current movement direction of the entity.
	 * 
	 * @return The direction of the entity.
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isStunned() {
		return isStunned;
	}

	/**
	 * Sets the speed of the entity along the x-axis.
	 * 
	 * @param xSpeed The new x-axis speed of the entity.
	 */
	public void setxSpeed(float xSpeed) {
		this.xSpeed = xSpeed;
	}

	/**
	 * Sets the speed of the entity while it is in the air.
	 * 
	 * @param airSpeed The new air speed of the entity.
	 */
	public void setAirSpeed(float airSpeed) {
		this.airSpeed = airSpeed;
	}

	/**
	 * Sets the speed at which the entity jumps.
	 * 
	 * @param jumpSpeed The new jump speed of the entity.
	 */
	public void setJumpSpeed(float jumpSpeed) {
		this.jumpSpeed = jumpSpeed;
	}

	/**
	 * Sets the movement direction of the entity.
	 * 
	 * @param direction The new movement direction of the entity.
	 */
	public void setDirection(Direction direction) {
			this.direction = direction;
	}

	public void stun(int stunTime) {
		if (stunTimer == null) {
			System.out.println("stunned");
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
	 * Updates the entity's position along the x-axis based on its speed and the
	 * ability to move. Handles collisions with obstacles or walls, adjusting the
	 * position to prevent moving through objects.
	 */
	public void updateXPos() {
		if (HelpMethods.canMoveHere(x + xSpeed, y, (int) width, (int) height)
				|| (HelpMethods.isEntityInsideWall(x, y, width, height)
						&& (x + xSpeed >= Tile.TILE_SIZE && x + xSpeed + width <= Level.GAME_WIDTH - Tile.TILE_SIZE))) {
			setX(x + xSpeed);
		} else {
			float delta = 0;
			if (xSpeed > 0) {
				while (delta < xSpeed && HelpMethods.canMoveHere(x + delta, y, width, height))
					delta += 0.01;
				if (delta < xSpeed)
					setX(x - 1);
			} else {
				while (delta > xSpeed && HelpMethods.canMoveHere(x + delta, y, width, height))
					delta -= 0.01;
				if (delta > xSpeed)
					setX(x + 1);
			}
			setX(x + delta);
		}
	}

	/**
	 * Updates the entity's position along the y-axis based on air speed, gravity,
	 * and collisions. Handles situations where the entity is in the air, hits the
	 * roof, or goes out of bounds on the screen.
	 */
	protected void updateYPos() {
		// Checks if the entity is grounded
		inAir = !HelpMethods.isEntityGrounded(this);

		// Checks if the entity is outside the screen bounds
		if (y > Level.GAME_HEIGHT + 1) {
			setY(-1); // Moves entity to the top of the screen if it falls below the floor
			return;
		}

		if (y < -2) {
			setY(Level.GAME_HEIGHT); // Moves entity to the bottom of the screen if it jumps over the roof
			return;
		}

		// Handles the entity's movement while in air or inside a wall
		if (airSpeed <= 0 || HelpMethods.isEntityInsideWall(x, y, width, height)) {
			// Handles collision with the level roof
			if (y <= Tile.TILE_SIZE && !HelpMethods.canMoveHere(x, y + airSpeed, width, height))
				setAirSpeed(fallSpeedAfterCollision);
			setY(y + airSpeed);
			return;
		}

		// Handles free movement in air
		float delta = 0;

		// Increment delta while in air until it reaches airSpeed
		while (delta < airSpeed && HelpMethods.canMoveHere(x, y + delta, width, height)) {
			delta += 0.01f;
		}

		// If delta does not reach airSpeed, an obstacle was hit
		if (delta < airSpeed) {
			setY(y - 1); // Move position slightly up to prevent overlap
			resetInAir(); // Reset air-related states
		}

		// Updates y with calculated delta
		setY(y + delta);
	}

	/**
	 * Makes the entity jump, setting it into the air with a negative vertical
	 * speed. This method ensures the entity can only jump if it is grounded and not
	 * inside a wall.
	 */
	public void jump() {
		if (!inAir && !HelpMethods.isEntityInsideWall(x, y, width, height)) {
			inAir = true;
			airSpeed = jumpSpeed;
		}
	}

	/**
	 * Resets the in-air state and air speed of the entity. This is typically called
	 * after the entity lands on the ground.
	 */
	public void resetInAir() {
		inAir = false;
		airSpeed = 0.0f;
	}

	/**
	 * Applies gravity to the entity, increasing its air speed up to a maximum value
	 * if the entity is not grounded. This method simulates the effect of gravity on
	 * the entity when it is in the air.
	 */
	public void gravity() {
		if (!HelpMethods.isEntityGrounded(this) && airSpeed < maxFallingSpeed) {
			inAir = true;
			airSpeed += gravity;
		}
	}

	/**
	 * Stops the entity's movement by setting its direction to {@code STATIC} and
	 * setting its xSpeed to 0. This effectively halts the entity's horizontal
	 * motion.
	 */
	public void stop() {
		setDirection(Direction.STATIC);
		setxSpeed(0);
	}

	/**
	 * Moves the entity based on the current direction and a specified speed.
	 * 
	 * @param speed The speed at which to move the entity.
	 * @throws IllegalArgumentException If the direction is unexpected.
	 */
	public void move(float speed) {
		switch (direction) {
		case LEFT -> setxSpeed(-1 * speed);
		case RIGHT -> setxSpeed(speed);
		default -> throw new IllegalArgumentException("Unexpected value: " + direction);
		}
	}

	/**
	 * Updates the entity's state, including movement, gravity, and any other
	 * relevant changes. This method should be called every game tick to ensure the
	 * entity behaves as expected.
	 */
	public void updateEntity() {
		updateXPos();
		updateYPos();
		gravity();
	}
}
