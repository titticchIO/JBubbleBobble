package game.view;

import javax.swing.JFrame;

import game.controller.PlayerController;
import game.model.entities.Player;
import game.model.level.Level;

public class GameFrame extends JFrame {
	private GamePanel gamePanel;
	

	public GameFrame() {
		this.gamePanel = GamePanel.getInstance();
		add(gamePanel);
		// Attach the PlayerController as a KeyListener
		addKeyListener(new PlayerController());
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
