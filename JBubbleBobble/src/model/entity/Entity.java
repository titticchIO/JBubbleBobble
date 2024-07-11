package model.entity;

import java.util.Observable;

public class Entity extends Observable {
	protected float x, y;
	protected float width, height;

	public Entity(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Getters and Setters
	 */

	
	 public void notifyPosition() { setChanged(); notifyObservers(); }
	 

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		notifyPosition();
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		notifyPosition();
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
		notifyPosition();
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
		notifyPosition();
	}

	public float[] getPosition() {
		return new float[] { x, y };
	}

	public float[][] getPoints() {
		return new float[][] { { x, y }, { x + width, y }, { x, y + height }, { x + width, y + height } };
	}

	/**
	 * method to check the collision with another entity from the top
	 * 
	 * @param entity
	 * @return boolean
	 */
	public boolean topHit(Entity entity) {
		float[][] p1 = getPoints();
		float[][] p2 = entity.getPoints();
		if (p2[3][0] >= p1[0][0] && p2[3][0] <= p1[1][0] && p2[3][1] >= p1[0][1] && p2[3][1] <= p1[3][1]) // check p1
																											// top left
			return true;
		if (p2[2][0] >= p1[0][0] && p2[2][0] <= p1[1][0] && p2[2][1] >= p1[0][1] && p2[2][1] <= p1[3][1]) // check p1
																											// top right
			return true;
		return false;
	}

	/**
	 * method to check the collision with another entity from the bottom
	 * 
	 * @param entity
	 * @return boolean
	 */
	public boolean bottomHit(Entity entity) {
		float[][] p1 = getPoints();
		float[][] p2 = entity.getPoints();
		if (p2[1][0] >= p1[0][0] && p2[1][0] <= p1[1][0] && p2[1][1] >= p1[0][1] && p2[1][1] <= p1[3][1]) // check p1
																											// bottom
																											// left
			return true;
		if (p2[0][0] >= p1[0][0] && p2[0][0] <= p1[1][0] && p2[0][1] >= p1[0][1] && p2[0][1] <= p1[3][1]) // check p1
																											// bottom
																											// right
			return true;
		return false;
	}

	/**
	 * method to check the collision with another entity
	 * 
	 * @param entity
	 * @return boolean
	 */
	public boolean hit(Entity entity) {
		return topHit(entity) || bottomHit(entity);
	}

}
