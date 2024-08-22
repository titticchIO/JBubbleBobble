package game.model.powerups;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import game.model.Model;
import game.model.level.Level;

public class PowerupManager {
	private List<Powerup> powerups;
	
	private boolean pinkCandy;
	private boolean yellowCandy;
	private boolean shoes;

	public PowerupManager() {
		powerups = new CopyOnWriteArrayList<>();
	}

	public List<Powerup> getPowerups() {
		return powerups;
	}
	
	
	private boolean checkShoes() {
		return Model.getInstance().getCurrentLevel().getPlayer().getDistanceTravelled() > 2 * Level.GAME_WIDTH;
	}
	
	private boolean checkPinkCandy() {
		return Model.getInstance().getCurrentLevel().getPlayer().getTotBubbles() > 35;
	}
	
	
	private boolean checkYellowCandy() {
		return Model.getInstance().getCurrentLevel().getPlayer().getTotJumpsOnBubbles() > 2;
	}
	
	public void createPowerup() {
		if (checkPinkCandy() && !pinkCandy) {
			Model.getInstance().getCurrentLevel().spawnPowerup(new BlueCandy(0, 0));
			pinkCandy = true;
		}
		
		if (checkYellowCandy() && !yellowCandy) {
			Model.getInstance().getCurrentLevel().spawnPowerup(new YellowCandy(0, 0));
			yellowCandy = true;
		}
//		if ()
		if (checkShoes()) {
			Model.getInstance().getCurrentLevel().getPlayer().setDistanceTravelled(0);
			Model.getInstance().getCurrentLevel().spawnPowerup(new Shoes(0,0));
			shoes = true;
		}
		
	}

	public void addPowerup(Powerup powerup) {
		powerups.add(powerup);
	}
	
	public void removePowerup(Powerup powerup) {
		powerups.remove(powerup);
	}

	public void updatePowerups() {
		createPowerup();
		for (Powerup powerup : powerups) {
			powerup.updatePowerup();
		}
	}

}
