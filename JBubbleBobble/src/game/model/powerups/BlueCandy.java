package game.model.powerups;

import game.model.bubbles.PlayerBubble;

public class BlueCandy extends Powerup {
	private static final int POINTS = 100;
	private static final long DURATION = 10;
	private static final float INCREASED_X_SPEED_AMOUNT = 1.3f;
	

	public BlueCandy(float x, float y) {
		super(x, y, "£", POINTS, DURATION);
	}

	@Override
	public void effect() {
		PlayerBubble.setExtraXSpeed(INCREASED_X_SPEED_AMOUNT);
	}

	@Override
	public void resetToNormal() {

	}
}
