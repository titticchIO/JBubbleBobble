package game.model.powerups;

import game.model.entities.Player;

public class Skeleton extends Powerup {
	private static final int POINTS = 500;
	private static final long DURATION = 8000;
	private static int spawnCondition=2;

	public Skeleton() {
		super(0, 0, "%", POINTS, DURATION);

	}

	public Skeleton(float x, float y) {
		super(x, y, "%", POINTS, DURATION);
	}

	@Override
	public void effect() {
		super.effect();
		Player.getInstance().setShooting(true);
	}

	@Override
	public void resetToNormal() {
		Player.getInstance().setShooting(false);
		super.resetToNormal();
	}

	public static int getSpawnCondition() {
		return spawnCondition;
	}
}
