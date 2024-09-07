package game.model.powerups;

import game.model.bubbles.PlayerBubble;

/**
 * The {@code BLueCandy} makes the {@code PlayerBubble} travel faster
 */
public class BlueCandy extends Powerup {
	public static final char CODE = '£';
	public static final int SPAWN_CONDITION = 15;
	public static final int POINTS = 100;
	public static final long DURATION = 10000;
	public static final float INCREASED_X_SPEED_AMOUNT = 2;

	/*
	 * Constructor for BlueCandy
	 */
	public BlueCandy() {
		super(0, 0, CODE, POINTS, DURATION);
	}

	/**
	 * Increases the speed of the playerBubbles
	 */
	@Override
	public void effect() {
		super.effect();
		PlayerBubble.setExtraXSpeed(INCREASED_X_SPEED_AMOUNT);
	}

	/**
	 * Resets the speed of the playerBubbles back to normal
	 */
	@Override
	public void resetToNormal() {
		super.resetToNormal();
		PlayerBubble.setExtraXSpeed(1);
	}
}
