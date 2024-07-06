package model;

public class Player extends Entity {
	// bolla attuale
	private Bubble currentBubble;
	// powerUp in uso
	private PowerUp currentPowerUp;

	/**
	 * Getters and Setters
	 */
	public Bubble getCurrentBubble() {
		return currentBubble;
	}

	public void setCurrentBubble(Bubble currentBubble) {
		this.currentBubble = currentBubble;
	}

	public PowerUp getCurrentPowerUp() {
		return currentPowerUp;
	}

	public void setCurrentPowerUp(PowerUp currentPowerUp) {
		this.currentPowerUp = currentPowerUp;
	}

}
