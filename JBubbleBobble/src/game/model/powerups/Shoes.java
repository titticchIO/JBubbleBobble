package game.model.powerups;

import game.model.Model;
import game.model.level.Level;

/**
 * The {@code Shoes} are a {@link Powerup} that temporarily enhance the player's
 * movement speed
 */
public class Shoes extends Powerup {
	public static final char CODE = '§';
	public static final int SPAWN_CONDITION = 25 * Level.GAME_WIDTH;
	public static final int POINTS = 100;
	public static final long DURATION = 10000;
	public static final float INCREASED_X_SPEED_AMOUNT = 2f;

	/**
	 * Constructor for Shoes
	 */
	public Shoes() {
		super(0, 0, CODE, POINTS, DURATION);
	}

	/**
	 * Increases the player's movement speed
	 */
	@Override
	public void effect() {
		setX(-200);
		Model.getInstance().getCurrentLevel().getPlayer().setExtraXSpeed(INCREASED_X_SPEED_AMOUNT);
	}

	/**
	 * Resets the player's movement speed
	 */
	@Override
	public void resetToNormal() {
		super.resetToNormal();
		Model.getInstance().getCurrentLevel().getPlayer().setExtraXSpeed(1);
	}
}
