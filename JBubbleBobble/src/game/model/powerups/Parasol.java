package game.model.powerups;

import game.model.Model;

public abstract class Parasol extends Powerup {

	private static final int POINTS = 5000;
	private static final long DURATION = 0;
	private int levelsToSkip;

	public Parasol(float x, float y, int levelsToSkip) {
		super(x, y, "@", POINTS, DURATION);
		this.levelsToSkip = levelsToSkip;
	}

	@Override
	public void effect() {
		for (int i = 0; i < levelsToSkip; i++) {
			Model.getInstance().nextLevel();
			System.out.println("skip level");
		}

		setX(-200);

	}

	@Override
	public void resetToNormal() {
	}

}
