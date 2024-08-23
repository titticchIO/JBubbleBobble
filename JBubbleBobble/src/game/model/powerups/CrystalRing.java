package game.model.powerups;

import game.model.Model;

public class CrystalRing extends Powerup {
	
	private static final int POINTS = 1000;
	private static final long DURATION = 3000;
	private static int spawnCondition = 2;

	public CrystalRing() {
		super(0, 0, "&", POINTS, DURATION);
	}

	public CrystalRing(float x, float y) {
		super(x, y, "&", POINTS, DURATION);
	}
	
	@Override
	public void effect() {
		setX(-200);
		Model.getInstance().getCurrentUser().addPoints(POINTS);
		Model.getInstance().getCurrentLevel().getPlayer().setCrystalRingActive(true);
		
	}

	@Override
	public void resetToNormal() {		
		Model.getInstance().getCurrentLevel().getPlayer().setCrystalRingActive(false);
	}

	public static int getSpawnCondition() {
		return spawnCondition;
	}


}
