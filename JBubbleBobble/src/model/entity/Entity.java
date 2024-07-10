package model.entity;

import java.util.Observable;

public abstract class Entity extends Observable {
	protected float x, y;
	protected float width, height;
	protected Hitbox hitbox;

	public Entity() {
		hitbox = new Hitbox(x, y, 10, 10);
	}

	/**
	 * Getters and Setters
	 */
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

	public Hitbox getHitbox() {
		return hitbox;
	}

	public void setHitbox(Hitbox hitbox) {
		this.hitbox = hitbox;
	}
}
