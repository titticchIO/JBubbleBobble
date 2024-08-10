package game.model.enemies;

import game.model.Attack;
import game.model.entities.MovingEntity;

public abstract class Enemy extends MovingEntity {

	protected boolean popped;

	public Enemy(float x, float y, String code) {
		super(x, y, code);
	}

	public Enemy(float x, float y, float width, float height, String code) {
		super(x, y, width, height, code);
	}

	public void pop() {
		System.out.println("popped");
		popped = true;
		setChanged();
		notifyObservers("pop");
	}

	public boolean isPopped() {
		return popped;
	}

}
