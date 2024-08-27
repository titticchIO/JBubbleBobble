package game.model.powerups;

import game.model.Model;

public class Clock extends Powerup {
	public static final char CODE = '*';
	private static final int POINTS = 200;
	private static final long DURATION = 6000;
	private static int spawnCondition = 2;

	public Clock() {
		super(0, 0, CODE, POINTS, DURATION);
	}

	public Clock(float x, float y) {
		super(x, y, CODE, POINTS, DURATION);
	}

	@Override
	public void effect() {
		setX(-200);
		Model.getInstance().getCurrentLevel().getEnemyManager().getEnemies().stream().forEach(x -> x.setStopped(true));
	}

	@Override
	public void resetToNormal() {
		Model.getInstance().getCurrentLevel().getEnemyManager().getEnemies().stream().forEach(x -> x.setStopped(false));
	}

	public static int getSpawnCondition() {
		return spawnCondition;
	}

}
