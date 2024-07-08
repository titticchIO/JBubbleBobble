package model.entity;

import java.util.Observable;

public abstract class Entity extends Observable{
	protected float x;
	protected float y;
	protected Hitbox hitbox;

	public Entity() {
		hitbox=new Hitbox(x, y, 10, 10);
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

	public float[] getPosition() {
		return new float[] { x, y };
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Hitbox getHitbox() {
		return hitbox;
	}

	public void setHitbox(Hitbox hitbox) {
		this.hitbox = hitbox;
	}
}
