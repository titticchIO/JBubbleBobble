package model.bubbles;

import model.enemies.Enemy;

public class EnemyBubble extends Bubble {
//	nemico contenuto nella bolla
	private Enemy enemy;

	/**
	 * @param enemy
	 */
	public EnemyBubble(Enemy enemy) {
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
