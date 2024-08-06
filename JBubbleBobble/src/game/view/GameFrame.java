package game.view;

import java.awt.event.ActionListener;

import javax.swing.JFrame;

import game.controller.Game;
import game.controller.PlayerController;

public class GameFrame extends JFrame {
	private GamePanel gamePanel;

	public GameFrame(Game game, PlayerController playerController, ActionListener actionListener) {
		this.gamePanel = new GamePanel(actionListener);
		add(gamePanel);
		// Attach the PlayerController as a KeyListener
		addKeyListener(playerController);
		setFocusable(true); // Ensure the frame is focusable
		setFocusTraversalKeysEnabled(false); // Disable focus traversal keys

		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	@Override
	public void repaint() {
		super.repaint();
		gamePanel.repaint();
	}
}
