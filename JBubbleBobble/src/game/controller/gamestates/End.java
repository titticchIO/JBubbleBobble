package game.controller.gamestates;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import game.controller.Controller;

public class End extends State implements Statemethods {

	public End(Controller game) {
		super(game);
	}

	@Override
	public void update() {
		// Win-specific update logic (if any)
	}

	@Override
	public void keyPressed(KeyEvent e) {
		controller.resetGame(); // Use the resetGame method from the Game class
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Handle key release if necessary
	}
}
