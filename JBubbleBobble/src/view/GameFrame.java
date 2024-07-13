package view;

import javax.swing.JFrame;

import controller.PlayerController;
import model.level.Level;

public class GameFrame extends JFrame {
	private GamePanel gamePanel;
	public final static int TILE_DEFAULT_SIZE = 16;
	public final static float SCALE = 1.0f;
	public final static int HORIZONTAL_TILES = 30;
	public final static int VERTICAL_TILES = 32;
	public final static int TILE_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE);
	public final static int FRAME_WIDTH = TILE_SIZE * HORIZONTAL_TILES;
	public final static int FRAME_HEIGHT = TILE_SIZE * VERTICAL_TILES;

	public GameFrame(MovingEntityView playerView, PlayerController playerController, LevelView level) {
		this.gamePanel = new GamePanel(playerView, level);
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
