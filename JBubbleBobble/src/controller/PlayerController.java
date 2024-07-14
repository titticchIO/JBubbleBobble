package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.entities.Player;
import view.GameFrame;

public class PlayerController implements KeyListener {
	private Player player;

	/**
	 * @param playerView
	 */
	public PlayerController() {
		player = Player.getInstance();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			player.jump();
			break;
		case KeyEvent.VK_SPACE:
			player.jump();
			break;
		case KeyEvent.VK_A:
			player.setxSpeed(-2 * GameFrame.SCALE);
			break;
		case KeyEvent.VK_D:
			player.setxSpeed(2 * GameFrame.SCALE);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			player.setySpeed(0);
			break;
		case KeyEvent.VK_A:
			player.setxSpeed(0);
			break;
		case KeyEvent.VK_S:
			player.setySpeed(0);
			break;
		case KeyEvent.VK_D:
			player.setxSpeed(0);
			break;
		}
	}

}
