package game.model.powerups;

import game.model.Model;

public class Parasol extends Powerup {

	private static final int POINTS = 5000;
	private static final long DURATION = 0;

	public enum Color {
		ORANGE, RED, PURPLE
	}

	private Color color;

	public Parasol(float x, float y, Color color) {
		super(x, y, "@", POINTS, DURATION);
	}

	@Override
	public void effect() {
		switch (color) {
		case ORANGE -> {
			for (int i = 0; i < 3; i++)
				Model.getInstance().nextLevel();
		}
		case PURPLE -> {
			for (int i = 0; i < 5; i++)
				Model.getInstance().nextLevel();
		}
		case RED -> {
			for (int i = 0; i < 7; i++)
				Model.getInstance().nextLevel();
		}
		}
		setX(-200);

	}

	@Override
	public void resetToNormal() {}

}
