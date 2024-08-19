package game.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import editor.model.LevelReader;
import game.model.level.Level;

public class Model extends Observable {
	public enum State {
		PLAY,
		GAME_OVER,
		WIN
	}
	
	private List<Level> levels;
	private Iterator<Level> levelIterator;
	private Level currentLevel;

	private long score;
	private long highScore;

	private static Model instance;
	
	private State state;

	public static Model getInstance() {
		if (instance == null)
			instance = new Model();
		return instance;
	}

	private Model() {
		levels = new ArrayList<Level>();
		setChanged();
		notifyObservers(currentLevel);
		loadLevels();
		levelIterator = levels.iterator();
	}

	public Level getCurrentLevel() {
		return currentLevel;

	}

	public void loadLevels() {

		LevelReader.getLevels().forEach(s -> {
			levels.add(new Level(Integer.parseInt(s)));
		});
		currentLevel = levels.getFirst();
//		currentLevel = levels.get(4);
		
		state = State.PLAY;
		
		setChanged();
		notifyObservers(currentLevel);
	}

	public void nextLevel() {
		currentLevel = levelIterator.next();
		setChanged();
		notifyObservers("next");
	}

	public void resetLevels() {
		levels = new ArrayList<Level>();
		loadLevels();
	}

	public void updateModel() {
		currentLevel.updateLevel();
		if (currentLevel.getEnemyManager().getEnemies().size() == 0
				&& currentLevel.getBubbleManager().getPlayerBubbles().stream().allMatch(b -> !b.hasEnemy()))
			
			try {
				nextLevel();
			} catch (Exception e) {
				state = State.WIN;
			}

		setChanged();
		notifyObservers(currentLevel);
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}
