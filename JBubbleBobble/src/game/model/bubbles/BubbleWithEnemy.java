package game.model.bubbles;

import game.model.enemies.Enemy;

public class BubbleWithEnemy extends PlayerBubble {

//	nemico contenuto nella bolla
	private Enemy enemy;

	/**
	 * @param enemy
	 */
	
	public BubbleWithEnemy(float x, float y, float width, float height, String positionCode, Enemy enemy) {
		super(x, y, width, height, positionCode);
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
