package game.view;

import java.util.Observable;
import java.util.Observer;

import game.model.level.Level;

public class View implements Observer{
	private static View instance;
	private Level level;
	private LevelPanel levelPanel;
	
	
	public static View getInstance() {
		if (instance == null)
			instance = new View(new LevelPanel());
		return instance;
	}

	private View(LevelPanel levelPanel) {
		this.levelPanel=levelPanel;
	}
	
	public void renderLevel() {
		if(level.getPlayer()!=null)
			levelPanel.renderEntity(level.getPlayer());
		level.getTiles().forEach(tile->levelPanel.renderEntity(tile));
		level.getbManager().getBubbles().forEach(b->levelPanel.renderEntity(b));
		level.geteManager().getEnemies().forEach(e->levelPanel.renderEntity(e));
	}
	
	
	
	public LevelPanel getLevelPanel() {
		return levelPanel;
	}

	@Override
	public void update(Observable o, Object arg) {
		this.level=(Level)arg;
		renderLevel();
	}

	
	
}
