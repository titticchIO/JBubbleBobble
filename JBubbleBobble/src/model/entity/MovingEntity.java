package model.entity;

public abstract class MovingEntity extends Entity {
	// positive up, negative down
	private float xSpeed;
	// positive right, negative left
	private float ySpeed;

	public MovingEntity() {

	}

	/**
	 * Getters and Setters
	 */

	public float getxSpeed() {
		return xSpeed;
	}

	public void setxSpeed(float xSpeed) {
		this.xSpeed = xSpeed;
	}

	public float getySpeed() {
		return ySpeed;
	}

	public void setySpeed(float ySpeed) {
		this.ySpeed = ySpeed;
	}

	/**
	 * Acceleration
	 */
	public void setxAcceleration(float n) {
		setxSpeed(getxSpeed() + n);
	}

	public void setyAcceleration(float n) {
		setySpeed(getySpeed() + n);
	}

	/**
	 * Movement
	 */
	public void move() {
		setX(getX() + getxSpeed()); // moltiplicare xSpeed per una costante?
		setY(getY() + getySpeed()); // moltiplicare ySpeed per una costante?
	}

}