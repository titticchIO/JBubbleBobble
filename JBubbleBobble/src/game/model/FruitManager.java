package game.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FruitManager {
	List<Fruit> fruits;

	public FruitManager() {
		fruits = new CopyOnWriteArrayList<>();
	}

	public List<Fruit> getFruits() {
		return fruits;
	}

	public void addFruit(Fruit fruit) {
		fruits.add(fruit);
	}

	public void removeFruit(Fruit fruit) {
		fruits.remove(fruit);
	}

}
