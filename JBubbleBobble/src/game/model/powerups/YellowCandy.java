package game.model.powerups;

import game.model.entities.Player;

/**
 * The {@code YellowCandy} increases the attack speed of the player
 */
public class YellowCandy extends Powerup {
	public static final char CODE = '$';
	public static final int SPAWN_CONDITION = 5;
	public static final int POINTS = 100;
	public static final int INCREASED_FIRING_RATE_AMOUNT = 1;
	public static final long DURATION = 10000;

	/**
	 * Constructor for YellowCandy
	 */
	public YellowCandy() {
		super(0, 0, CODE, POINTS, DURATION);
	}

	/**
	 * increases the attack speed of the player
	 */
	@Override
	public void effect() {
		super.effect();
		Player.getInstance().increaseFiringRate(INCREASED_FIRING_RATE_AMOUNT);
	}

	/**
	 * Resets the attack speed of the player back to normal
	 */
	@Override
	public void resetToNormal() {
		super.resetToNormal();
		Player.getInstance().decreaseFiringRate(INCREASED_FIRING_RATE_AMOUNT);
	}
}
