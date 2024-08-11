package game.model.bubbles;

import game.model.HelpMethods;
import game.model.Settings;
import game.model.entities.MovingEntity;

public abstract class Bubble extends MovingEntity {

	/**
	 * tempo prima che la bolla scoppi
	 */

	private final String type = "B";

	protected float timeHorizontalMoving = 1000.0f;

	protected float lifeSpan;

	private boolean popped;

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
	private void decreaseLifeSpan(float k) {
		setLifeSpan(getLifeSpan() - k);
	}

	private void decreaseTimeHorizontalMoving(float k) {
		setTimeHorizontalMoving(getTimeHorizontalMoving() - k);
	}

	/**
	 * metodo per far scoppiare la bolla
	 */
	public void pop() {
		popped = true;
		setChanged();
		notifyObservers("pop");
	}

	public boolean isPopped() {
		return popped;
	}

	@Override
	protected void updateYPos() {
		if (y < 0) {
			pop();
		} else
			setY(y + airSpeed);
	}

	public String getType() {
		return type;
	}

//	@Override
//	public void updateXPos() {
//		if (HelpMethods.canMoveHere(x + xSpeed, y, (int) width, (int) height)) {
//			setX(x + xSpeed);
//		} else {
//			setX(HelpMethods.getEntityXPosNextToWall(this));
//		}
//	}

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
		setChanged();
		notifyObservers();
	}

}
