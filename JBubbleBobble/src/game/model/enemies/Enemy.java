package game.model.enemies;

import java.util.Random;

import game.model.entities.MovingEntity;

public abstract class Enemy extends MovingEntity {

	protected boolean popped;

	public Enemy(float x, float y, String code) {
		super(x, y, code);
	}

	public Enemy(float x, float y, float width, float height, String code) {
		super(x, y, width, height, code);
	}

	public boolean isPopped() {
		return popped;
	}
	
	public boolean randomBoolean(int chances) {
		return new Random().nextInt(0, chances) == 0;
	}

	public void pop() {
		System.out.println("popped");
		popped = true;
	}


}
