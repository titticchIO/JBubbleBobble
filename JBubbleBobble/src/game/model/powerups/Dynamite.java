package game.model.powerups;

import game.model.Model;

public class Dynamite extends Powerup{
	
	private static final int POINTS = 200;
	private static final long DURATION = 1;
	private static int spawnCondition = 2;

	public Dynamite() {
		super(0, 0, "^", POINTS, DURATION);
	}

	public Dynamite(float x, float y) {
		super(x, y, "^", POINTS, DURATION);
	}
	
	@Override
	public void effect() {
		setX(-200);
		Model.getInstance().getCurrentLevel().getEnemyManager().removeAllEnemies();	
	}

	@Override
	public void resetToNormal() {
		
	}

	public static int getSpawnCondition() {
		return spawnCondition;
	}
	
}
