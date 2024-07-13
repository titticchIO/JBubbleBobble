package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Player;


public class PlayerController implements KeyListener {
	private Player player;

	/**
	 * @param playerView
	 */
	public PlayerController() {
		player=Player.getInstance();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()){
		case KeyEvent.VK_W:
			player.setUp(true);
			//player.setySpeed(-2);
			break;
		case KeyEvent.VK_A:
			player.setLeft(true);
			//player.setxSpeed(-2);
			break;
		case KeyEvent.VK_S:
			player.setDown(true);
			//player.setySpeed(2);
			break;
		case KeyEvent.VK_D:
			player.setRight(true);
			//player.setxSpeed(2);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()){
		case KeyEvent.VK_W:
			player.setUp(false);
			//player.setySpeed(0);
			break;
		case KeyEvent.VK_A:
			player.setLeft(false);
			//player.setxSpeed(0);
			break;
		case KeyEvent.VK_S:
			player.setDown(false);
			//player.setySpeed(0);
			break;
		case KeyEvent.VK_D:
			player.setRight(false);
			//player.setxSpeed(0);
			break;
		case KeyEvent.VK_SPACE:
			//player.setJump(false);
		}
	}

}
