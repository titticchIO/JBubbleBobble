package model;

import model.bubbles.Bubble;
import model.entity.MovingEntity;

public class Player extends MovingEntity {
	// bolla attuale
	private Bubble currentBubble;
	// singleton
	private Player instance;
	
	public Player getInstance() {
		if (instance==null) instance=new Player();
		return instance;
	}
	
	private Player() {
		currentBubble=new Bubble();
	}

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
