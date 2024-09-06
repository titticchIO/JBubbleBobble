package game.model.powerups;

import game.model.Model;
import game.model.bubbles.PlayerBubble;

/**
 * The {@code Clock} temporarily stops all {@code Enemy} in the level from
 * moving
 */
public class Clock extends Powerup {
	public static final char CODE = '*';
	public static final int SPAWN_CONDITION = 1;
	public static final int POINTS = 200;
	public static final long DURATION = 6000;

	/**
	 * Construntor for the Clock
	 */
	public Clock() {
		super(0, 0, CODE, POINTS, DURATION);
	}

	/**
	 * Sets all the enemies in the level to stopped
	 */
	@Override
	public void effect() {
		setX(-200);
		Model.getInstance().getCurrentLevel().getEnemyManager().getEnemies().stream().forEach(x -> x.setStopped(true));
	}

	/**
	 * Makes all the enemies in the level start moving again
	 */
	@Override
	public void resetToNormal() {
		super.resetToNormal();
		Model.getInstance().getCurrentLevel().getEnemyManager().getEnemies().stream().forEach(x -> x.setStopped(false));
		Model.getInstance().getCurrentLevel().getBubbleManager().getPlayerBubbles().stream()
				.filter(PlayerBubble::hasEnemy).forEach(b -> b.getEnemy().setStopped(false));
	}

}
