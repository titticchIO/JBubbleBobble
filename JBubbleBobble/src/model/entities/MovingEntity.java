package model.entities;

import model.level.LevelLoader;
import view.GameFrame;
import model.HelpMethods;

public abstract class MovingEntity extends Entity {

	/**
	 * Movement speed on x axis: positive up and negative down
	 */
	private float xSpeed;

	/**
	 * Movement speed on y axis: positive right and negative left
	 */
	private float ySpeed;

	private boolean moving;
	private int playerSpeed = 2;

	// jumping and gravity
	private float airSpeed = 0f;
	private float gravity = 0.04f * GameFrame.SCALE;
	private float jumpSpeed = -2.25f * GameFrame.SCALE;
	private float fallSpeedAfterCollision = 0.5f * GameFrame.SCALE;
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
	 * @param ySpeed
	 */
	public void setySpeed(float ySpeed) {
		this.ySpeed = ySpeed;
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

	/**
	 * @return movement speed on y axis
	 */
	public float getySpeed() {
		return ySpeed;
	}

	/**
	 * Acceleration
	 */

	/**
	 * Increments by n the speed on x axis
	 * 
	 * @param n
	 */
	public void setxAcceleration(float n) {
		setxSpeed(getxSpeed() + n);
	}

	/**
	 * Increments by n the speed on y axis
	 * 
	 * @param n
	 */
	public void setyAcceleration(float n) {
		setySpeed(getySpeed() + n);
	}

	/**
	 * Deceleration
	 */

	/**
	 * Reduces by n the speed on x axis
	 * 
	 * @param n
	 */
	public void setxDeceleration(float n) {
		setxSpeed(getxSpeed() - n);
	}

	/**
	 * Reduces by n the speed on y axis
	 * 
	 * @param n
	 */
	public void setyDeceleration(float n) {
		setySpeed(getySpeed() - n);
	}

	private void updateYPos() {
		if (HelpMethods.canMoveHere(x, y + airSpeed, (int) width, (int) height, LevelLoader.getLevelData())) {
			setY(y + airSpeed);
		} else {
			y = HelpMethods.getEntityPosUnderRoofOrAboveFloor(this, airSpeed);
		}
	}

	private void updateXPos() {
		if (HelpMethods.canMoveHere(x + xSpeed, y, (int) width, (int) height, LevelLoader.getLevelData())) {
			setX(x + xSpeed);
		} else {
			x = HelpMethods.getEntityXPosNextToWall(this, xSpeed);
		}

	}

	public void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	public void jump() {
		if (!inAir) {
			inAir = true;
			airSpeed = jumpSpeed;
		}
	}

	/**
	 * Movement
	 */
	public void walk() {
//		moving = true;
		updateXPos();
		setChanged();
		notifyObservers("walking");

	}

	public void gravity() {
		if (inAir) {
			if (HelpMethods.canMoveHere(x, y + airSpeed, (int) width, (int) height, LevelLoader.getLevelData())) {
				y += airSpeed;
				airSpeed += gravity;
			} else {
				y = HelpMethods.getEntityPosUnderRoofOrAboveFloor(this, airSpeed);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
			}
		}

	}

	public void updateEntity() {
		updateYPos();
		gravity();
		walk();
		setChanged();
		notifyObservers("y");
	}
}