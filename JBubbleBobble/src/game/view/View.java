package game.view;

import java.util.Observable;
import java.util.Observer;

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
        if (instance == null) {
            instance = new View(new LevelPanel(gameFrame), gameFrame, new TransitionPanel(gameFrame));
        }
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
        level = Model.getInstance().getCurrentLevel();  // Update the level reference

        if (arg instanceof String s && s.equals("transition")) {
        	levelPanel.startLevelTransition(level.getLevelNumber() + 1);
            //levelPanel.renderTilesOnce();  // Render tiles for the next level
            
        }
        
        
        // Check if the level is changing
        if (arg instanceof String s && s.equals("next")) {
            levelPanel.renderTilesOnce();  // Render tiles for the next level
            
        }
        

        // Check if points are updated
        if (arg instanceof String s && s.equals("points")) {
            gameFrame.updateScoreAndHighscore();  // Update score display
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
