package game.model.enemies;

import game.model.Attack;
import game.model.entities.MovingEntity;

public abstract class Enemy extends MovingEntity {
	/**
	 * Nome del nemico
	 */
	private String name;

	public Enemy(float x, float y, float width, float height, String imageCode, String name) {
		super(x, y, width, height, imageCode);
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
