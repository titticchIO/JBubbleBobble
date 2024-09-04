package game.controller.gamestates;

import game.controller.Controller;

public class State {
	
	
	
	protected Controller game;

	public State(Controller game) {
		this.game = game;
	}

	public Controller getGame() {
		return game;
	}

}
