package game.model.entities;

import game.model.HelpMethods;
import game.model.level.Level;
import game.model.tiles.Tile;

public abstract class MovingEntity extends Entity {

	// Possible movement directions
	public enum Direction {
		LEFT, RIGHT, STATIC
	}

	public enum Color {
		NORMAL, RED, BLUE
	}

	protected Direction direction;
	protected Color color;
	protected float xSpeed;
	protected float airSpeed;
	protected float jumpSpeed;
	protected float gravity;
	protected float fallSpeedAfterCollision;
	protected float maxFallingSpeed;
	protected boolean inAir;

	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 * @param code
	 */
	public MovingEntity(float x, float y, String code) {
		super(x, y, code);
		direction = Direction.STATIC;
		gravity = 0.02f;
		jumpSpeed = -2.0f;
		fallSpeedAfterCollision = 0.3f;
		maxFallingSpeed = 2;
	}

	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param code
	 */
	public MovingEntity(float x, float y, float width, float height, String code) {
		super(x, y, width, height, code);
		direction = Direction.STATIC;
		gravity = 0.02f;
		jumpSpeed = -2.0f;
		fallSpeedAfterCollision = 0.3f;
		maxFallingSpeed = 2;
	}

	/**
	 * @return xSpeed
	 */
	public float getxSpeed() {
		return xSpeed;
	}

	/**
	 * @return airSpeed
	 */
	public float getAirSpeed() {
		return airSpeed;
	}

	/**
	 * @return direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * @return color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param xSpeed
	 */
	public void setxSpeed(float xSpeed) {
		this.xSpeed = xSpeed;
	}

	/**
	 * @param airSpeed
	 */
	public void setAirSpeed(float airSpeed) {
		this.airSpeed = airSpeed;
	}

	/**
	 * @param jumpSpeed
	 */
	public void setJumpSpeed(float jumpSpeed) {
		this.jumpSpeed = jumpSpeed;
	}

	/**
	 * @param direction
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Updates the x-axis position based on speed and the ability to move. Also
	 * handles collisions.
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
	 * Updates the y-axis position based on air speed and the ability to move. Also
	 * handles gravity and collisions.
	 */
	protected void updateYPos() {
		// Checks if entity is grounded
		inAir = !HelpMethods.isEntityGrounded(this);

		// Checks if entity is inside screen
		if (y > Level.GAME_HEIGHT + 1) {
			setY(-1); // Moves entity on the top of the screen if it falls under floor
			return;
		}

		if (y < -2) {
			setY(Level.GAME_HEIGHT); // Moves entity on the bottom of the screen if it jumps over the roof
			return;
		}

		// Handles entity's movement while in air or inside wall
		if (airSpeed <= 0 || HelpMethods.isEntityInsideWall(x, y, width, height)) {
			// Handles collision with level roof
			if (y <= Tile.TILE_SIZE && !HelpMethods.canMoveHere(x, y + airSpeed, width, height))
				setAirSpeed(fallSpeedAfterCollision);
			setY(y + airSpeed);
			return;
		}

		// Handles free entity's in air movement
		float delta = 0;

		// Increments delta while in air till it reaches airSpeed
		while (delta < airSpeed && HelpMethods.canMoveHere(x, y + delta, width, height)) {
			delta += 0.01f;
		}

		// If delta is not equal to airSpeed it has hit an obstacle
		if (delta < airSpeed) {
			setY(y - 1); // Moves position slightly up
			resetInAir(); // Resets in air
		}

		// Updates y with calculated delta
		setY(y + delta);
	}

	/**
	 * Handles entity's jump
	 */
	public void jump() {
		if (!inAir && !HelpMethods.isEntityInsideWall(x, y, width, height)) {
			inAir = true;
			airSpeed = jumpSpeed;
		}
	}

	/**
	 * Resets inAir and airSpeed
	 */
	public void resetInAir() {
		inAir = false;
		airSpeed = 0.0f;
	}

	/**
	 * Handles the entity's gravity, increasing air speed up to a maximum if the
	 * entity is not grounded.
	 */
	public void gravity() {
		if (!HelpMethods.isEntityGrounded(this) && airSpeed < maxFallingSpeed) {
			inAir = true;
			airSpeed += gravity;
		}
	}

	/**
	 * Stops the entity's movement by setting the direction to STILL and xSpeed to
	 * 0.
	 */
	public void stop() {
		setDirection(Direction.STATIC);
		setxSpeed(0);
	}

	/**
	 * Moves the entity based on the current direction and specified speed.
	 * 
	 * @param speed Movement speed
	 */
	public void move(float speed) {
		switch (direction) {
		case LEFT -> setxSpeed(-1 * speed);
		case RIGHT -> setxSpeed(speed);
		default -> throw new IllegalArgumentException("Unexpected value: " + direction);
		}
	}

	/**
	 * Updates the entity's state, including movement, gravity, and image. Notifies
	 * observers of changes.
	 */
	public void updateEntity() {
		updateXPos();
		updateYPos();
		gravity();
	}
}
