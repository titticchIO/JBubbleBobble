package game.model.powerups;

import game.model.Model;
import game.model.entities.Player;

public class AmethystRing extends Powerup {
	public static final char CODE = '=';
	public static final int POINTS = 500;
	public static final long DURATION = 8000;
	private static int spawnCondition=2;

	public AmethystRing() {
		super(0, 0, CODE, POINTS, DURATION);
	}

	public AmethystRing(float x, float y) {
		super(x, y, CODE, POINTS, DURATION);
	}

	@Override
	public void effect() {
		super.effect();
		Model.getInstance().getCurrentUser().addPoints(POINTS);
		Player.getInstance().setAmethystRingActive(true);
	}

	@Override
	public void resetToNormal() {
		Player.getInstance().setAmethystRingActive(false);
		super.resetToNormal();
	}

	public static int getSpawnCondition() {
		return spawnCondition;
	}
}
