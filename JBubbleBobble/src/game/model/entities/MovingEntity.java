package game.model.entities;

import game.model.HelpMethods;
import game.model.Tile;
import game.model.level.Level;

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

	// Maximum falling speed
	public static final float GRAVITY = 0.02f;
	public static final float MAX_FALLING_SPEED = 1.6f;

	// Direction of movement
	protected Direction direction;

	// Speed along the x-axis
	protected float xSpeed;

	// Speed along the y-axis when the entity is in the air
	protected float airSpeed;

	// Speed at which the entity jumps
	protected float jumpSpeed;

	// Speed applied after a collision to prevent sticking
	protected float fallSpeedAfterCollision;

	// Indicates if the entity is in the air
	protected boolean inAir;

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
		jumpSpeed = -2.0f;
		fallSpeedAfterCollision = 0.3f;
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
		jumpSpeed = -2.0f;
		fallSpeedAfterCollision = 0.3f;
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

	/**
	 * Updates the entity's position along the x-axis based on its speed and the
	 * ability to move. Handles collisions with obstacles or walls, adjusting the
	 * position to prevent moving through objects.
	 */
	public void updateXPos() {
		if (y > Level.GAME_HEIGHT)
			return;
		if (HelpMethods.canMoveHere(x + xSpeed, y, (int) width, (int) height) || (HelpMethods.isEntityInsideWall(this)
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
		inAir = !HelpMethods.isEntityGrounded(this);

		// Checks if the entity is outside the level and wraps around.
		if (y > Level.GAME_HEIGHT + 1) {
			setY(-1);
			return;
		}
		if (y < -2) {
			setY(Level.GAME_HEIGHT);
			return;
		}

		// Handles the entity's movement while in air or inside a wall
		if (airSpeed <= 0 || HelpMethods.isEntityInsideWall(this)) {
			// Handles collision with the level roof
			if (y <= Tile.TILE_SIZE && !HelpMethods.canMoveHere(x, y + airSpeed, width, height))
				setAirSpeed(fallSpeedAfterCollision);
			setY(y + airSpeed);
			return;
		}

		//Collisions with tiles
		float delta = 0;
		while (delta < airSpeed && HelpMethods.canMoveHere(x, y + delta, width, height)) {
			delta += 0.001f;
		}

		if (delta < airSpeed) {
			setY(y - 1);
			resetInAir();
		}
		setY(y + delta);
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
	}
}
