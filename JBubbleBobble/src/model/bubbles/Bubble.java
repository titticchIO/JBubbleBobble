package model.bubbles;

import model.entity.MovingEntity;

public abstract class Bubble extends MovingEntity {
	/**
	 * tempo prima che la bolla scoppi
	 */
	private float lifeSpan;

	public Bubble() {
		lifeSpan = 10; // da calibrare con il controller
	}

	public Bubble(float lifeSpan) {
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
		setLifeSpan(lifeSpan - k);
	}

	/**
	 * metodo per far scoppiare la bolla
	 */
	public void pop() {
//		setChanged, notify...
	}

	@Override
	public void updateEntity() {
		if (lifeSpan <= 0)
			pop();
		else
			decreaseLifeSpan(10); // decrementa la lifespan della bolla (valore da calibrare con la view)
	}

}
