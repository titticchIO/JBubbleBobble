package game.model.bubbles;

import game.model.entities.MovingEntity;

public abstract class Bubble extends MovingEntity {

	/**
	 * tempo prima che la bolla scoppi
	 */
	private float lifeSpan;

	public Bubble(float x, float y, float width, float height) {
		super(x, y, width, height);
		lifeSpan = 1000;
	}

	public Bubble(float x, float y, float width, float height, float lifeSpan) {
		super(x, y, width, height);
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
	private void decreaseLifeSpan(float k) {
		setLifeSpan(getLifeSpan() - k);
	}

	/**
	 * metodo per far scoppiare la bolla
	 */
	public void pop() {
//		setChanged, notify...
	}

	@Override
	public void updateEntity() {
//		System.out.println(lifeSpan);
		if (lifeSpan > 0) {
			decreaseLifeSpan(10.0f); // decrementa la lifespan della bolla (valore da calibrare con la view)
		}
		else
			pop();

		setChanged();
		notifyObservers();
	}

}
