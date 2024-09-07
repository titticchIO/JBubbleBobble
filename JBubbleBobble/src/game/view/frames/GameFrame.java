package game.view.frames;

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

import game.controller.Controller;
import game.controller.InputManager;
import game.model.Model;
import game.model.level.Level;
import game.view.View;
import game.view.panels.EndPanel;
import game.view.panels.LevelPanel;
import game.view.panels.MenuPanel;
import game.view.panels.PausePanel;
import game.view.panels.TransitionPanel;
import game.view.panels.EndPanel.Ending;

/**
 * The {@code GameFrame} class represents the main window frame for the game,
 * which displays various screens such as the menu, gameplay, win/loss panels,
 * transitions, and pauses. It also manages the game's score display.
 */
public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Enum representing the various screens that can be displayed in the game.
	 */
	public enum Screen {
		MENU, GAME, WIN, LOSS, TRANSITION, PAUSE
	}

	private JPanel layoutPanel; // Main panel with a CardLayout to switch between different screens
    private LevelPanel levelPanel; // Panel where the game level is rendered
    private MenuPanel menuPanel; // Panel for the menu screen
    private EndPanel winPanel; // Panel displayed when the player wins
    private EndPanel lossPanel; // Panel displayed when the player loses
    private PausePanel pausePanel; // Panel displayed when the game is paused

    // Labels for displaying the player's score and high score
    private JLabel scoreLabel;
    private JLabel highScoreLabel;

    /**
     * Constructs a {@code GameFrame} object, initializing all game panels and setting up the layout.
     *
     * @param controller             the {@code Controller} instance managing the game logic.
     * @param playerController the {@code InputManager} that handles player input.
     * @param menu             the {@code Menu} object representing the game's main menu state.
     */
	public GameFrame(Controller controller, InputManager playerController) {
		layoutPanel = new JPanel(new CardLayout());
		layoutPanel.setSize(new Dimension((int) (Level.GAME_WIDTH * LevelPanel.SCALE),
				(int) (Level.GAME_HEIGHT * LevelPanel.SCALE)));
		
        // Initialize and add the transition panel to the layout
		TransitionPanel transitionPanel = View.getInstance(this).getTransitionPanel();
		layoutPanel.add(transitionPanel, "TRANSITION");

		// Create and set up the game panel, which includes the score display and game level
		JPanel gamePanel = new JPanel(new BorderLayout());
		JPanel scorePanel = new JPanel(new GridBagLayout());
		scorePanel.setBackground(Color.BLACK);
		scorePanel.setPreferredSize(new Dimension(gamePanel.getWidth(), 30));


		// Initialize score and high score labels with default values of 0
		Font font = new Font("Arial", Font.BOLD, 12);
		scoreLabel = new JLabel("Score: 0");
		scoreLabel.setFont(font);
		scoreLabel.setForeground(Color.YELLOW);
		 // Create constraints for positioning the labels within the score panel
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 0, 20); // Adds space to the right of scoreLabel
		scorePanel.add(scoreLabel, gbc);

		highScoreLabel = new JLabel("HighScore: 0");
		highScoreLabel.setFont(font);
		highScoreLabel.setForeground(Color.YELLOW);

		gbc.insets = new Insets(0, 20, 0, 0); // Adds space to the left of highScoreLabel
		scorePanel.add(highScoreLabel, gbc);
		
		 // Add the score panel to the top of the game panel
		gamePanel.add(scorePanel, BorderLayout.NORTH);

		 // Initialize the level panel and add it to the center of the game panel
		levelPanel = View.getInstance(this).getLevelPanel();
		gamePanel.add(levelPanel, BorderLayout.CENTER);

		// Initialize other panels for different game states
		menuPanel = new MenuPanel();
		winPanel = new EndPanel(Ending.WIN);
		lossPanel = new EndPanel(Ending.LOSS);
		pausePanel = new PausePanel(levelPanel, controller);

		// Add the player controller as a KeyListener to capture player input
		addKeyListener(playerController);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		// Add all panels to the CardLayout panel
		layoutPanel.add(menuPanel, Screen.MENU.name());
		layoutPanel.add(gamePanel, Screen.GAME.name());
		layoutPanel.add(winPanel, Screen.WIN.name());
		layoutPanel.add(lossPanel, Screen.LOSS.name());
		layoutPanel.add(pausePanel, Screen.PAUSE.name());

	     // Set up the frame's properties
		add(layoutPanel); 
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	/**
     * Gets the {@code LevelPanel} which renders the game level.
     *
     * @return the {@code LevelPanel} object.
     */
	public LevelPanel getLevelPanel() {
		return levelPanel;
	}

	 /**
     * Gets the {@code MenuPanel} which displays the main menu.
     *
     * @return the {@code MenuPanel} object.
     */
	public MenuPanel getMenuPanel() {
		return menuPanel;
	}

	 /**
     * Displays a specific screen in the game, such as the menu, game, win, loss, pause, or transition.
     *
     * @param screen the screen to display, as specified by the {@code Screen} enum.
     */
	public void showState(Screen screen) {
		if (screen == Screen.PAUSE)
			pausePanel.drawBackground();
		((CardLayout) layoutPanel.getLayout()).show(layoutPanel, screen.name());
		layoutPanel.revalidate();
		layoutPanel.repaint();
	}

	 /**
     * Updates the score and high score labels based on the current user's points and high score.
     */
	public void updateScoreAndHighscore() {
		int currentPoints = Model.getInstance().getCurrentUser().getPoints();
		int highScore = Model.getInstance().getCurrentUser().getHighScore();

		scoreLabel.setText("Score: " + currentPoints);
		highScoreLabel.setText("HighScore: " + highScore);
	}
}
