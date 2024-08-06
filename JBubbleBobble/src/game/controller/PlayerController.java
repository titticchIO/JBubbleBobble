package game.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.controller.gamestates.GameState;

public class PlayerController implements KeyListener {
	private Game game;

	/**
	 * @param game
	 */
	public PlayerController(Game game) {
		this.game = game;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (GameState.state) {
		case MENU:
			game.getMenu().keyPressed(e);
			break;
		case PLAYING:
			game.getPlaying().keyPressed(e);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (GameState.state) {
		case MENU:
			game.getMenu().keyReleased(e);
			break;
		case PLAYING:
			game.getPlaying().keyReleased(e);
			break;
		}
	}

}
