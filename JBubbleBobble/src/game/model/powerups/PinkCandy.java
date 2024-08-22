package game.model.powerups;

import game.model.bubbles.PlayerBubble;

public class PinkCandy extends Powerup{
	private static final int POINTS = 100;
	private static final long DURATION = 10;
	private static final float INCREASED_TRAVEL_TIME_AMOUNT = 2;


	public PinkCandy(float x, float y) {
		super(x, y, "!", POINTS, DURATION);
	}

	@Override
	public void effect() {
		super.effect();
		PlayerBubble.setExtraTravelTime(INCREASED_TRAVEL_TIME_AMOUNT);
	}

	@Override
	public void resetToNormal() {
		PlayerBubble.setExtraTravelTime(0);
		super.resetToNormal();
	}
}
