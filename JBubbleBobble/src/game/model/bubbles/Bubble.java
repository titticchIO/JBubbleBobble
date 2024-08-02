package game.model.bubbles;

import game.model.HelpMethods;
import game.model.entities.MovingEntity;

public abstract class Bubble extends MovingEntity {

	/**
	 * tempo prima che la bolla scoppi
	 */
	private float lifeSpan;

	private boolean popped;

	public Bubble(float x, float y, float width, float height) {
		super(x, y, width, height);
		lifeSpan = 10000;
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
		System.out.println("popped");
		popped = true;
		setChanged();
		notifyObservers("pop");
	}

	public boolean isPopped() {
		return popped;
	}

	@Override
	protected void updateYPos() {
		if (HelpMethods.canMoveHere(x, y + airSpeed, (int) width, (int) height)) {
			setY(y + airSpeed);
			setxSpeed(0);
		} else {
			setY(HelpMethods.getEntityPosUnderRoofOrAboveFloor(this, airSpeed));
			setxSpeed(-1);
		}
	}
	
	
	@Override
	public void updateEntity() {
		if (lifeSpan <= 0) {
			pop();
		} else {
			decreaseLifeSpan(10.0f); // decrementa la lifespan della bolla (valore da calibrare con la view)
		}
		updateXPos();
		updateYPos();
		setChanged();
		notifyObservers();
	}

}
