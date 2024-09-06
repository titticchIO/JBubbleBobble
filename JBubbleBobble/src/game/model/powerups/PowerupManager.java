package game.model.powerups;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

public class PowerupManager {

	private List<Powerup> powerups;
	private PowerupFactory powerupFactory;
	private Timer spawnTimer;

	public PowerupManager(char[][] lvlData) {
		powerups = new CopyOnWriteArrayList<>();
		powerupFactory = new PowerupFactory(lvlData);
		spawnTimer = new Timer();
		spawnTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (powerups.size() <= 4)
					createPowerup();

			}
		}, 30000, 30000);
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

}
