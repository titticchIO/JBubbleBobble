package model.entity;

public abstract class Entity {
	protected float x;
	protected float y;
	protected Hitbox hitbox;

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
