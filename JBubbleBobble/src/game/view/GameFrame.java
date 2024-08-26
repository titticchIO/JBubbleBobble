package game.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.controller.Game;
import game.controller.InputManager;
import game.controller.gamestates.GameState;
import game.controller.gamestates.Menu;
import game.model.Model;
import game.model.level.Level;
import game.view.EndPanel.Ending;

public class GameFrame extends JFrame {

	public enum Screen {
		MENU, GAME, WIN, LOSS, TRANSITION
	}

	private JPanel layoutPanel;
	private LevelPanel levelPanel;
	private MenuPanel menuPanel;

	private EndPanel winPanel;
	private EndPanel lossPanel;

	// Aggiunti per mostrare e aggiornare punti e highscore
	private JLabel scoreLabel;
	private JLabel highScoreLabel;

	public GameFrame(Game game, InputManager playerController, Menu menu) {
		layoutPanel = new JPanel(new CardLayout());
		layoutPanel.setSize(new Dimension((int) (Level.GAME_WIDTH * LevelPanel.SCALE),
				(int) (Level.GAME_HEIGHT * LevelPanel.SCALE)));

		TransitionPanel transitionPanel = View.getInstance(this).getTransitionPanel();
		layoutPanel.add(transitionPanel, "TRANSITION");

		JPanel gamePanel = new JPanel(new BorderLayout());
		JPanel scorePanel = new JPanel(new GridBagLayout());
		scorePanel.setBackground(Color.BLACK);
		scorePanel.setPreferredSize(new Dimension(gamePanel.getWidth(), 30));

		Font font = new Font("Arial", Font.BOLD, 12);

		// Inizializzazione delle label con valori 0
		scoreLabel = new JLabel("Score: 0");
		scoreLabel.setFont(font);
		scoreLabel.setForeground(Color.YELLOW);
		// Creazione delle constraints per le label
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 0, 20); // Spazio a destra di scoreLabel
		scorePanel.add(scoreLabel, gbc);

		highScoreLabel = new JLabel("HighScore: 0");
		highScoreLabel.setFont(font);
		highScoreLabel.setForeground(Color.YELLOW);
		// Aggiunta della highScoreLabel
		gbc.insets = new Insets(0, 20, 0, 0); // Spazio a sinistra di highScoreLabe
		scorePanel.add(highScoreLabel, gbc);

		gamePanel.add(scorePanel, BorderLayout.NORTH);

		levelPanel = View.getInstance(this).getLevelPanel();
		gamePanel.add(levelPanel, BorderLayout.CENTER);

		menuPanel = new MenuPanel(menu);
		winPanel = new EndPanel(Ending.WIN);
		lossPanel = new EndPanel(Ending.LOSS);

		// Attach the PlayerController as a KeyListener
		addKeyListener(playerController);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		layoutPanel.add(menuPanel, Screen.MENU.name());
		layoutPanel.add(gamePanel, Screen.GAME.name());
		layoutPanel.add(winPanel, Screen.WIN.name());
		layoutPanel.add(lossPanel, Screen.LOSS.name());

		add(layoutPanel);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	public LevelPanel getLevelPanel() {
		return levelPanel;
	}

	public MenuPanel getMenuPanel() {
		return menuPanel;
	}

	public void showState(Screen screen) {
		((CardLayout) layoutPanel.getLayout()).show(layoutPanel, screen.name());
		layoutPanel.revalidate();
		layoutPanel.repaint();
	}

	@Override
	public void repaint() {
		super.repaint();
		if (GameState.state == GameState.PLAYING) {
			levelPanel.repaint();
		}
	}

	// Metodo per aggiornare i punti e l'highscore
	public void updateScoreAndHighscore() {
		int currentPoints = Model.getInstance().getCurrentUser().getPoints();
		int highScore = Model.getInstance().getCurrentUser().getHighScore();

		scoreLabel.setText("Score: " + currentPoints);
		highScoreLabel.setText("HighScore: " + highScore);
	}
}
