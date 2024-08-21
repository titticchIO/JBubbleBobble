package game.model.powerups;

import game.model.Model;

public class YellowCandy extends Powerup{
	private static final int POINTS = 100;
	private static final long DURATION = 10;
	private static final int INCREASED_FIRING_RATE_AMOUNT = 1;

	public YellowCandy(float x, float y) {
		super(x, y, "$", POINTS, DURATION);
	}

	@Override
	public void effect() {
		Model.getInstance().getCurrentLevel().getPlayer().increaseFiringRate(INCREASED_FIRING_RATE_AMOUNT);
	}

	@Override
	public void resetToNormal() {
		Model.getInstance().getCurrentLevel().getPlayer().decreaseFiringRate(INCREASED_FIRING_RATE_AMOUNT);
	}


}
