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
	
	public void setInitialPosition(float x, float y) {
		this.x = x;
		this.y = y;
		setChanged();
		notifyObservers();
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		setChanged();
		notifyObservers();
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		setChanged();
		notifyObservers();
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
		setChanged();
		notifyObservers();
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
		setChanged();
		notifyObservers();
	}

	public float[] getPosition() {
		return new float[] { x, y };
	}

	
	public float[][] getPoints() {
		return new float[][] { { x, y }, { x + width, y }, { x, y + height }, { x + width, y + height } };
	}
	
	
	
	
	/**
	 * method to check the collision with another hitbox from the top
	 * @param hitbox
	 * @return			boolean
	 */
	public boolean topHit(Entity entity) {
		float[][] p1 = getPoints();
		float[][] p2 = entity.getPoints();
		if (p2[3][0] >= p1[0][0] && p2[3][0] <= p1[1][0] && p2[3][1] >= p1[0][1] && p2[3][1] <= p1[3][1])	//check p1 top left
			return true;
		if (p2[2][0] >= p1[0][0] && p2[2][0] <= p1[1][0] && p2[2][1] >= p1[0][1] && p2[2][1] <= p1[3][1])	//check p1 top right
			return true;
		return false;
	}
	
	
	/**
	 * method to check the collision with another hitbox from the bottom
	 * @param hitbox
	 * @return			boolean
	 */
	public boolean bottomHit(Entity entity) {
		float[][] p1 = getPoints();
		float[][] p2 = entity.getPoints();
		if (p2[1][0] >= p1[0][0] && p2[1][0] <= p1[1][0] && p2[1][1] >= p1[0][1] && p2[1][1] <= p1[3][1])	//check p1 bottom left
			return true;
		if (p2[0][0] >= p1[0][0] && p2[0][0] <= p1[1][0] && p2[0][1] >= p1[0][1] && p2[0][1] <= p1[3][1])	//check p1 bottom right
			return true;
		return false;
	}
	
	/**
	 * method to check the collision with another hitbox
	 * @param hitbox
	 * @return			boolean
	 */
	public boolean hit(Entity entity) {
		return topHit(entity) || bottomHit(entity);
	}

}

