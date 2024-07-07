package model.bubbles;

import model.MovingEntity;

public class Bubble extends MovingEntity {
	/**
	 * distanza percorsa dalla bolla prima di fermarsi
	 */
	private float travelDistance;
	/**
	 * velocità iniziale della bolla
	 */
	private float travelSpeed;
	/**
	 * tempo prima che la bolla scoppi
	 */
	private float lifeSpan;
	
	/**
	 * @param travelDistance
	 * @param travelSpeed
	 */

	public Bubble() {
//		valori di default
		travelDistance = 5; // da calibrare con il model
		travelSpeed = 10; // da calibrare con il model
		lifeSpan = 10; // da calibrare con il controller

	}

	public Bubble(float travelDistance, float travelSpeed, float lifeSpan) {
//		valori personalizzabili
		this.travelDistance = travelDistance;
		this.travelSpeed = travelSpeed;
		this.lifeSpan = lifeSpan;
	}

	/**
	 * Getters and Setters
	 */

	public float getTravelDistance() {
		return travelDistance;
	}

	public void setTravelDistance(float travelDistance) {
		this.travelDistance = travelDistance;
	}

	public float getTravelSpeed() {
		return travelSpeed;
	}

	public void setTravelSpeed(float lifeSpan) {
		this.travelSpeed = lifeSpan;
	}

	public float getLifeSpan() {
		return lifeSpan;
	}

	public void setLifeSpan(float lifeSpan) {
		this.lifeSpan = lifeSpan;
	}

	/**
	 * metodo per far decrementare la lifeSpan
	 */
	private void decreaseLifeSpan(float k) {
		setLifeSpan(lifeSpan - k);
	}

	/**
	 * metodo per far scoppiare la bolla
	 */
	public void pop() {
//		setChanged, notify...
	}

	/**
	 * metodo per far fluttuare la bolla
	 */
	private void rise() {
//		fa salire la bolla
		setySpeed(5); // da calibrare con il model

//		fa muovere la bolla a destra e sinistra, da calibrare con il model!!
		setxSpeed(10);
		while (true) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			setxSpeed(-getxSpeed());
		}

	}
}
