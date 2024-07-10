package model.entity;

import model.Player;
import model.level.LevelLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

import model.HelpMethods;

public abstract class MovingEntity extends Entity {
	
	
	public MovingEntity(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	// positive up, negative down
	private float xSpeed;
	// positive right, negative left
	private float ySpeed;


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
		if(HelpMethods.canMoveHere(x + xSpeed, y + ySpeed, (int)width, (int)height, LevelLoader.getLevelData())) {
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