package model.entity;

import model.level.LevelLoader;
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
		this.xSpeed += n;
	}

	/**
	 * Increments by n the speed on y axis
	 * 
	 * @param n
	 */
	public void setyAcceleration(float n) {
		this.ySpeed += n;
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
		this.xSpeed -= n;
	}

	/**
	 * Reduces by n the speed on y axis
	 * 
	 * @param n
	 */
	public void setyDeceleration(float n) {
		this.ySpeed -= n;
	}

	/**
	 * Movement
	 */
	public void move() {
		if (HelpMethods.canMoveHere(x + xSpeed, y + ySpeed, (int) width, (int) height, LevelLoader.getLevelData())) {
			this.x += xSpeed;
			this.y += ySpeed;
		}
		setX(getX());
		setY(getY());
	}

	public void updateEntity() {
		move();
	};
}