package view;

import javax.swing.JFrame;

import controller.PlayerController;

public class GameFrame extends JFrame {
	private GamePanel gamePanel;
	public final static int TILE_DEFAULT_SIZE = 16;
	public final static float SCALE = 5.0f;
	public final static int HORIZONTAL_TILES = 26;
	public final static int VERTICAL_TILES = 14;
	public final static int TILES_SIZE = TILE_DEFAULT_SIZE * (int) SCALE;
	public final static int FRAME_WIDTH=TILES_SIZE*HORIZONTAL_TILES;
	public final static int FRAME_HEIGHT=TILES_SIZE*VERTICAL_TILES;

	public GameFrame(PlayerView playerView, PlayerController playerController) {
		this.gamePanel = new GamePanel(playerView);
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

	@Override
	public void repaint() {
		super.repaint();
		gamePanel.repaint();
	}
}
