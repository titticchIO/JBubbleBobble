package game.model.enemies;

import game.model.Attack;
import game.model.entities.MovingEntity;

public abstract class Enemy extends MovingEntity {
	/**
	 * Nome del nemico
	 */
	private String name;
	
	protected boolean popped;

	public Enemy(float x, float y, float width, float height, String imageCode) {
		super(x, y, width, height, imageCode);
		this.name = name;
	}

	public String getName() {
		return name;
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
