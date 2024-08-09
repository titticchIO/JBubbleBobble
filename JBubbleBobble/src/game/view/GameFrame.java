package game.view;

import java.awt.event.ActionListener;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.controller.Game;

import game.controller.PlayerController;

public class GameFrame extends JFrame {
	
	public enum Screen {
		MENU,
		GAME
	}
	
	
	private GamePanel gamePanel;
	private MenuPanel menuPanel;

	public GameFrame(Game game, PlayerController playerController, ActionListener actionListener) {
		setLayout(new CardLayout());
		
		
		gamePanel = new GamePanel();
		menuPanel = new MenuPanel(actionListener);
		// Attach the PlayerController as a KeyListener
		addKeyListener(playerController);
		setFocusable(true); // Ensure the frame is focusable
		setFocusTraversalKeysEnabled(false); // Disable focus traversal keys

		add(gamePanel, Screen.GAME);
		add(menuPanel, Screen.MENU);

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
