package game.model.bubbles;

public class PlayerBubble extends Bubble {

	/**
	 * tempo prima che la bolla inizi a salire
	 */
	private float travelTime;

	/**
	 * @param travelDistance
	 * @param travelSpeed
	 */

	public PlayerBubble(float x, float y, float width, float height) {
		super(x, y, width, height);
		// valori di default
		travelTime = 100;
		xSpeed = 1;
	}

	public PlayerBubble(float x, float y, float width, float height, float lifeSpan, float travelTime, float xSpeed) {
		super(x, y, width, height, lifeSpan);
		// valori personalizzabili
		this.travelTime = travelTime;
		this.xSpeed = xSpeed;

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
	private void rise() {
//		fa salire la bolla
		setAirSpeed(-1); // da calibrare con la view
		setxSpeed(0);
		decreaseTravelTime(1);
		
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (travelTime > 0)
			decreaseTravelTime(1);
		else if (travelTime == 0) {
			rise();
		}

		setChanged();
		notifyObservers();

	}

}
