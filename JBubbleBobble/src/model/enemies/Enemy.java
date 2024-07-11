package model.enemies;

import model.Attack;
import model.entity.MovingEntity;

public abstract class Enemy extends MovingEntity implements Attack {
	
	public Enemy(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	/**
	 * Nome del nemico
	 */
	private String name;
	
	
	
	
}
