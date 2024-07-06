package model;

public abstract class Entity {
	private float x;
	private float y;
	//positive up, negative down
	private float xSpeed;
	//positive right, negative left
	private float ySpeed;

	// Builder pattern?
	
	public Entity() {

	}

	/**
	 * Getters and Setters
	 */
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

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
		setxSpeed(getxSpeed()+n);
	}
	public void setyAcceleration(float n) {
		setySpeed(getySpeed()+n);
	}
	
	/**
	 * Movement
	 */
	public void move() {
		setX(getX()+getxSpeed()); //moltiplicare xSpeed per una costante?
		setY(getY()+getySpeed()); //moltiplicare ySpeed per una costante?
	}
}
