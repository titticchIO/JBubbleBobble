package game.view;

import java.awt.event.ActionListener;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.controller.Game;

import game.controller.PlayerController;
import game.model.level.Level;

public class GameFrame extends JFrame {

	public enum Screen {
		MENU, GAME
	}

	private JPanel layoutPanel;
	private GamePanel gamePanel;
	private MenuPanel menuPanel;

	public GameFrame(Game game, PlayerController playerController, ActionListener actionListener) {

		layoutPanel = new JPanel(new CardLayout());
		layoutPanel.setPreferredSize(
				new Dimension((int) (Level.GAME_WIDTH * GamePanel.SCALE), (int) (Level.GAME_HEIGHT * GamePanel.SCALE)));
		gamePanel = new GamePanel();
		menuPanel = new MenuPanel(actionListener);
		System.out.println(menuPanel.getSize());
		// Attach the PlayerController as a KeyListener
		addKeyListener(playerController);
		setFocusable(true); // Ensure the frame is focusable
		setFocusTraversalKeysEnabled(false); // Disable focus traversal keys

		layoutPanel.add(menuPanel, Screen.MENU.name());
		layoutPanel.add(gamePanel, Screen.GAME.name());

		setResizable(false);
		setSize(
				new Dimension((int) (Level.GAME_WIDTH * GamePanel.SCALE), (int) (Level.GAME_HEIGHT * GamePanel.SCALE)));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void showState(Screen screen) {
		switch (screen) {
		case MENU -> ((CardLayout) layoutPanel.getLayout()).show(layoutPanel, Screen.MENU.name());
		case GAME -> ((CardLayout) layoutPanel.getLayout()).show(layoutPanel, Screen.GAME.name());
		}
	}

	@Override
	public void repaint() {
		super.repaint();
		gamePanel.repaint();
	}

}
