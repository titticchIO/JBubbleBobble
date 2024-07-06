package model;

public class Player extends MovingEntity {
	// bolla attuale
	private Bubble currentBubble;


	/**
	 * Getters and Setters
	 */
	public Bubble getCurrentBubble() {
		return currentBubble;
	}

	public void setCurrentBubble(Bubble currentBubble) {
		this.currentBubble = currentBubble;
	}
}
