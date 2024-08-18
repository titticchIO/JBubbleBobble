package game.model.enemies;

import java.util.*;
import java.util.Observable;
import java.util.concurrent.CopyOnWriteArrayList;

public class EnemyManager {
	private List<Enemy> enemies;

	public EnemyManager() {
		enemies = new CopyOnWriteArrayList<>();
	}

	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}

	public void removeEnemy(Enemy enemy) {
		enemies.remove(enemy);
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
