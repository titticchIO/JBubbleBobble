package game.model.powerups;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import game.model.Model;
import game.model.entities.Player;
import game.model.level.Level;

public class PowerupManager {
	private List<Powerup> powerups;
	
	private boolean pinkCandy;
	private boolean yellowCandy;
	private boolean blueCandy;
	private boolean shoes;
	private boolean pistol;

	public PowerupManager() {
		powerups = new CopyOnWriteArrayList<>();
	}

	public List<Powerup> getPowerups() {
		return powerups;
	}
	
	
	private boolean checkShoes() {
		return Player.getInstance().getDistanceTravelled() > 2 * Level.GAME_WIDTH;
	}
	
	private boolean checkPinkCandy() {
		return Player.getInstance().getTotBubbles() > 2;
	}
	
	private boolean checkYellowCandy() {
		return Player.getInstance().getTotJumpsOnBubbles() > 2;
	}
	
	private boolean checkBlueCandy() {
		return Player.getInstance().getTotBubblesPopped() > 2;
	}
	
	private boolean checkPistol() {
		return Player.getInstance().getTotJumps() > 2;
	}
	
	
	public void createPowerup() {
		if (checkPinkCandy() && !pinkCandy) {
			Model.getInstance().getCurrentLevel().spawnPowerup(new PinkCandy(0, 0));
			pinkCandy = true;
		}
		
		if (checkYellowCandy() && !yellowCandy) {
			Model.getInstance().getCurrentLevel().spawnPowerup(new YellowCandy(0, 0));
			yellowCandy = true;
		}
		
		if (checkBlueCandy() && !blueCandy) {
			Model.getInstance().getCurrentLevel().spawnPowerup(new BlueCandy(0, 0));
			blueCandy = true;
		}
		if (checkShoes() && !shoes) {
			Model.getInstance().getCurrentLevel().getPlayer().setDistanceTravelled(0);
			Model.getInstance().getCurrentLevel().spawnPowerup(new Shoes(0,0));
			shoes = true;
		}
		
		if (checkPistol() && !pistol) {
			Model.getInstance().getCurrentLevel().spawnPowerup(new Pistol(0, 0));
			pistol = true;
		}
		
	}

	public boolean isTherePowerup(int x, int y) {
		for (Powerup powerup : powerups) {
			if (powerup.getX() == x && powerup.getY() == y)
				return true;
		}
		return false;
	}
	
	
	public void printPowerups() {
		for (Powerup powerup : powerups) {
			System.out.println("X: "+powerup.getX());
			System.out.println("Y: "+powerup.getY());
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
			if (powerup.isToRemove()) {
				powerups.remove(powerup);
			}
		}
	}

}
