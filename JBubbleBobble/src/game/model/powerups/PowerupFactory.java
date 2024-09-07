package game.model.powerups;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import game.model.tiles.Tile;
import static game.model.level.Level.NUM_HORIZONTAL_TILES;
import static game.model.level.Level.NUM_VERTICAL_TILES;;;

/**
 * The {@code PowerupFactory} is responsible for generating power-ups in the
 * game. It tracks various game metrics and based on these metrics, determines
 * the types of power-ups to spawn. The factory is also responsible for
 * determining the appropriate spawn position for power-ups on the game level.
 */
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

	/**
	 * Constructs a new PowerupFactory with the specified level data.
	 *
	 * @param lvlData The level data array representing the game map.
	 */
	public PowerupFactory(char[][] lvlData) {
		this.lvlData = lvlData;
	}
	// Getter and setter methods for various game metrics

	/**
	 * Gets the total distance traveled by the player.
	 *
	 * @return the distance traveled.
	 */
	public float getDistanceTravelled() {
		return distanceTravelled;
	}

	/**
	 * Gets the number of jumps on bubbles by the player.
	 *
	 * @return the number of jumps on bubbles.
	 */
	public int getNumberOfJumpsOnBubbles() {
		return numberOfJumpsOnBubbles;
	}

	/**
	 * Gets the total number of bubbles created by the player.
	 *
	 * @return the number of bubbles.
	 */
	public int getNumberOfBubbles() {
		return numberOfBubbles;
	}

	/**
	 * Gets the number of bubbles popped by the player.
	 *
	 * @return the number of bubbles popped.
	 */
	public int getNumberOfBubblesPopped() {
		return numberOfBubblesPopped;
	}

	/**
	 * Gets the number of fire bubbles popped by the player.
	 *
	 * @return the number of fire bubbles popped.
	 */
	public int getNumberOfFireBubblesPopped() {
		return numberOfFireBubblesPopped;
	}

	/**
	 * Gets the number of water bubbles popped by the player.
	 *
	 * @return the number of water bubbles popped.
	 */
	public int getNumberOfWaterBubblesPopped() {
		return numberOfWaterBubblesPopped;
	}

	/**
	 * Gets the number of thunder bubbles popped by the player.
	 *
	 * @return the number of thunder bubbles popped.
	 */
	public int getNumberOfThunderBubblesPopped() {
		return numberOfThunderBubblesPopped;
	}

	/**
	 * Gets the number of special bubbles popped by the player.
	 *
	 * @return the number of special bubbles popped.
	 */
	public int getNumberOfSpecialBubblesPopped() {
		return numberOfSpecialBubblesPopped;
	}

	/**
	 * Gets the number of extend bubbles popped by the player.
	 *
	 * @return the number of extend bubbles popped.
	 */
	public int getNumberOfExtendBubblesPopped() {
		return numberOfExtendBubblesPopped;
	}

	/**
	 * Gets the number of yellow candies collected by the player.
	 *
	 * @return the number of yellow candies collected.
	 */
	public int getNumberOfYellowCandies() {
		return numberOfYellowCandies;
	}

	/**
	 * Gets the number of blue candies collected by the player.
	 *
	 * @return the number of blue candies collected.
	 */
	public int getNumberOfBlueCandies() {
		return numberOfBlueCandies;
	}

	/**
	 * Gets the number of pink candies collected by the player.
	 *
	 * @return the number of pink candies collected.
	 */
	public int getNumberOfPinkCandies() {
		return numberOfPinkCandies;
	}

	// Setter methods to update various game metrics

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

	// Methods to increment various game metrics

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

	/**
	 * Calculates the percentage difference between two values.
	 *
	 * @param a the first value.
	 * @param b the second value.
	 * @return the percentage difference between the two values.
	 */
	public float getPercentDiff(float a, float b) {
		return ((a - b) / b) * 100;
	}

	/**
	 * Retrieves the completion percentages for various power-up conditions. These
	 * percentages are based on the difference between the current game metrics and
	 * the spawn conditions for each power-up. Only positive percentage differences
	 * are included.
	 *
	 * @return results a map where the key is the power-up class and the value is
	 *         the percentage completion of that power-up's spawn condition.
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

	/**
	 * Creates a power-up based on the current game metrics. This method selects the
	 * top two power-ups with the highest spawn condition completion and creates the
	 * first available power-up.
	 *
	 * @param powerupManager the power-up manager that handles power-up
	 *                       interactions.
	 * @return the created power-up or null if no power-up could be created.
	 */
	public Powerup createPowerup(PowerupManager powerupManager) {
		HashMap<Class<? extends Powerup>, Float> powerupsConditions = getPowerupsConditionCompletions();

		// Use stream to find the top 2 power-ups
		List<Class<? extends Powerup>> powerupsToSpawn = powerupsConditions.entrySet().stream()
				.sorted(Map.Entry.<Class<? extends Powerup>, Float>comparingByValue(Comparator.reverseOrder())).limit(2)
				.map(Map.Entry::getKey).collect(Collectors.toList());

		// Creates the 2 powerups
		for (Class<? extends Powerup> powerupClass : powerupsToSpawn) {
			try {
				Powerup powerupInstance = powerupClass.getDeclaredConstructor().newInstance();
				setPowerupPosition(powerupInstance, powerupManager);
				return powerupInstance;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Creates a random power-up and sets its position in the game.
	 *
	 * @param powerupManager the power-up manager that handles power-up
	 *                       interactions.
	 * @return the created random power-up.
	 */
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

	/**
	 * Sets the position of the specified power-up in the game level on top of a
	 * random tile.
	 *
	 * @param powerup        the power-up to position.
	 * @param powerupManager the manager handling power-up interactions.
	 */
	public void setPowerupPosition(Powerup powerup, PowerupManager powerupManager) {
		Random random = new Random();
		int x = random.nextInt(1, NUM_HORIZONTAL_TILES - 1);
		int y = random.nextInt(1, NUM_VERTICAL_TILES - 1);
		final int MAX_ATTEMPTS = (NUM_HORIZONTAL_TILES - 2) * (NUM_VERTICAL_TILES - 2);

		for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
			// check the current position
			if (lvlData[y][x] == ' ' && Character.isDigit(lvlData[y + 1][x])
					&& !powerupManager.isTherePowerup(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE)) {
				powerup.setPosition(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
				return;
			}
			// If it is invalid go on
			x++;
			if (x >= NUM_HORIZONTAL_TILES - 1) {
				x = 1;
				y++;
				if (y >= NUM_VERTICAL_TILES - 1) {
					y = 1;
				}
			}
		}
	}
}
