package game.model.powerups;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import game.model.Model;

public class PowerupManager {
	private List<Powerup> powerups;
	
	private boolean pinkCandy;
	private boolean yellowCandy;

	public PowerupManager() {
		powerups = new CopyOnWriteArrayList<>();
	}

	public List<Powerup> getPowerups() {
		return powerups;
	}
	
	
	private boolean checkPinkCandy() {
		return Model.getInstance().getCurrentLevel().getPlayer().getTotBubbles() > 35;
	}
	
	
	private boolean checkYellowCandy() {
		return Model.getInstance().getCurrentLevel().getPlayer().getTotJumpsOnBubbles() > 2;
	}
	
	public void remove(Powerup powerup) {
		powerups.remove(powerup);
	}
	
	public void addPowerup() {
		if (checkPinkCandy() && !pinkCandy) {
			Model.getInstance().getCurrentLevel().spawnPowerup(new BlueCandy(0, 0));
			pinkCandy = true;
		}
		
		if (checkYellowCandy() && !yellowCandy) {
			Model.getInstance().getCurrentLevel().spawnPowerup(new YellowCandy(0, 0));
			yellowCandy = true;
		}
//		if ()
	}

	public void removePowerup(Powerup powerup) {
		powerups.remove(powerup);
	}

	public void updatePowerups() {
		addPowerup();
		for (Powerup powerup : powerups) {
			powerup.updatePowerup();
		}
	}

}
