package game.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The {@code FruitManager} keeps track of all the {@code Fruit} contained in a
 * {@code Level}
 */
public class FruitManager {
	private List<Fruit> fruits;

	/**
	 * Constructor for the FruitManager
	 */
	public FruitManager() {
		fruits = new CopyOnWriteArrayList<>();
	}

	/**
	 * Returns the list of fruits.
	 *
	 * @return the list of fruits.
	 */
	public List<Fruit> getFruits() {
		return fruits;
	}

	/**
	 * Adds a fruit to the list of fruits.
	 *
	 * @param fruit the fruit to be added.
	 */
	public void addFruit(Fruit fruit) {
		fruits.add(fruit);
	}

	/**
	 * Removes a fruit from the list of fruits.
	 *
	 * @param fruit the fruit to be removed.
	 */
	public void removeFruit(Fruit fruit) {
		fruits.remove(fruit);
	}

}
