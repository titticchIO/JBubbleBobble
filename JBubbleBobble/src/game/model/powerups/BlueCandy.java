package game.model.powerups;

import game.model.bubbles.PlayerBubble;

public class BlueCandy extends Powerup {
	public static final char CODE = '£';
	public static final int SPAWN_CONDITION = 20;
	public static final int POINTS = 100;
	public static final long DURATION = 10000;
	public static final float INCREASED_X_SPEED_AMOUNT = 2;

	public BlueCandy() {
		super(0, 0, CODE, POINTS, DURATION);
	}

	@Override
	public void effect() {
		super.effect();
		PlayerBubble.setExtraXSpeed(INCREASED_X_SPEED_AMOUNT);
	}

	@Override
	public void resetToNormal() {
		PlayerBubble.setExtraXSpeed(1);
		super.resetToNormal();
	}
}
