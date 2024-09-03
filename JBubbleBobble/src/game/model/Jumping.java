package game.model;

public interface Jumping {

	/**
	 * Makes the entity jump, setting it into the air with a negative vertical
	 * speed. This method ensures the entity can only jump if it is grounded and not
	 * inside a wall.
	 */
	void jump();

}
