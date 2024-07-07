package model.bubbles;

import model.enemies.Enemy;

public class BubbleWithEnemy extends PlayerBubble {
//	nemico contenuto nella bolla
	private Enemy enemy;

	/**
	 * @param enemy
	 */
	public BubbleWithEnemy(Enemy enemy) {
		this.enemy = enemy;
	}

	/**
	 * Getters and Setters
	 */

	public Enemy getEnemy() {
		return enemy;
	}

	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}

}
