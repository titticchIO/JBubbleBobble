package game.view;

import java.util.Observable;
import java.util.Observer;

import game.model.Model;
import game.model.level.Level;
import game.view.frames.CheatFrame;
import game.view.frames.GameFrame;
import game.view.panels.LevelPanel;
import game.view.panels.MenuPanel;
import game.view.panels.TransitionPanel;

/**
 * The {@code View} class is responsible for managing and updating the graphical
 * components of the game. It implements the Observer pattern to observe the
 * Model and update the view accordingly.
 */
public class View implements Observer {

	private static View instance; // the only instance of the View object to be obtained using getInstance()
	private GameFrame gameFrame; // the main window frame for the game, which displays various screens
	private LevelPanel levelPanel; // the panel which draws the actual level
	private CheatFrame cheatFrame; // a JFrame containing the buttons to activate cheats
	private Level level; // the current level to be drawn, updated through notifications from the Model
	private TransitionPanel transitionPanel; // the panel responsible for rendering level transitions

	/**
	 * Gets the singleton instance of {@code View}. If the instance does not exist,
	 * it creates a new one with the specified {@code GameFrame}.
	 *
	 * @param gameFrame the {@code GameFrame} instance that represents the main game
	 *                  window.
	 * @return the singleton instance of {@code View}.
	 */
	public static View getInstance(GameFrame gameFrame) {
		if (instance == null)
			instance = new View(gameFrame, new LevelPanel(), new TransitionPanel());
		return instance;
	}

	/**
	 * Gets the current singleton instance of {@code View}. This method does not
	 * create a new instance if one does not exist.
	 *
	 * @return the singleton instance of {@code View}.
	 */
	public static View getInstance() {
		return instance;
	}

	/**
	 * Private constructor for initializing the {@code View} with the provided
	 * {@code GameFrame}, {@code LevelPanel} and {@code TransitionPanel}.
	 *
	 * @param gameFrame       the {@code GameFrame} instance that represents the
	 *                        main game window.
	 * @param levelPanel      the {@code LevelPanel} used for rendering the game
	 *                        level.
	 * @param transitionPanel the {@code TransitionPanel} used for displaying
	 *                        transitions between levels.
	 */
	private View(GameFrame gameFrame, LevelPanel levelPanel, TransitionPanel transitionPanel) {
		this.gameFrame = gameFrame;
		this.levelPanel = levelPanel;
		this.transitionPanel = transitionPanel;
	}

	/**
	 * Gets the {@code GameFrame} instance.
	 *
	 * @return the {@code GameFrame} used by the game.
	 */
	public GameFrame getGameFrame() {
		return gameFrame;
	}

	/**
	 * Gets the {@code MenuPanel} from the game frame.
	 *
	 * @return the {@code MenuPanel} instance.
	 */
	public MenuPanel getMenuPanel() {
		return gameFrame.getMenuPanel();
	}

	/**
	 * Gets the {@code LevelPanel} used for rendering the game level.
	 *
	 * @return the {@code LevelPanel} instance.
	 */
	public LevelPanel getLevelPanel() {
		return levelPanel;
	}

	/**
	 * Gets the {@code TransitionPanel} used for rendering level transitions.
	 *
	 * @return the {@code TransitionPanel} instance.
	 */
	public TransitionPanel getTransitionPanel() {
		return transitionPanel;
	}

	/**
	 * Gets the current level instance.
	 *
	 * @return the current {@code Level} instance.
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Gets the {@code CheatFrame} used for the cheat interface.
	 *
	 * @return the {@code CheatFrame} instance.
	 */
	public CheatFrame getCheatFrame() {
		return cheatFrame;
	}

	/**
	 * Sets the {@code CheatFrame} instance for cheat controls.
	 *
	 * @param cheatFrame the {@code CheatFrame} instance to be set.
	 */
	public void setCheatFrame(CheatFrame cheatFrame) {
		this.cheatFrame = cheatFrame;
	}

	/**
	 * Updates the view based on notifications from the observed {@code Model}.
	 * Depending on the argument passed, this method will update the level, score,
	 * or handle audio notifications.
	 *
	 * @param o   the observable object being observed.
	 * @param arg an argument passed by the {@code Observable} to notify the view
	 *            about updates (e.g., transitions, score updates).
	 */
	@Override
	public void update(Observable o, Object arg) {
		level = Model.getInstance().getCurrentLevel(); // Update the level reference

		if (arg instanceof String s) {
			switch (s) {
			case "transition" -> levelPanel.startLevelTransition(level.getLevelNumber() + 1);
			case "next" -> {
				levelPanel.renderTilesOnce(gameFrame);
				cheatFrame.getInvincibilityButton().setSelected(false);
			} // Render tiles for the next level
			case "points" -> gameFrame.updateScoreAndHighscore(); // Update score display

//			AUDIO NOTIFICATIONS:
			case "bubble" -> AudioManager.getInstance().play(Paths.getAbsolutePath("/audio/bubble.wav"));
			case "jump" -> AudioManager.getInstance().play(Paths.getAbsolutePath("/audio/jump.wav"));
			case "heal" -> AudioManager.getInstance().play(Paths.getAbsolutePath("/audio/heal.wav"));
			case "lifeLost" -> AudioManager.getInstance().play(Paths.getAbsolutePath("/audio/lifeLost.wav"));
			case "bossHit" -> AudioManager.getInstance().play(Paths.getAbsolutePath("/audio/bossHit.wav"));
			case "bossKill" -> AudioManager.getInstance().play(Paths.getAbsolutePath("/audio/bossKill.wav"));
			}
		}

		// If the model was reset, make sure to re-render the level
		if (arg instanceof Level) {
			levelPanel.renderTilesOnce(gameFrame);
		}
	}
}
