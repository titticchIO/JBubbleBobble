package game.view;

import java.util.Observable;
import java.util.Observer;

import game.model.Model;
import game.model.Paths;
import game.model.level.Level;
import game.view.frames.CheatFrame;
import game.view.frames.GameFrame;
import game.view.panels.LevelPanel;
import game.view.panels.MenuPanel;
import game.view.panels.TransitionPanel;

public class View implements Observer {
	private static View instance;
	private Level level;
	private LevelPanel levelPanel;
	private GameFrame gameFrame;
	private CheatFrame cheatFrame;

	private TransitionPanel transitionPanel;

	// Singleton pattern to get View instance
	public static View getInstance(GameFrame gameFrame) {
		if (instance == null)
			instance = new View(new LevelPanel(gameFrame), gameFrame, new TransitionPanel(gameFrame));
		return instance;
	}

	public static View getInstance() {
		return instance;
	}

	// Private constructor requiring GameFrame
	private View(LevelPanel levelPanel, GameFrame gameFrame, TransitionPanel transitionPanel) {
		this.levelPanel = levelPanel;
		this.gameFrame = gameFrame;
		this.transitionPanel = transitionPanel;
	}

	public GameFrame getGameFrame() {
		return gameFrame;
	}

	public LevelPanel getLevelPanel() {
		return levelPanel;
	}

	public TransitionPanel getTransitionPanel() {
		return transitionPanel;
	}

	public Level getLevel() {
		return level;
	}

	public CheatFrame getCheatFrame() {
		return cheatFrame;
	}

	public void setCheatFrame(CheatFrame cheatFrame) {
		this.cheatFrame = cheatFrame;
	}

	@Override
	public void update(Observable o, Object arg) {
		level = Model.getInstance().getCurrentLevel(); // Update the level reference

		if (arg instanceof String s) {
			switch (s) {
			case "transition" -> levelPanel.startLevelTransition(level.getLevelNumber() + 1);
			case "next" -> levelPanel.renderTilesOnce(); // Render tiles for the next level
			case "points" -> gameFrame.updateScoreAndHighscore(); // Update score display

//			AUDIO NOTIFICATIONS:
//			case "bubble" -> AudioManager.getInstance()
//					.play(Paths.getAbsolutePath("/Audio/Sound Effects/Bubble Bobble SFX (6).wav"));
			case "jump"->AudioManager.getInstance()
			.play(Paths.getAbsolutePath("/Audio/Sound Effects/Bubble Bobble SFX (4).wav"));
			case "heal"->AudioManager.getInstance()
			.play(Paths.getAbsolutePath("/Audio/Sound Effects/Bubble Bobble SFX (20).wav"));
			case "lifeLost"->AudioManager.getInstance()
			.play(Paths.getAbsolutePath("/Audio/Sound Effects/Bubble Bobble SFX (3).wav"));
			case "bossHit"->AudioManager.getInstance()
			.play(Paths.getAbsolutePath("/Audio/Sound Effects/Bubble Bobble SFX (1).wav"));
			case "bossKill"->AudioManager.getInstance()
			.play(Paths.getAbsolutePath("/Audio/Sound Effects/Bubble Bobble SFX (18).wav"));
			}
		}

		// If the model was reset, make sure to re-render the level
		if (arg instanceof Level) {
			levelPanel.renderTilesOnce();
		}
	}

	public MenuPanel getMenuPanel() {
		return gameFrame.getMenuPanel();
	}
}
