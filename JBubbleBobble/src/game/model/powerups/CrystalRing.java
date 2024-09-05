package game.model.powerups;

import game.model.Model;

public class CrystalRing extends Powerup {
	public static final char CODE = '&';
	public static final int SPAWN_CONDITION = 2;
	public static final int POINTS = 1000;
	public static final long DURATION = 3000;

	public CrystalRing() {
		super(0, 0, CODE, POINTS, DURATION);
	}


	@Override
	public void effect() {
		setX(-200);
		Model.getInstance().getCurrentUser().addPoints(POINTS);
		Model.getInstance().getCurrentLevel().getPlayer().setCrystalRingActive(true);

	}

	@Override
	public void resetToNormal() {
		super.resetToNormal();
		Model.getInstance().getCurrentLevel().getPlayer().setCrystalRingActive(false);
	}

}
