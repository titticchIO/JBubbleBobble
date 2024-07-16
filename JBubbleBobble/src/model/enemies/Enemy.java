package model.enemies;

import model.Attack;
import model.entities.MovingEntity;

public abstract class Enemy extends MovingEntity {
	/**
	 * Nome del nemico
	 */
	private String name;

	public Enemy(float x, float y, float width, float height, String name) {
		super(x, y, width, height);
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
