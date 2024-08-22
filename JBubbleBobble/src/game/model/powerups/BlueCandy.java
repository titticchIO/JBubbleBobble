package game.model.powerups;

import game.model.bubbles.PlayerBubble;

public class BlueCandy extends Powerup {
	private static final int POINTS = 100;
	private static final long DURATION = 10000;
	private static final float INCREASED_X_SPEED_AMOUNT = 2;
	

	public BlueCandy(float x, float y) {
		super(x, y, "£", POINTS, DURATION);
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
