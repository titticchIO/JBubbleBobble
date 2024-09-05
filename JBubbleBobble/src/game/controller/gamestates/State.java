package game.controller.gamestates;

import game.controller.Controller;

public class State {
	
	
	
	protected Controller controller;

	public State(Controller controller) {
		this.controller = controller;
	}

	public Controller getGame() {
		return controller;
	}

}
