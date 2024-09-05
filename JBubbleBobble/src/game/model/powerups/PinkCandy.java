package game.model.powerups;

import game.model.bubbles.PlayerBubble;

public class PinkCandy extends Powerup {
	public static final char CODE = '!';
	public static final int SPAWN_CONDITION = 30;
	public static final int POINTS = 100;
	public static final long DURATION = 10000;
	public static final float INCREASED_TRAVEL_TIME_AMOUNT = 2;

	public PinkCandy() {
		super(0, 0, CODE, POINTS, DURATION);
	}

	@Override
	public void effect() {
		super.effect();
		PlayerBubble.setExtraTravelTime(INCREASED_TRAVEL_TIME_AMOUNT);
	}

	@Override
	public void resetToNormal() {
		super.resetToNormal();
		PlayerBubble.setExtraTravelTime(1);
	}
}
