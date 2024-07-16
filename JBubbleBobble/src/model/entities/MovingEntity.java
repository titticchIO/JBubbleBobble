package model.entities;

import model.level.LevelLoader;
import view.GameFrame;
import model.HelpMethods;

public abstract class MovingEntity extends Entity {

	public enum Directions {
		RIGHT, LEFT
	}

	/**
	 * Movement speed on x axis: positive up and negative down
	 */
	private float xSpeed;

	private boolean moving;
	private int playerSpeed = 2;

	// jumping and gravity
	private float airSpeed = 0f;
	private float gravity = 0.02f * GameFrame.SCALE;
	private float jumpSpeed = -1.25f * GameFrame.SCALE;
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
	 * Getters
	 */

	/**
	 * @return movement speed on x axis
	 */
	public float getxSpeed() {
		return xSpeed;
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

	public void jump() {
		if (!inAir) {
			inAir = true;
			airSpeed = jumpSpeed;
		}
	}

	public void resetInAir() {
		inAir = false;
		airSpeed = 0.000000000000000000000000f;
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
		if (HelpMethods.canMoveHere(x, y + airSpeed, (int) width, (int) height, LevelLoader.getLevelData())) {
			if (!HelpMethods.isEntityGrounded(this, LevelLoader.getLevelData()))
				inAir = true;
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

	public void updateEntity() {
//		System.out.println(HelpMethods.isEntityGrounded(this, LevelLoader.getLevelData()));
		updateYPos();
		gravity();
		walk();
		setChanged();
		notifyObservers("y");
	}

}