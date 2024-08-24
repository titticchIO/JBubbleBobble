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

	private float distanceTravelled;
	private int numberOfJumps;
	private int numberOfJumpsOnBubbles;
	private int numberOfBubbles;
	private int numberOfBubblesPopped;
	private int numberOfFireBubblesPopped;
	private int numberOfWaterBubblesPopped;
	private int numberOfThunderBubblesPopped;

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
	public void increaseNumberOfFireBubblesPopped() {
		numberOfFireBubblesPopped++;
	}
	public void increaseNumberOfWaterBubblesPopped() {
		numberOfWaterBubblesPopped++;
	}
	public void increaseNumberOfThunderBubblesPopped() {
		numberOfThunderBubblesPopped++;
	}

	public void increaseDistanceTraveled(float newDistance) {
		distanceTravelled += newDistance;
	}

	public void increaseNumberOfJumps() {
		numberOfJumps++;
	}

	public float getPercentDiff(float a, float b) {
		return ((a - b) / b) * 100;
	}

	public HashMap<Class<? extends Powerup>, Float> getPowerupsConditionCompletions() {
		HashMap<Class<? extends Powerup>, Float> results = new HashMap<Class<? extends Powerup>, Float>();

		// Adds for each powerup the percentage difference between the current value and
		// the minimum;
		results.put(BlueCandy.class, getPercentDiff(numberOfBubblesPopped, BlueCandy.getSpawnCondition()));
		results.put(PinkCandy.class, getPercentDiff(numberOfBubbles, PinkCandy.getSpawnCondition()));
		results.put(YellowCandy.class, getPercentDiff(numberOfJumpsOnBubbles, YellowCandy.getSpawnCondition()));
		results.put(Shoes.class, getPercentDiff(distanceTravelled, Shoes.getSpawnCondition()));
		results.put(Skeleton.class, getPercentDiff(numberOfJumps, Skeleton.getSpawnCondition()));
		results.put(Clock.class, getPercentDiff(numberOfBubblesPopped, Clock.getSpawnCondition()));
		results.put(Dynamite.class, getPercentDiff(numberOfBubblesPopped, Dynamite.getSpawnCondition()));
		results.put(CrystalRing.class, getPercentDiff(numberOfBubblesPopped, CrystalRing.getSpawnCondition()));
		
		
		
		HashMap<Class<? extends Powerup>, Float> parasolRes = new HashMap<Class<? extends Powerup>, Float>();
		parasolRes.put(OrangeParasol.class, getPercentDiff(numberOfBubblesPopped, OrangeParasol.getSpawnCondition()));
		parasolRes.put(RedParasol.class, getPercentDiff(numberOfBubblesPopped, RedParasol.getSpawnCondition()));
		parasolRes.put(PurpleParasol.class, getPercentDiff(numberOfBubblesPopped, PurpleParasol.getSpawnCondition()));

		Map.Entry<Class<? extends Powerup>, Float> maxEntry = null;
		for (Map.Entry<Class<? extends Powerup>, Float> entry : parasolRes.entrySet()) {
			if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
				maxEntry = entry;
			}
		}

		// Aggiungi l'entry con il valore massimo a results
		if (maxEntry != null) {
			results.put(maxEntry.getKey(), maxEntry.getValue());
		}
		
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
		if (spawnTimer == null) {
			spawnTimer = new Timer("Powerup Update");

			spawnTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					createPowerup();
					spawnTimer.cancel();
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
