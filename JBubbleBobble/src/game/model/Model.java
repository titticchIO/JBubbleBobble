package game.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import game.model.level.Level;

public class Model extends Observable {
	private List<Level> levels;
	private Iterator<Level> levelIterator;
	private Level currentLevel;

	private long score;
	private long highScore;

	private static Model instance;

	public static Model getInstance() {
		if (instance == null)
			instance = new Model();
		return instance;
	}

	private Model() {
		levels = new ArrayList<Level>();
		addLevels();
		currentLevel = levels.getFirst();
		levelIterator = levels.iterator();
	}

	public Level getCurrentLevel() {
		return currentLevel;
		
	}

	public void addLevels() {
		levels.add(new Level(111));
		levels.add(new Level(3));
		levels.add(new Level(333));
		levels.add(new Level(4));
		levels.add(new Level(5));
		levels.add(new Level(777));
		setChanged();
		notifyObservers(currentLevel);
	}

	public void nextLevel() {
		currentLevel = levelIterator.next();
		setChanged();
		notifyObservers(currentLevel);
	}

	public void updateModel() {
		currentLevel.updateLevel();
		setChanged();
		notifyObservers(currentLevel);
	}

}
