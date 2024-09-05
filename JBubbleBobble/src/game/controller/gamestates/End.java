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
	public void mouseClicked(MouseEvent e) {
		// Handle mouse click if necessary
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Handle mouse press if necessary
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Handle mouse release if necessary
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// Handle mouse move if necessary
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
