package game.model.bubbles;

public class PlayerBubble extends Bubble {
	
	/**
	 * tempo prima che la bolla inizi a salire
	 */
	private float travelTime;
	/**
	 * velocità iniziale della bolla
	 */
	private float travelSpeed;

	/**
	 * @param travelDistance
	 * @param travelSpeed
	 */

	public PlayerBubble(float x, float y, float width, float height) {
		super(x, y, width, height);
		//valori di default
		travelTime = 5;
		travelSpeed = 10;
	}
	
	public PlayerBubble(float x, float y, float width, float height, float lifeSpan, float travelTime, float travelSpeed) {
		super(x, y, width, height, lifeSpan);
		// valori personalizzabili
		this.travelTime = travelTime;
		this.travelSpeed = travelSpeed;
		
	}

	/**
	 * Getters and Setters
	 */

	public float getTravelDistance() {
		return travelTime;
	}

	public void setTravelDistance(float travelTime) {
		this.travelTime = travelTime;
	}

	public float getTravelSpeed() {
		return travelSpeed;
	}

	public void setTravelSpeed(float lifeSpan) {
		this.travelSpeed = lifeSpan;
	}

	/**
	 * metodo per far decrementare la lifeSpan
	 */
	private void decreaseTravelTime(float k) {
		setLifeSpan(travelTime - k);
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
//		setySpeed(5); // da calibrare con la view
		setxSpeed(0); // da calibrare con la view
	}



	@Override
	public void updateEntity() {
		super.updateEntity();
		if (travelTime >= 0)
			decreaseTravelTime(1); // decrementa il tempo prima che la bolla inizi a salire (valore da calibrare
									// con la view)
		else {
			rise();
		}
	}

}
