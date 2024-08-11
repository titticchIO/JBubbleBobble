package game.model.enemies;

import java.util.Random;

import game.model.Attack;
import game.model.entities.MovingEntity;

public abstract class Enemy extends MovingEntity {

	private Random random;
	protected boolean popped;

	public Enemy(float x, float y, String code) {
		super(x, y, code);
		random = new Random();
	}

	public Enemy(float x, float y, float width, float height, String code) {
		super(x, y, width, height, code);
		random = new Random();
	}

	public boolean randomBoolean(int chances) {
		return random.nextInt(0, chances) == 0;
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
