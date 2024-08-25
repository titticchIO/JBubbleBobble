package game.model.powerups;

import game.model.Model;
import game.model.entities.Player;

public class YellowCandy extends Powerup {
	private static final int POINTS = 100;
	private static final long DURATION = 10000;
	private static final int INCREASED_FIRING_RATE_AMOUNT = 1;
	private static int spawnCondition = 2;

	public YellowCandy() {
		super(0, 0, "$", POINTS, DURATION);
	}

	public YellowCandy(float x, float y) {
		super(x, y, "$", POINTS, DURATION);
	}

	@Override
	public void effect() {
		super.effect();
		Player.getInstance().increaseFiringRate(INCREASED_FIRING_RATE_AMOUNT);
	}

	@Override
	public void resetToNormal() {
		Player.getInstance().decreaseFiringRate(INCREASED_FIRING_RATE_AMOUNT);
		Model.getInstance().getCurrentLevel().spawnPowerup(new AmethystRing());
		super.resetToNormal();
	}

	public static int getSpawnCondition() {
		return spawnCondition;
	}

}
