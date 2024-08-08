package game.model.enemies;

import game.model.Attack;
import game.model.entities.MovingEntity;

public abstract class Enemy extends MovingEntity {
	/**
	 * Nome del nemico
	 */
	private String code;
	
	protected boolean popped;

	public Enemy(float x, float y, float width, float height,String code) {
		super(x, y, width, height);
		this.code = code;
	}

	public String getCode() {
		return code;
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
