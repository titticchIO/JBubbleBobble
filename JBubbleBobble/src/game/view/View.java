package game.view;

import java.util.Observable;
import java.util.Observer;

import game.controller.AudioManager;
import game.controller.gamestates.Menu;
import game.model.Model;
import game.model.level.Level;

public class View implements Observer {
	private static View instance;
	private Level level;
	private LevelPanel levelPanel;
	private GameFrame gameFrame;
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

	public LevelPanel getLevelPanel() {
		return levelPanel;
	}

	public TransitionPanel getTransitionPanel() {
		return transitionPanel;
	}

	public Level getLevel() {
		return level;
	}

	@Override
	public void update(Observable o, Object arg) {
		level = Model.getInstance().getCurrentLevel(); // Update the level reference
		
		if (arg instanceof String s) {
			switch(s) {
			case "transition"->levelPanel.startLevelTransition(level.getLevelNumber() + 1);
			case "next"->levelPanel.renderTilesOnce(); // Render tiles for the next level
			case "points"->gameFrame.updateScoreAndHighscore(); // Update score display
			
//			AUDIO NOTIFICATIONS:
			case "bubble"-> AudioManager.getInstance().play(AnimationLoader.getAbsolutePath("/Audio/Sound Effects/Bubble Bobble SFX (6).wav"));
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
