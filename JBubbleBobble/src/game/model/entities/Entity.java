package game.model.entities;

import java.util.Arrays;
import java.util.Objects;
import java.util.Observable;

/**
 * 
 */
public abstract class Entity extends Observable {
	/**
	 * Coordinates of the entity's top left corner
	 */
	
	protected String positionCode;
	
	protected float x, y;
	/**
	 * Width and height of the entity relative to coordinates
	 */
	protected float width, height;

	/**
	 * Public entity constructor
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Entity(float x, float y, float width, float height, String positionCode) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.positionCode = positionCode;
		
	}
	
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	
	public String getPositionCode() {
		return positionCode;
	}

	/**
	 * Notifies to the observers the entity's initial position
	 */
	public void notifyPosition() {
		setChanged();
		notifyObservers();
	}

	/**
	 * Setters
	 */

	/**
	 * @param x
	 */
	public void setX(float x) {
		this.x = x;
		notifyPosition();
	}

	/**
	 * @param y
	 */
	public void setY(float y) {
		this.y = y;
		notifyPosition();
	}

	/**
	 * @param width
	 */
	public void setWidth(float width) {
		this.width = width;
		notifyPosition();
	}

	/**
	 * @param height
	 */
	public void setHeight(float height) {
		this.height = height;
		notifyPosition();
	}

	/**
	 * Getters
	 */

	/**
	 * @return x coordinate
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return y coordinate
	 */
	public float getY() {
		return y;
	}

	/**
	 * @return entity's width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @return entity's height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @return array with entity's coordinates
	 */
	public float[] getPosition() {
		return new float[] { x, y };
	}

	public float[][] getTopPoints() {
		return new float[][] { { x, y }, { x + width, y } };
	}

	public float[][] getBottomPoints() {
		return new float[][] { { x, y + height }, { x + width, y + height } };
	}

	/**
	 * @return four entity's edges
	 */
	public float[][] getPoints() {
		return new float[][] { { x, y }, { x + width, y }, { x, y + height }, { x + width, y + height } };
	}

	/**
	 * Method that checks any top collision with another entity
	 * 
	 * @param entity
	 * @return boolean
	 */
	public boolean topHit(Entity entity) {
		float[][] p1 = getPoints();
		float[][] p2 = entity.getPoints();
		if (p2[3][0] >= p1[0][0] && p2[3][0] <= p1[1][0] && p2[3][1] >= p1[0][1] && p2[3][1] <= p1[3][1]) // checks p1
			return true;
		if (p2[2][0] >= p1[0][0] && p2[2][0] <= p1[1][0] && p2[2][1] >= p1[0][1] && p2[2][1] <= p1[3][1]) // checks p1
			return true;
		return false;
	}

	/**
	 * Method that checks any bottom collision with another entity
	 * 
	 * @param entity
	 * @return boolean
	 */
	public boolean bottomHit(Entity entity) {
		float[][] p1 = getPoints();
		float[][] p2 = entity.getPoints();
		if (p2[1][0] >= p1[0][0] && p2[1][0] <= p1[1][0] && p2[1][1] >= p1[0][1] && p2[1][1] <= p1[3][1]) // check p1
			return true;
		if (p2[0][0] >= p1[0][0] && p2[0][0] <= p1[1][0] && p2[0][1] >= p1[0][1] && p2[0][1] <= p1[3][1]) // check p1
			return true;
		return false;
	}

	/**
	 * Method that checks any collision with another entity
	 * 
	 * @param entity
	 * @return boolean
	 */
	public boolean hit(Entity entity) {
		return topHit(entity) || bottomHit(entity);
	}

	@Override
	public int hashCode() {
		int k = 13;
		k = k * 17 + (int) x;
		k = k * 17 + (int) y;
		k = k * 17 + (int) width;
		k = k * 17 + (int) width;
		return k;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		return height == other.height && width == other.width && x == other.x && y == other.y;
	}
	

}
