package model.powerups;

import model.Entity;

public abstract class PowerUp extends Entity{	
	
	/*
	 * punti dati dal powerUp
	 */
	private int point;
	
	/*
	 * durata dell'effetto del powerup
	 */
	private float duration;
	
	/*
	 * indica se il powerup è temporaneo
	 */	
	private boolean temporariness;
	
	
	public PowerUp(int point, float duration, boolean temporariness) {
		this.point = point;
		this.duration = duration;
		this.temporariness = temporariness;
	}
	
	
	/*
	 * getters e setters
	 */
	public int getPoint() {
		return point;
	}
	
	public void setPoint(int point) {
		this.point = point;
	}
	
	public float getDuration() {
		return duration;
	}
	
	public void setDuration(float duration) {
		this.duration = duration;
	}
	
	public boolean getTemporariness() {
		return temporariness;
	}
	
	public void setTemporariness(boolean temporariness) {
		this.temporariness = temporariness;
	}
	
	/*
	 * quando il powerup viene attivato
	 */
	public void activate() {
		
	}
	
}
