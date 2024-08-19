package game.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import editor.model.LevelReader;
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
		setChanged();
		notifyObservers(currentLevel);
		levelIterator = levels.iterator();
	}

	public Level getCurrentLevel() {
		return currentLevel;
		
	}

	public void loadLevels() {
		
		LevelReader.getLevels().forEach(s->{
			levels.add(new Level(Integer.parseInt(s)));
		});
//		currentLevel = levels.getFirst();
		currentLevel = levels.get(2);
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
