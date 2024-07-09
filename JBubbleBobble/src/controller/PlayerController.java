package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Player;


public class PlayerController implements KeyListener {
	private Player player;

	/**
	 * @param playerView
	 */
	public PlayerController(Player player) {
		this.player = player;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()){
		case KeyEvent.VK_W:
			player.setySpeed(-30);
			break;
		case KeyEvent.VK_A:
			player.setxSpeed(-30);
			break;
		case KeyEvent.VK_S:
			player.setySpeed(30);
			break;
		case KeyEvent.VK_D:
			player.setxSpeed(30);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()){
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
