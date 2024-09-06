package game.model.powerups;

import game.model.Model;

/**
 * The {@code Parasol} item allows the player to skip levels
 */
public abstract class Parasol extends Powerup {
	public static final char CODE = '@';
	private static final int POINTS = 5000;
	private static final long DURATION = 0;
	private int levelsToSkip;

	/**
	 * Constructor for the Parasol
	 * 
	 * @param x
	 * @param y
	 * @param levelsToSkip
	 */
	public Parasol(float x, float y, int levelsToSkip) {
		super(x, y, CODE, POINTS, DURATION);
		this.levelsToSkip = levelsToSkip;
	}

	/**
	 * Skips levelsToSkip levels
	 */
	@Override
	public void effect() {
		for (int i = 0; i < levelsToSkip; i++) {
			try {
				Model.getInstance().nextLevel();
			} catch (Exception e) {
				Model.getInstance().setWin();
				break;
			}
		}

		setX(-200);

	}

}
