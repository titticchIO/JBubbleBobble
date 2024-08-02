package game.model.entities;

import game.model.HelpMethods;
import utils.LevelLoader;

import static game.model.Settings.SCALE;

public abstract class MovingEntity extends Entity {

	public enum Directions {
		RIGHT, LEFT
	}

	/**
	 * Movement speed on x axis: positive up and negative down
	 */
	protected float xSpeed;

//	private boolean moving;
//	private int playerSpeed = 2;

	// jumping and gravity
	protected float airSpeed = 0f;
	private float gravity = 0.02f * SCALE;
	private float jumpSpeed = -2.0f * SCALE;
	private float fallSpeedAfterCollision = 0.3f * SCALE;
	private boolean inAir = false;

	/**
	 * MovingEntity constructor calls Entity's super constructor
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public MovingEntity(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	/**
	 * Setters
	 */

	/**
	 * @param xSpeed
	 */
	public void setxSpeed(float xSpeed) {
		this.xSpeed = xSpeed;
	}

	/**
	 * Getters
	 */

	/**
	 * @return movement speed on x axis
	 */
	public float getxSpeed() {
		return xSpeed;
	}

	protected void updateYPos() {
		if (airSpeed <= 0 || HelpMethods.isEntityInsideWall(x, y, width, height)) {
			setY(y + airSpeed);
		} else {
			if (HelpMethods.canMoveHere(x+xSpeed, y + airSpeed, width, height)) {
				setY(y + airSpeed);
			} else {
				setY(HelpMethods.getEntityPosUnderRoofOrAboveFloor(this, airSpeed));
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
			}
		}

	}

	public void updateXPos() {
		if (HelpMethods.canMoveHere(x + xSpeed, y+airSpeed, (int) width, (int) height)
				|| HelpMethods.isEntityInsideWall(x, y, width, height)) {
			setX(x + xSpeed);
		} else {
			setX(HelpMethods.getEntityXPosNextToWall(this));
		}
	}

	public void jump() {
		if (!inAir && !HelpMethods.isEntityInsideWall(x, y, width, height)) {
			inAir = true;
			airSpeed = jumpSpeed;
		}
	}

	public void resetInAir() {
		inAir = false;
		airSpeed = 0.0f;
	}

	public float getAirSpeed() {
		return airSpeed;
	}

	public void setAirSpeed(float airSpeed) {
		this.airSpeed = airSpeed;
	}

	public void stop() {
		setxSpeed(0);
	}

	public void move(Directions dir) {
		if (dir == Directions.LEFT)
			setxSpeed((int) -1);
		else
			setxSpeed((int) 1);
	}

	public void gravity() {
		if (!HelpMethods.isEntityGrounded(this)) {
			inAir = true;
			airSpeed += gravity;
		}
	}

	public void updateEntity() {
		updateXPos();
		updateYPos();
		gravity();
		setChanged();
		notifyObservers();
	}

}