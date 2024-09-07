package game.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.controller.gamestates.GameState;

/**
 * The {@code InputManager} redirects the key inputs to the proper {@code State}
 * according to the current {@code GameState}
 */
public class InputManager implements KeyListener {

	private Controller controller;

	/**
	 * Constructor fot the InputManager
	 * 
	 * @param controller
	 */
	public InputManager(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * Sends the KeyEvent to the correct {@code State}
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch (GameState.state) {
		case MENU -> controller.getMenu().keyPressed(e);
		case PLAYING -> controller.getPlaying().keyPressed(e);
		case WIN, LOSS -> controller.getEnd().keyPressed(e);
		case PAUSE -> controller.getPause().keyPressed(e);
		}
	}

	/**
	 * Sends the KeyEvent to the correct {@code State}
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		switch (GameState.state) {
		case MENU -> controller.getMenu().keyReleased(e);
		case PLAYING -> controller.getPlaying().keyReleased(e);
		case LOSS, WIN -> controller.getEnd().keyReleased(e);
		case PAUSE -> controller.getPause().keyReleased(e);
		}
	}

}
