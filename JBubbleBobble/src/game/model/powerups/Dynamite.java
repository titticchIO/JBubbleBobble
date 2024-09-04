package game.model.powerups;

import game.model.Model;

public class Dynamite extends Powerup{
	public static final char CODE = '^';
	public static final int SPAWN_CONDITION = 3;
	public static final int POINTS = 200;
	public static final long DURATION = 1;

	public Dynamite() {
		super(0, 0, CODE, POINTS, DURATION);
	}

	
	@Override
	public void effect() {
		setX(-200);
		Model.getInstance().getCurrentLevel().getEnemyManager().removeAllEnemies();	
	}

	@Override
	public void resetToNormal() {
		
	}
	
}
