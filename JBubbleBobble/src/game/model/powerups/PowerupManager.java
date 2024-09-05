package game.model.powerups;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import game.model.Model;

public class PowerupManager {

	private List<Powerup> powerups;
	private PowerupFactory powerupFactory;
	private Timer spawnTimer;

	public PowerupManager(char[][] lvlData) {
		powerups = new CopyOnWriteArrayList<>();
		powerupFactory = new PowerupFactory(lvlData);
	}

	public List<Powerup> getPowerups() {
		return powerups;
	}

	public PowerupFactory getPowerupFactory() {
		return powerupFactory;
	}

	public void createPowerup() {
		Powerup newPowerup = powerupFactory.createPowerup(this);
		if (newPowerup != null) {
			powerups.add(newPowerup);
		}
	}

	public void createRandomPowerup() {
		powerups.add(powerupFactory.createRandomPowerup(this));

	}

	public boolean isTherePowerup(int x, int y) {
		for (Powerup powerup : powerups) {
			if (powerup.getX() == x && powerup.getY() == y)
				return true;
		}
		return false;
	}

	public void addPowerup(Powerup powerup) {
		powerups.add(powerup);
	}

	public void removePowerup(Powerup powerup) {
		powerups.remove(powerup);
	}

	public void updatePowerups() {
		if (spawnTimer == null && powerups.size() < 4) {
			spawnTimer = new Timer("Powerup Update");

			spawnTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					createPowerup();
					spawnTimer = null;
				}
			}, 30000);
		}

		for (Powerup powerup : powerups) {
			powerup.updatePowerup();
			if (powerup.isToRemove()) {
				powerups.remove(powerup);
			}
		}
	}

}
