package game.model.enemies;

import java.util.*;
import java.util.Observable;
import java.util.concurrent.CopyOnWriteArrayList;

public class EnemyManager {
	private List<Enemy> enemies;
	private List<Laser> lasers;

	public EnemyManager() {
		enemies = new CopyOnWriteArrayList<>();
		lasers = new CopyOnWriteArrayList<>();
	}

	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}

	public void addLaser(Laser laser) {
		lasers.add(laser);
	}

	public void removeEnemy(Enemy enemy) {
		enemies.remove(enemy);
	}

	public void removeLaser(Laser laser) {
		lasers.remove(laser);
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public List<Laser> getLasers() {
		return lasers;
	}

	public void updateEnemies() {
		enemies.stream().forEach(Enemy::updateEntity);
		lasers.stream().forEach(Laser::updateEntity);
	}
}
