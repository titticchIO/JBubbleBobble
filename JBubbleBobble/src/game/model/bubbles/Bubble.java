package game.model.bubbles;

import game.model.HelpMethods;
import game.model.enemies.Enemy;
import game.model.entities.MovingEntity;
import game.model.level.Level;
import game.model.tiles.Tile;

public abstract class Bubble extends MovingEntity {

	protected float lifeSpan;

	public Bubble(float x, float y, float width, float height, char code) {
		super(x, y, width, height, code);
		lifeSpan = 10000;

	}

	public Bubble(float x, float y, float width, float height, float lifeSpan, char code) {
		super(x, y, width, height, code);
		this.lifeSpan = lifeSpan;
	}

	/**
	 * Getters and Setters
	 */
	public float getLifeSpan() {
		return lifeSpan;
	}

	public void setLifeSpan(float lifeSpan) {
		this.lifeSpan = lifeSpan;
	}

	/**
	 * metodo per far decrementare la lifeSpan
	 */
	protected void decreaseLifeSpan(float k) {
		setLifeSpan(getLifeSpan() - k);
	}

	public boolean isEnemyHit(Enemy enemy) {
		return enemy.hit(this);
	}

	/**
	 * metodo per far scoppiare la bolla
	 */
	abstract public void pop();

	protected void rise(float airSpeed) {
		setAirSpeed(airSpeed);
		setxSpeed(0);
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

	@Override
	public void updateEntity() {
		if (lifeSpan <= 0) {
			pop();
		} else {
			decreaseLifeSpan(10.0f);// decrementa la lifespan della bolla (valore da calibrare con la view)
		}
		updateYPos();
		updateXPos();
	}

}
