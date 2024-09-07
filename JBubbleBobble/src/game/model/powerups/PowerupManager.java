package game.model.powerups;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The {@code PowerupManager} class is responsible for managing the spawning and
 * removal of power-ups in the game. It uses a PowerupFactory to create
 * power-ups and maintains a list of active power-ups within the game level. The
 * class also handles periodic spawning of new power-ups at set intervals.
 */
public class PowerupManager {

	private List<Powerup> powerups;
	private PowerupFactory powerupFactory;
	private Timer spawnTimer;

	/**
	 * Constructs a new PowerupManager with the specified level data. This manager
	 * initializes the power-up list and factory, and sets up a timer to
	 * periodically spawn new power-ups every 30 seconds.
	 * 
	 * @param lvlData The level data array representing the game map.
	 */
	public PowerupManager(char[][] lvlData) {
		powerups = new CopyOnWriteArrayList<>();
		powerupFactory = new PowerupFactory(lvlData);
		spawnTimer = new Timer();
		// Spawns a power-up every 30 seconds
		spawnTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (powerups.size() <= 4)
					createPowerup();
			}
		}, 30000, 30000);
	}

	/**
	 * Gets the list of active power-ups in the game.
	 * 
	 * @return A list of active power-ups.
	 */
	public List<Powerup> getPowerups() {
		return powerups;
	}

	/**
	 * Gets the PowerupFactory instance used by this manager.
	 * 
	 * @return The PowerupFactory instance.
	 */
	public PowerupFactory getPowerupFactory() {
		return powerupFactory;
	}

	/**
	 * Creates and adds a new power-up to the game using the PowerupFactory. The
	 * created power-up is added to the list of active power-ups.
	 */
	public void createPowerup() {
		Powerup newPowerup = powerupFactory.createPowerup(this);
		if (newPowerup != null) {
			powerups.add(newPowerup);
		}
	}

	/**
	 * Creates and adds a random power-up to the game using the PowerupFactory. The
	 * randomly generated power-up is added to the list of active power-ups.
	 */
	public void createRandomPowerup() {
		powerups.add(powerupFactory.createRandomPowerup(this));

	}

	/**
	 * Checks whether there is a power-up at the specified (x, y) coordinates in the
	 * game.
	 * 
	 * @param x The x-coordinate of the tile.
	 * @param y The y-coordinate of the tile.
	 * @return True if there is a power-up at the given coordinates, false
	 *         otherwise.
	 */
	public boolean isTherePowerup(int x, int y) {
		for (Powerup powerup : powerups) {
			if (powerup.getX() == x && powerup.getY() == y)
				return true;
		}
		return false;
	}

	/**
	 * Adds a power-up to the list of active power-ups.
	 * 
	 * @param powerup The power-up to be added.
	 */
	public void addPowerup(Powerup powerup) {
		powerups.add(powerup);
	}

	/**
	 * Removes a power-up from the list of active power-ups.
	 * 
	 * @param powerup The power-up to be removed.
	 */
	public void removePowerup(Powerup powerup) {
		powerups.remove(powerup);
	}

}
