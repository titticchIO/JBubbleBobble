package game.model.powerups;

import game.model.Model;

public class Parasol extends Powerup {

	private static final int POINTS = 5000;
	private static final long DURATION = 0;

	public enum Color {
		ORANGE, PURPLE, RED
	}

	private Color color;

	public Parasol(float x, float y, Color color) {
		super(x, y, "@", POINTS, DURATION);
		this.color = color;
		spawnCondition = switch (color) {
		case ORANGE -> 2;
		case PURPLE -> 5;
		case RED -> 10;
		};
	}

	@Override
	public void effect() {
		int times = switch (color) {
		case ORANGE -> 3;
		case PURPLE -> 5;
		case RED -> 7;
		};

		for (int i = 0; i < times; i++) {
			Model.getInstance().nextLevel();
			System.out.println("skip level");
		}

		setX(-200);

	}

	@Override
	public void resetToNormal() {
	}

	public Color getColor() {
		return color;
	}

}
