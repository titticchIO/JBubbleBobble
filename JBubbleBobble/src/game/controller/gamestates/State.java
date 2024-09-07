package game.controller.gamestates;

import game.controller.Controller;

/**
 * The {@code State} class, extended by the various game state classes.
 */
public class State {

	protected Controller controller;

	/**
	 * Constructor for State
	 * 
	 * @param controller
	 */
	public State(Controller controller) {
		this.controller = controller;
	}

	/**
	 * Gets the controller of the game
	 * 
	 * @return the controller of the game
	 */
	public Controller getController() {
		return controller;
	}

}
