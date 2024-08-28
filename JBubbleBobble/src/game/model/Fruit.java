package game.model;

import game.model.entities.Entity;


public class Fruit extends Entity {

	public enum FruitType {
		BANANA('~', 100), PEACH(';', 300), WATERMELON('<', 500), PEAR('>', 1000), ORANGE(':', 2000);

		char code;
		int points;

		FruitType(char code, int points) {
			this.code = code;
			this.points = points;
		}
	}

	int points;

	public Fruit(float x, float y, FruitType fruitType) {
		super(x, y, fruitType.code);
		points = fruitType.points;
	}

	public int getPoints() {
		return points;
	}

}
