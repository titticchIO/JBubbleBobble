package game.model;

import java.util.Random;
import game.model.entities.Entity;

/**
 * The {@code Fruit} class represents a collectible item in the game that can be
 * picked up by the player to gain points. Fruits come in various types, each
 * providing a different amount of points.
 */
public class Fruit extends Entity {

	/**
	 * The {@code FruitType} enum defines the types of fruits available in the game,
	 * each represented by a unique character code and a point value.
	 */
	public enum FruitType {
		BANANA('~', 100), PEACH(';', 300), WATERMELON('<', 500), PEAR('>', 1000), ORANGE(':', 2000);

		char code;
		int points;

		/**
		 * Constructs a {@code FruitType} with the specified character code and point
		 * value.
		 *
		 * @param code   the character code representing the fruit.
		 * @param points the point value of the fruit.
		 */
		FruitType(char code, int points) {
			this.code = code;
			this.points = points;
		}
	}

	private int points; // The points awarded for collecting this fruit.

	/**
	 * Constructs a {@code Fruit} at the specified coordinates with the specified
	 * {@code FruitType}.
	 *
	 * @param x         the x-coordinate of the fruit's position.
	 * @param y         the y-coordinate of the fruit's position.
	 * @param fruitType the type of the fruit, which determines its appearance and
	 *                  point value.
	 */
	public Fruit(float x, float y, FruitType fruitType) {
		super(x, y, fruitType.code);
		points = fruitType.points;
	}

	/**
	 * Randomly selects a {@code FruitType} from the available types.
	 *
	 * @return a randomly selected {@code FruitType}.
	 */
	public static FruitType randomFruitType() {
		return switch (new Random().nextInt(5)) {
		case 0 -> FruitType.BANANA;
		case 1 -> FruitType.ORANGE;
		case 2 -> FruitType.PEACH;
		case 3 -> FruitType.PEAR;
		default -> FruitType.WATERMELON;
		};
	}

	/**
	 * Returns the point value of the fruit.
	 *
	 * @return the number of points the fruit is worth.
	 */
	public int getPoints() {
		return points;
	}

}
