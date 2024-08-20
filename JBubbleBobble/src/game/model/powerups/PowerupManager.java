package game.model.powerups;

import java.util.ArrayList;
import java.util.List;

public class PowerupManager {
	private List<Powerup> powerups;

	public PowerupManager() {
		powerups = new ArrayList<Powerup>();
	}

	public void addPowerup(Powerup powerup) {
		powerups.add(powerup);
	}

	public void removePowerup(Powerup powerup) {
		powerups.remove(powerup);
	}

	public void updatePowerups() {
		
	}

	public List<Powerup> getPowerups() {
		return powerups;
	}
}
