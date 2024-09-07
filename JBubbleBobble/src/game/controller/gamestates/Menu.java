package game.controller.gamestates;

import java.awt.event.KeyEvent;

import game.controller.Controller;

/**
 * Represents {@code Menu} the menu state in the game.
 * <p>
 * This class handles the menu state logic, including responding to key events
 * and updating the state. It implements the {@link Statemethods} interface to
 * define the specific methods needed for menu functionality.
 * </p>
 * 
 * @see State
 * @see Statemethods
 */
public class Menu extends State implements Statemethods {

	/**
	 * Constructs a new {@code Menu} instance.
	 * 
	 * @param game the controller that manages the game's state
	 */
	public Menu(Controller game) {
		super(game);

	}

	@Override
	public void update() {
	}

	/**
	 * Handles key press events.
	 * <p>
	 * If the 'P' key is pressed, the game loop is started. This is typically used
	 * to transition from the menu to the game.
	 * </p>
	 * 
	 * @param e the key event that occurred
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_P) {
			controller.startGameLoop();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	/**
	 * Starts the game loop.
	 * <p>
	 * This method is used to transition from the menu state to the game state by
	 * starting the game loop.
	 * </p>
	 */
	public void startGame() {
		controller.startGameLoop();
	}

}
