package game.model.powerups;

import game.model.Model;
import game.model.entities.Player;

/**
 * The {@code AmethystRing} makes the player earn 100 points for each jump.
 */
public class AmethystRing extends Powerup {
	public static final char CODE = '=';
	public static final int SPAWN_CONDITION = 2;
	public static final int POINTS = 1000;
	public static final long DURATION = 8000;

	/**
	 * Constructor for AmethystRing-
	 */
	public AmethystRing() {
		super(0, 0, CODE, POINTS, DURATION);
	}

	/**
	 * Triggers the effect
	 */
	@Override
	public void effect() {
		super.effect();
		Model.getInstance().getCurrentUser().addPoints(POINTS);
		Player.getInstance().setAmethystRingActive(true);
	}

	/**
	 * Cancels the effect
	 */
	@Override
	public void resetToNormal() {
		super.resetToNormal();
		Player.getInstance().setAmethystRingActive(false);
	}

}
