package game.model.powerups;

import game.model.Model;
import game.model.enemies.Enemy;

/**
 * The {@code Dynamite} kills all the {@code Enemy} in the level
 */
public class Dynamite extends Powerup {
	public static final char CODE = '^';
	public static final int SPAWN_CONDITION = 1;
	public static final int POINTS = 200;
	public static final long DURATION = 1;

	/**
	 * Constructor for the Dynamite
	 */
	public Dynamite() {
		super(0, 0, CODE, POINTS, DURATION);
	}

	/**
	 * Kills all the enemies
	 */
	@Override
	public void effect() {
		setX(-200);
		Model.getInstance().getCurrentLevel().getEnemyManager().getEnemies().stream().forEach(Enemy::kill);
	}

}
