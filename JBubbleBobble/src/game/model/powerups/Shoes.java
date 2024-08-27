package game.model.powerups;

import game.model.Model;
import game.model.level.Level;

public class Shoes extends Powerup {
	public static final char CODE = '§';
	private static final int POINTS = 100;
	private static final long DURATION = 10000;
	private static final float INCREASED_X_SPEED_AMOUNT = 2f;
	private static float spawnCondition = 2 * Level.GAME_WIDTH;

	public Shoes() {
		super(0, 0, CODE, POINTS, DURATION);
	}

	public Shoes(float x, float y) {
		super(x, y, CODE, POINTS, DURATION);
	}

	@Override
	public void effect() {
		setX(-200);
		Model.getInstance().getCurrentLevel().getPlayer().setExtraXSpeed(INCREASED_X_SPEED_AMOUNT);
	}

	@Override
	public void resetToNormal() {
		Model.getInstance().getCurrentLevel().getPlayer().setExtraXSpeed(1);
	}

	public static float getSpawnCondition() {
		return spawnCondition;
	}
}
