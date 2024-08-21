package game.model.entities;

import game.model.HelpMethods;
import game.model.Model;
import game.model.Settings;
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

	// Movement speed on the x-axis: positive for right, negative for left
	protected float xSpeed;

	protected Direction direction;
	protected Color color;

	// Jumping and gravity variables
	protected float airSpeed;
	protected float gravity;

	protected float jumpSpeed;
	protected float fallSpeedAfterCollision;
	protected float maxFallingSpeed;
	protected boolean inAir;

	/**
	 * MovingEntity constructor. Initializes movement parameters and calls the super
	 * constructor from Entity.
	 * 
	 * @param x            x-coordinate of the entity
	 * @param y            y-coordinate of the entity
	 * @param width        Width of the entity
	 * @param height       Height of the entity
	 * @param positionCode Code to identify the position
	 */
	public MovingEntity(float x, float y, String code) {
		super(x, y, code);
		direction = Direction.STATIC;
		airSpeed = 0;
		gravity = 0.02f;
		jumpSpeed = -2.0f;
		fallSpeedAfterCollision = 0.3f;
		maxFallingSpeed = 2;
		inAir = false;
	}

	public MovingEntity(float x, float y, float width, float height, String code) {
		super(x, y, width, height, code);
		direction = Direction.STATIC;
		airSpeed = 0;
		gravity = 0.02f;
		jumpSpeed = -2.0f;
		fallSpeedAfterCollision = 0.3f;
		maxFallingSpeed = 2;
		inAir = false;
	}

	/**
	 * Sets the speed on the x-axis.
	 * 
	 * @param xSpeed Speed on the x-axis
	 */
	public void setxSpeed(float xSpeed) {
		this.xSpeed = xSpeed;
	}

	/**
	 * Returns the speed on the x-axis.
	 * 
	 * @return Speed on the x-axis
	 */
	public float getxSpeed() {
		return xSpeed;
	}

	/**
	 * Sets the movement direction.
	 * 
	 * @param direction Movement direction
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setJumpSpeed(float jumpSpeed) {
		this.jumpSpeed = jumpSpeed;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Sets the air speed (airSpeed).
	 * 
	 * @param airSpeed Air speed
	 */
	public void setAirSpeed(float airSpeed) {
		this.airSpeed = airSpeed;
	}

	/**
	 * Returns the air speed (airSpeed).
	 * 
	 * @return Air speed
	 */
	public float getAirSpeed() {
		return airSpeed;
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
		// Verifica se l'entità è a terra
		inAir = !HelpMethods.isEntityGrounded(this);

		// Controllo dei limiti verticali dello schermo
		if (y > Level.GAME_HEIGHT + 1) {
			setY(-1); // Riporta l'entità nella parte superiore dello schermo se esce dal basso
			return;
		}

		if (y < -2) {
			setY(Level.GAME_HEIGHT); // Riporta l'entità nella parte inferiore dello schermo se esce dall'alto
			return;
		}

		// Gestione quando l'entità è in aria o dentro un muro
		if (airSpeed <= 0 || HelpMethods.isEntityInsideWall(x, y, width, height)) {
			// Collisione con i tile sul limite superiore del livello
			if (y <= Tile.TILE_SIZE && !HelpMethods.canMoveHere(x, y + airSpeed, width, height))
				setAirSpeed(fallSpeedAfterCollision);
			setY(y + airSpeed);
			return;
		}

		// Gestione del movimento verticale quando l'entità è in aria e può muoversi
		// liberamente
		float delta = 0;

		// Incrementa delta finché può muoversi in aria, fino a raggiungere airSpeed
		while (delta < airSpeed && HelpMethods.canMoveHere(x, y + delta, width, height)) {
			delta += 0.01f;
		}

		// Se delta non ha raggiunto airSpeed, significa che c'è stato un ostacolo
		if (delta < airSpeed) {
			setY(y - 1); // Regola leggermente la posizione verso l'alto per correggere
			resetInAir(); // Reset dello stato in aria
		}

		// Aggiorna la posizione Y con il delta calcolato
		setY(y + delta);
	}

	/**
	 * Makes the entity jump if it's not in the air and not inside a wall.
	 */
	public void jump() {
		if (!inAir && !HelpMethods.isEntityInsideWall(x, y, width, height)) {
			inAir = true;
			airSpeed = jumpSpeed;
		}
	}

	/**
	 * Resets the in-air state (inAir) and air speed (airSpeed).
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
