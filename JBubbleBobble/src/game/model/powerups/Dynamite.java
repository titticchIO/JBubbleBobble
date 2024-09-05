package game.model.powerups;

import game.model.Model;
import game.model.enemies.Enemy;

public class Dynamite extends Powerup{
	public static final char CODE = '^';
	public static final int SPAWN_CONDITION = 1;
	public static final int POINTS = 200;
	public static final long DURATION = 1;

	public Dynamite() {
		super(0, 0, CODE, POINTS, DURATION);
	}

	
	@Override
	public void effect() {
		setX(-200);
		Model.getInstance().getCurrentLevel().getEnemyManager().getEnemies().stream().forEach(Enemy::kill);	
	}
	
}
