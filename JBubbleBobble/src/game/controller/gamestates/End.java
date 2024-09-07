package game.controller.gamestates;

import java.awt.event.KeyEvent;

import game.controller.Controller;

/**
 * The {@code End} class represents the end state of the game. It handles
 * game-specific logic when the game ends, such as updating the state and
 * responding to user input.
 * <p>
 * This class extends {@link State} and implements the {@link Statemethods}
 * interface.
 * </p>
 */
public class End extends State implements Statemethods {

	/**
	 * Constructs an {@code End} state with the specified game controller.
	 * 
	 * @param game the controller managing the game
	 */
	public End(Controller game) {
		super(game);
	}

	@Override
	public void update() {
	}

	/**
	 * Handles a key press event. When a key is pressed, the game is reset by
	 * calling the {@code resetGame} method from the {@link Controller} class.
	 * 
	 * @param e the key event
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		controller.resetGame(); // Use the resetGame method from the Game class
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
