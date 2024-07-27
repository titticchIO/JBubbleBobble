package game.model.entities;

import game.model.level.LevelLoader;
import game.model.HelpMethods;
import static game.model.Settings.SCALE;

public abstract class MovingEntity extends Entity {

	public enum Directions {
		RIGHT, LEFT
	}

	/**
	 * Movement speed on x axis: positive up and negative down
	 */
	protected float xSpeed;

	private boolean moving;
	private int playerSpeed = 2;

	// jumping and gravity
	protected float airSpeed = 0f;
	private float gravity = 0.02f * SCALE;
	private float jumpSpeed = -1.25f * SCALE;
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
		if (airSpeed < 0) {
			setY(y + airSpeed);
		} else {
			if (HelpMethods.canMoveHere(x, y + airSpeed, (int) width, (int) height, LevelLoader.getLevelData())) {
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

	private void updateXPos() {
		if (HelpMethods.canMoveHere(x + xSpeed, y, (int) width, (int) height, LevelLoader.getLevelData())) {
			setX(x + xSpeed);
		} else {
			setX(HelpMethods.getEntityXPosNextToWall(this, xSpeed));
		}
	}

	public void jump() {
		if (!inAir && !HelpMethods.isEntityInsideWall(x, y, width, height, LevelLoader.getLevelData())) {
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

	/**
	 * Movement
	 */
	public void walk() {
//		moving = true;
		updateXPos();

	}

	public void gravity() {
		if (!HelpMethods.isEntityGrounded(this, LevelLoader.getLevelData())) {
			inAir = true;
			airSpeed += gravity;
		}
	}

	public void updateEntity() {
		updateYPos();
		gravity();
		walk();
		setChanged();
		notifyObservers();
	}

}