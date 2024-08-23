package game.model.bubbles;

import game.model.Model;
import game.model.enemies.Enemy;

public class PlayerBubble extends Bubble {

	private static float extraTravelTime = 1;
	private static float extraXSpeed = 1;

	protected float timeHorizontalMoving = 1000.0f;

	/**
	 * tempo prima che la bolla inizi a salire
	 */
	private float travelTime;

	private Enemy enemy;

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param xSpeed
	 * @param airSpeed
	 * @param lifeSpan
	 * @param travelTime
	 */
	private PlayerBubble(float x, float y, float width, float height, float xSpeed, float airSpeed, float lifeSpan,
			float travelTime) {
		super(x, y, width, height, "°");
		this.xSpeed = xSpeed * extraXSpeed;
		this.airSpeed = airSpeed;
		this.lifeSpan = 5000;
		this.travelTime = travelTime * extraTravelTime;
		timeHorizontalMoving = 500;
	}

	/**
	 * Getters and Setters
	 */

	/**
	 * metodo per far decrementare la lifeSpan
	 */
	private void decreaseTravelTime(float k) {
		setTravelTime(travelTime - k);
	}

	public void setTravelTime(float travelTime) {
		this.travelTime = travelTime;
	}

	/**
	 * metodo per far fluttuare la bolla
	 */
	@Override
	protected void rise(float airSpeed) {
//		fa salire la bolla
		setAirSpeed(airSpeed);
		setxSpeed(0);
		decreaseTravelTime(1);
	}

	public Enemy getEnemy() {
		return enemy;
	}

	public void setEnemy(Enemy enemy) {
		if (this.enemy == null)
			this.enemy = enemy;
	}

	public boolean hasEnemy() {
		return getEnemy() != null;
	}

	public static float getExtraTravelTime() {
		return extraTravelTime;
	}

	public static void setExtraTravelTime(float extraTravelTime) {
		PlayerBubble.extraTravelTime = extraTravelTime;
	}

	public static float getExtraXSpeed() {
		return extraXSpeed;
	}

	public static void setExtraXSpeed(float extraXSpeed) {
		PlayerBubble.extraXSpeed = extraXSpeed;
	}

	public float getTimeHorizontalMoving() {
		return timeHorizontalMoving;
	}

	public void setTimeHorizontalMoving(float timeHorizontalMoving) {
		this.timeHorizontalMoving = timeHorizontalMoving;
	}

	protected void decreaseTimeHorizontalMoving(float k) {
		setTimeHorizontalMoving(getTimeHorizontalMoving() - k);
	}

	@Override
	public void pop() {
		if (enemy != null) {
			enemy.setPosition(getX(), getY());
			Model.getInstance().getCurrentLevel().getEnemyManager().addEnemy(enemy);
		}
		Model.getInstance().getCurrentLevel().getBubbleManager().removePlayerBubble(this);
	}

	public void popAndKill() {
		Model.getInstance().getCurrentLevel().getBubbleManager().removePlayerBubble(this);
		Model.getInstance().getCurrentUser().addPoints(100);
		Model.getInstance().getCurrentLevel().getBubbleManager().getPlayerBubbles().forEach(pb -> {
			if (getDistanceFrom(pb) < 10)
				pb.popAndKill();
		});
	}

	@Override
	public void updateEntity() {
		if (lifeSpan <= 0) {
			pop();
		} else {
			decreaseLifeSpan(10.0f);// decrementa la lifespan della bolla (valore da calibrare con la view)
			decreaseTimeHorizontalMoving(10.0f * extraXSpeed / extraTravelTime);// decrementa il tempo prima che la
																				// bolla vada a salire
		}

		if (timeHorizontalMoving <= 0)
			updateYPos();
		else
			updateXPos();

		if (travelTime > 0)
			decreaseTravelTime(1 * extraXSpeed);
		else if (travelTime <= 0) {
			rise(-0.5f);
		}
	}

	public static class Builder {
		private float x, y, width, height;
		private String imagePath;
		private float xSpeed, airSpeed;
		private float lifeSpan = 10000;
		private float travelTime = 100;

		public Builder(float x, float y, float width, float height, String imagePath) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		public Builder xSpeed(float xSpeed) {
			this.xSpeed = xSpeed;
			return this;
		}

		public Builder airSpeed(float airSpeed) {
			this.airSpeed = airSpeed;
			return this;
		}

		public Builder lifeSpan(float lifeSpan) {
			this.lifeSpan = lifeSpan;
			return this;
		}

		public Builder travelTime(float travelTime) {
			this.travelTime = travelTime;
			return this;
		}

		public PlayerBubble build() {
			return new PlayerBubble(x, y, width, height, xSpeed, airSpeed, lifeSpan, travelTime);
		}
	}
}
