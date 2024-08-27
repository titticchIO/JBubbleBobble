package game.model.powerups;

import game.model.bubbles.PlayerBubble;

public class PinkCandy extends Powerup {
	public static final char CODE = '!';
	private static final int POINTS = 100;
	private static final long DURATION = 10000;
	private static final float INCREASED_TRAVEL_TIME_AMOUNT = 2;

	private static int spawnCondition = 2;

	public PinkCandy() {
		super(0, 0, CODE, POINTS, DURATION);
	}

	public PinkCandy(float x, float y) {
		super(x, y, CODE, POINTS, DURATION);
	}

	@Override
	public void effect() {
		super.effect();
		PlayerBubble.setExtraTravelTime(INCREASED_TRAVEL_TIME_AMOUNT);
	}

	@Override
	public void resetToNormal() {
		PlayerBubble.setExtraTravelTime(1);
		super.resetToNormal();
	}

	public static int getSpawnCondition() {
		return spawnCondition;
	}
}
