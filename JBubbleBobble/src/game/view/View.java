package game.view;

import java.util.Observable;
import java.util.Observer;

import game.model.Model;
import game.model.level.Level;

public class View implements Observer {
    private static View instance;
    private Level level;
    private LevelPanel levelPanel;
    private GameFrame gameFrame;

    // Singleton pattern
    public static View getInstance(GameFrame gameFrame) {
        if (instance == null) {
            instance = new View(new LevelPanel(gameFrame), gameFrame);
        }
        return instance;
    }

    // Costruttore privato, ora sempre richiede GameFrame
    private View(LevelPanel levelPanel, GameFrame gameFrame) {
        this.levelPanel = levelPanel;
        this.gameFrame = gameFrame;
    }

    public LevelPanel getLevelPanel() {
        return levelPanel;
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public void update(Observable o, Object arg) {
        level = Model.getInstance().getCurrentLevel();

        if (arg instanceof String s && s.equals("next")) {
            levelPanel.renderTilesOnce();
        }

        
        if (arg instanceof String s && s.equals("points")) {
            gameFrame.updateScoreAndHighscore(); // Questo ora è sicuro che non sarà mai nullo
        }
        
    }
}
