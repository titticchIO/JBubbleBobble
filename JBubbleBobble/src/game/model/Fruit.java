package game.model;

import java.util.Random;

import game.model.Fruit.FruitType;
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

	public static FruitType randomFruitType() {
		return switch (new Random().nextInt(5)) {
		case 0 -> FruitType.BANANA;
		case 1 -> FruitType.ORANGE;
		case 2 -> FruitType.PEACH;
		case 3 -> FruitType.PEAR;
		default -> FruitType.WATERMELON;
		};
}

	
	public int getPoints() {
		return points;
	}

}
