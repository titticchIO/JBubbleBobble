package game.model.enemies;

import java.util.*;
import java.util.Observable;


public class EnemyManager extends Observable {
	private List<Enemy> enemies;
	private static EnemyManager instance;

	public static EnemyManager getInstance() {
		if (instance == null)
			instance = new EnemyManager();
		return instance;
	}

	private EnemyManager() {
		enemies = new ArrayList<Enemy>();
	}

	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
		setChanged();
		notifyObservers(enemy);
	}
	
	public void removeEnemy(Enemy enemy) {
		enemies.remove(enemy);
		setChanged();
		notifyObservers(enemy);
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}
	
	public void updateEnemies() {
		for (Enemy e : enemies) {
			e.updateEntity();
		}
	}
}