package game.model.powerups;

import game.model.entities.Player;

public class Pistol extends Powerup {
	private static final int POINTS = 500;
	private static final long DURATION = 8000;
	private static int spawnCondition;

	public Pistol() {
		super(0, 0, "%", POINTS, DURATION);
		spawnCondition = 2;
	}

	public Pistol(float x, float y) {
		super(x, y, "%", POINTS, DURATION);
		spawnCondition = 2;
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
