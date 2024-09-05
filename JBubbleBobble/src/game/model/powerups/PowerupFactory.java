package game.model.powerups;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import game.model.Model;
import game.model.tiles.Tile;
import static game.model.level.Level.NUM_HORIZONTAL_TILES;
import static game.model.level.Level.NUM_VERTICAL_TILES;;;

public class PowerupFactory {
	private float distanceTravelled;
	private int numberOfJumpsOnBubbles;
	private int numberOfBubbles;
	private int numberOfBubblesPopped;
	private int numberOfFireBubblesPopped;
	private int numberOfWaterBubblesPopped;
	private int numberOfThunderBubblesPopped;
	private int numberOfSpecialBubblesPopped;
	private int numberOfExtendBubblesPopped;
	private int numberOfYellowCandies;
	private int numberOfBlueCandies;
	private int numberOfPinkCandies;

	private char[][] lvlData;

	public PowerupFactory(char[][] lvlData) {
		this.lvlData = lvlData;
	}

	public float getDistanceTravelled() {
		return distanceTravelled;
	}

	public int getNumberOfJumpsOnBubbles() {
		return numberOfJumpsOnBubbles;
	}

	public int getNumberOfBubbles() {
		return numberOfBubbles;
	}

	public int getNumberOfBubblesPopped() {
		return numberOfBubblesPopped;
	}

	public int getNumberOfFireBubblesPopped() {
		return numberOfFireBubblesPopped;
	}

	public int getNumberOfWaterBubblesPopped() {
		return numberOfWaterBubblesPopped;
	}

	public int getNumberOfThunderBubblesPopped() {
		return numberOfThunderBubblesPopped;
	}

	public int getNumberOfSpecialBubblesPopped() {
		return numberOfSpecialBubblesPopped;
	}

	public int getNumberOfExtendBubblesPopped() {
		return numberOfExtendBubblesPopped;
	}

	public int getNumberOfYellowCandies() {
		return numberOfYellowCandies;
	}

	public int getNumberOfBlueCandies() {
		return numberOfBlueCandies;
	}

	public int getNumberOfPinkCandies() {
		return numberOfPinkCandies;
	}

	public void setDistanceTravelled(float distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
	}

	public void setNumberOfJumpsOnBubbles(int numberOfJumpsOnBubbles) {
		this.numberOfJumpsOnBubbles = numberOfJumpsOnBubbles;
	}

	public void setNumberOfBubbles(int numberOfBubbles) {
		this.numberOfBubbles = numberOfBubbles;
	}

	public void setNumberOfBubblesPopped(int numberOfBubblesPopped) {
		this.numberOfBubblesPopped = numberOfBubblesPopped;
	}

	public void setNumberOfFireBubblesPopped(int numberOfFireBubblesPopped) {
		this.numberOfFireBubblesPopped = numberOfFireBubblesPopped;
	}

	public void setNumberOfWaterBubblesPopped(int numberOfWaterBubblesPopped) {
		this.numberOfWaterBubblesPopped = numberOfWaterBubblesPopped;
	}

	public void setNumberOfThunderBubblesPopped(int numberOfThunderBubblesPopped) {
		this.numberOfThunderBubblesPopped = numberOfThunderBubblesPopped;
	}

	public void setNumberOfSpecialBubblesPopped(int numberOfSpecialBubblesPopped) {
		this.numberOfSpecialBubblesPopped = numberOfSpecialBubblesPopped;
	}

	public void setNumberOfExtendBubblesPopped(int numberOfExtendBubblesPopped) {
		this.numberOfExtendBubblesPopped = numberOfExtendBubblesPopped;
	}

	public void setNumberOfYellowCandies(int numberOfYellowCandies) {
		this.numberOfYellowCandies = numberOfYellowCandies;
	}

	public void setNumberOfBlueCandies(int numberOfBlueCandies) {
		this.numberOfBlueCandies = numberOfBlueCandies;
	}

	public void setNumberOfPinkCandies(int numberOfPinkCandies) {
		this.numberOfPinkCandies = numberOfPinkCandies;
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

	public void increaseNumberOfSpecialBubblesPopped() {
		numberOfSpecialBubblesPopped++;
	}

	public void increaseNumberOfExtendBubblesPopped() {
		numberOfExtendBubblesPopped++;
	}

	public void increaseNumberOfYellowCandies() {
		numberOfYellowCandies++;
	}

	public void increaseNumberOfBlueCandies() {
		numberOfBlueCandies++;
	}

	public void increaseNumberOfPinkCandies() {
		numberOfPinkCandies++;
	}

	public void increaseDistanceTraveled(float newDistance) {
		distanceTravelled += newDistance;
	}

	public float getPercentDiff(float a, float b) {
		return ((a - b) / b) * 100;
	}

	/**
	 * Retrieves the completion percentages for various powerup conditions. The
	 * percentages are calculated based on the difference between the current values
	 * and the minimum conditions required for each powerup. Only positive
	 * percentage differences are included in the returned map.
	 *
	 * @return a map where the key is the powerup class and the value is the
	 *         percentage difference, only including entries with positive
	 *         percentages.
	 */
	public HashMap<Class<? extends Powerup>, Float> getPowerupsConditionCompletions() {
		HashMap<Class<? extends Powerup>, Float> results = new HashMap<>();

		// Compute percentage differences for each powerup condition
		addIfPositive(results, PinkCandy.class, getPercentDiff(numberOfBubbles, PinkCandy.SPAWN_CONDITION));
		addIfPositive(results, BlueCandy.class, getPercentDiff(numberOfBubblesPopped, BlueCandy.SPAWN_CONDITION));
		addIfPositive(results, YellowCandy.class, getPercentDiff(numberOfJumpsOnBubbles, YellowCandy.SPAWN_CONDITION));
		addIfPositive(results, Shoes.class, getPercentDiff(distanceTravelled, Shoes.SPAWN_CONDITION));
		addIfPositive(results, Clock.class, getPercentDiff(numberOfThunderBubblesPopped, Clock.SPAWN_CONDITION));
		addIfPositive(results, Dynamite.class, getPercentDiff(numberOfFireBubblesPopped, Dynamite.SPAWN_CONDITION));
		addIfPositive(results, OrangeParasol.class,
				getPercentDiff(numberOfWaterBubblesPopped, OrangeParasol.SPAWN_CONDITION));
		addIfPositive(results, RedParasol.class,
				getPercentDiff(numberOfSpecialBubblesPopped, RedParasol.SPAWN_CONDITION));
		addIfPositive(results, PurpleParasol.class,
				getPercentDiff(numberOfExtendBubblesPopped, PurpleParasol.SPAWN_CONDITION));
		addIfPositive(results, CrystalRing.class, getPercentDiff(numberOfYellowCandies, CrystalRing.SPAWN_CONDITION));
		addIfPositive(results, AmethystRing.class, getPercentDiff(numberOfBlueCandies, AmethystRing.SPAWN_CONDITION));
		addIfPositive(results, RubyRing.class, getPercentDiff(numberOfPinkCandies, RubyRing.SPAWN_CONDITION));

		return results;
	}

	/**
	 * Adds an entry to the results map if the percentage difference is positive.
	 *
	 * @param results      the map to which the entry will be added.
	 * @param powerupClass the class of the powerup.
	 * @param percentDiff  the percentage difference to be checked.
	 */
	private void addIfPositive(HashMap<Class<? extends Powerup>, Float> results, Class<? extends Powerup> powerupClass,
			float percentDiff) {
		if (percentDiff > 0) {
			results.put(powerupClass, percentDiff);
		}
	}

	public Powerup createPowerup(PowerupManager powerupManager) {
		// Ottieni le condizioni di completamento dei power-up
		HashMap<Class<? extends Powerup>, Float> powerupsConditions = getPowerupsConditionCompletions();
		powerupsConditions.values().stream().filter(x -> x <= 0).forEach(n -> System.out.println(n));

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
				setPowerupPosition(powerupInstance, powerupManager);
				return powerupInstance;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Powerup createRandomPowerup(PowerupManager powerupManager) {
		Powerup randomPowerup = switch (new Random().nextInt(12)) {
		case 0 -> new AmethystRing();
		case 1 -> new BlueCandy();
		case 2 -> new Clock();
		case 3 -> new CrystalRing();
		case 4 -> new Dynamite();
		case 5 -> new OrangeParasol();
		case 6 -> new PinkCandy();
		case 7 -> new PurpleParasol();
		case 8 -> new RedParasol();
		case 9 -> new RubyRing();
		case 10 -> new Shoes();
		default -> new YellowCandy();
		};
		setPowerupPosition(randomPowerup, powerupManager);
		return randomPowerup;
	}

	public void setPowerupPosition(Powerup powerup, PowerupManager powerupManager) {
		Random random = new Random();
		int x = random.nextInt(1, NUM_HORIZONTAL_TILES - 1);
		int y = random.nextInt(1, NUM_VERTICAL_TILES - 1);
		final int MAX_ATTEMPTS = (NUM_HORIZONTAL_TILES - 2) * (NUM_VERTICAL_TILES - 2);

		for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
			// Verifica se la posizione è valida per generare il powerup
			if (lvlData[y][x] == ' ' && Character.isDigit(lvlData[y + 1][x])
					&& !powerupManager.isTherePowerup(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE)) {
				powerup.setPosition(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
				return;
			}

			// Passa al prossimo tile orizzontalmente
			x++;
			if (x >= NUM_HORIZONTAL_TILES - 1) {
				x = 1;
				y++;

				// Se y ha superato il limite, ricomincia dall'alto
				if (y >= NUM_VERTICAL_TILES - 1) {
					y = 1;
				}
			}
		}

	}
}
