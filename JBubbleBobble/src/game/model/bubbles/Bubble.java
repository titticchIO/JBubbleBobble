	package game.model.bubbles;

import game.model.HelpMethods;
import game.model.Model;
import game.model.enemies.Enemy;
import game.model.entities.MovingEntity;
import game.model.level.Level;
import game.model.tiles.Tile;

public abstract class Bubble extends MovingEntity {

	/**
	 * tempo prima che la bolla scoppi
	 */

	private final String type = "B";

	protected float timeHorizontalMoving = 1000.0f;

	protected float lifeSpan;

	protected boolean popped;

	public Bubble(float x, float y, float width, float height) {
		super(x, y, width, height, "B");
		lifeSpan = 10000;
		timeHorizontalMoving = 500;
	}

	public Bubble(float x, float y, float width, float height, float lifeSpan) {
		super(x, y, width, height, "B");
		this.lifeSpan = lifeSpan;
	}

	/**
	 * Getters and Setters
	 */
	public float getLifeSpan() {
		return lifeSpan;
	}

	public float getTimeHorizontalMoving() {
		return timeHorizontalMoving;
	}

	public void setLifeSpan(float lifeSpan) {
		this.lifeSpan = lifeSpan;
	}

	public void setTimeHorizontalMoving(float timeHorizontalMoving) {
		this.timeHorizontalMoving = timeHorizontalMoving;
	}

	/**
	 * metodo per far decrementare la lifeSpan
	 */
	protected void decreaseLifeSpan(float k) {
		setLifeSpan(getLifeSpan() - k);
	}

	protected void decreaseTimeHorizontalMoving(float k) {
		setTimeHorizontalMoving(getTimeHorizontalMoving() - k);
	}

	public boolean isEnemyHit(Enemy enemy) {
		return enemy.hit(this);
	}

	/**
	 * metodo per far scoppiare la bolla
	 */
	public void pop() {
		popped = true;
	}

	public boolean isPopped() {
		return popped;
	}

	@Override
	protected void updateYPos() {
		if (y < 0) {
			setY(Level.GAME_HEIGHT);

		} else if (y > Tile.TILE_SIZE || HelpMethods.canMoveHere(x, y + airSpeed, width, height))
			setY(y + airSpeed);
		else {
			setY(y + 15);
			setAirSpeed(-0.2f);
		}
	}

	public String getType() {
		return type;
	}

	@Override
	public void updateEntity() {
		if (lifeSpan <= 0) {
			pop();
		} else {
			decreaseLifeSpan(10.0f);// decrementa la lifespan della bolla (valore da calibrare con la view)
			decreaseTimeHorizontalMoving(10.0f);// decrementa il tempo prima che vada a salire la bolla
		}
		if (timeHorizontalMoving <= 0)
			updateYPos();
		else
			updateXPos();
	}

}
