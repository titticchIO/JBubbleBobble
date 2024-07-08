package view;

import javax.swing.JFrame;

import controller.PlayerController;

public class GameFrame extends JFrame {
	private GamePanel gamePanel;
	
	
	public GameFrame(PlayerView playerView, PlayerController playerController) {
        this.gamePanel = new GamePanel(playerView);
        add(gamePanel);

        // Attach the PlayerController as a KeyListener
        addKeyListener(playerController);
        setFocusable(true);  // Ensure the frame is focusable
        setFocusTraversalKeysEnabled(false);  // Disable focus traversal keys

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
