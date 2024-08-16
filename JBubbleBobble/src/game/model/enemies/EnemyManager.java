package game.model.enemies;

import java.util.*;
import java.util.Observable;

public class EnemyManager {
	private List<Enemy> enemies;

	public EnemyManager() {
		enemies = new ArrayList<Enemy>();
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
