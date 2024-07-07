package model;


import model.bubbles.PlayerBubble;
import model.entity.MovingEntity;


public class Player extends MovingEntity {
	// bolla attuale
	private PlayerBubble currentBubble;
	// singleton
	private Player instance;
	
	public Player getInstance() {
		if (instance==null) instance=new Player();
		return instance;
	}
	
	private Player() {
		currentBubble=new PlayerBubble();
	}

	/**
	 * Getters and Setters
	 */
	public PlayerBubble getCurrentBubble() {
		return currentBubble;
	}

	public void setCurrentBubble(PlayerBubble currentBubble) {
		this.currentBubble = currentBubble;
	}
}
