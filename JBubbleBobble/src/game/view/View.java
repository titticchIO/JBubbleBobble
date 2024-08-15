package game.view;

import java.util.Observable;
import java.util.Observer;

import game.model.level.Level;

public class View implements Observer {
	private static View instance;
	private Level level;
	private LevelPanel levelPanel;

	public static View getInstance() {
		if (instance == null)
			instance = new View(new LevelPanel());
		return instance;
	}

	private View(LevelPanel levelPanel) {
		this.levelPanel = levelPanel;
	}

	public LevelPanel getLevelPanel() {
		return levelPanel;
	}
	

	public Level getLevel() {
		return level;
	}

	@Override
	public void update(Observable o, Object arg) {
		this.level = (Level) arg;
	}

}
