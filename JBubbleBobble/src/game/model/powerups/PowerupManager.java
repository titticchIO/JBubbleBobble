package game.model.powerups;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import game.model.Model;
import game.model.level.Level;

public class PowerupManager {

	private Timer spawnTimer;
	private List<Powerup> powerups;

	private int numberOfJumps;
	private int numberOfJumpsOnBubbles;
	private int numberOfBubbles;
	private int numberOfBubblesPopped;
	private float distanceTravelled;

	private boolean pinkCandy;
	private boolean yellowCandy;
	private boolean blueCandy;
	private boolean shoes;
	private boolean pistol;
	private boolean parasol;

	public PowerupManager() {
		powerups = new CopyOnWriteArrayList<>();
	}

	public List<Powerup> getPowerups() {
		return powerups;
	}

	public void increaseNumberOfJumpsOnBubbles() {
		numberOfJumpsOnBubbles++;
	}

	public void increaseNumberOfBubbles() {
		numberOfBubbles++;
	}

	public void increaseNumberOfBubblesPopped() {
		numberOfBubblesPopped++;
	}

	public void increaseDistanceTraveled(float newDistance) {
		distanceTravelled += newDistance;
	}

	public void increaseNumberOfJumps() {
		numberOfJumps++;
	}

	private boolean checkShoes() {
		return distanceTravelled > 2 * Level.GAME_WIDTH;
	}

	private boolean checkPinkCandy() {
		return numberOfBubbles > 2;
	}

	private boolean checkYellowCandy() {
		return numberOfJumpsOnBubbles > 2;
	}

	private boolean checkBlueCandy() {
		return numberOfBubblesPopped > 2;
	}

	private boolean checkPistol() {
		return numberOfJumps > 2;
	}

	public HashMap<Class<? extends Powerup>, Float> getPowerupsConditionCompletions() {
		HashMap<Class<? extends Powerup>, Float> results = new HashMap<Class<? extends Powerup>, Float>();

		// Inserisco i valori per ciascun power-up, convertendo int a float dove
		// necessario
		results.put(BlueCandy.class, (float) (numberOfBubblesPopped - BlueCandy.getSpawnCondition()));
		results.put(PinkCandy.class, (float) (numberOfBubbles - PinkCandy.getSpawnCondition()));
		results.put(YellowCandy.class, (float) (numberOfJumpsOnBubbles - YellowCandy.getSpawnCondition()));
		results.put(Shoes.class, (float) (distanceTravelled - Shoes.getSpawnCondition()));
		results.put(Skeleton.class, (float) (numberOfJumps - Skeleton.getSpawnCondition()));
		results.put(Clock.class, (float) (numberOfBubblesPopped - Clock.getSpawnCondition()));
		results.put(OrangeParasol.class, (float) (numberOfBubblesPopped - OrangeParasol.getSpawnCondition()));
		results.put(RedParasol.class, (float) (numberOfBubblesPopped - RedParasol.getSpawnCondition()));
		results.put(PurpleParasol.class, (float) (numberOfBubblesPopped - PurpleParasol.getSpawnCondition()));

		return results;
	}

	public void createPowerup() {
		// Ottieni le condizioni di completamento dei power-up
		HashMap<Class<? extends Powerup>, Float> powerupsConditions = getPowerupsConditionCompletions();

		// Usa lo stream per trovare le 2 classi con i valori float maggiori
		List<Class<? extends Powerup>> powerupsToSpawn = powerupsConditions.entrySet().stream()
				// Ordina le entry per valore in ordine decrescente
				.sorted(Map.Entry.<Class<? extends Powerup>, Float>comparingByValue(Comparator.reverseOrder()))
				// Limita lo stream ai primi 2
				.limit(2)
				// Mappa le entry alle loro chiavi (le classi di power-up)
				.map(Map.Entry::getKey)
				// Colleziona in una lista
				.collect(Collectors.toList());

		for (Class<? extends Powerup> powerupClass : powerupsToSpawn) {
			// Logica per creare il power-up (ad esempio instanziare le classi)
			try {
				// Crea una nuova istanza della classe di power-up
				Powerup powerupInstance = powerupClass.getDeclaredConstructor().newInstance();
				// Logica per spawnare il power-up nel gioco
				Model.getInstance().getCurrentLevel().spawnPowerup(powerupInstance);
			} catch (Exception e) {
				e.printStackTrace();
				// Gestisci eventuali eccezioni
			}
		}
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
		if (spawnTimer==null) {
			spawnTimer=new Timer();
			
			spawnTimer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					createPowerup();
					spawnTimer=null;
				}
			}, 10000);
		}
			
		for (Powerup powerup : powerups) {
			powerup.updatePowerup();
			if (powerup.isToRemove()) {
				powerups.remove(powerup);
			}
		}
	}

}
