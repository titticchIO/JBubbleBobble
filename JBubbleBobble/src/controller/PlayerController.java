package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.entities.MovingEntity.Directions;
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
			player.move(Directions.LEFT);
			break;
		case KeyEvent.VK_D:
			player.move(Directions.RIGHT);
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
			player.stop();
			break;
		case KeyEvent.VK_D:
			player.stop();
			break;
		}
	}

}
