package game.model.powerups;

import game.model.Model;
import game.model.entities.Player;

public class YellowCandy extends Powerup {
	public static final char CODE = '$';
	public static final int SPAWN_CONDITION = 20;
	public static final int POINTS = 100;
	public static final int INCREASED_FIRING_RATE_AMOUNT = 1;
	public static final long DURATION = 10000;

	public YellowCandy() {
		super(0, 0, CODE, POINTS, DURATION);
	}

	public YellowCandy(float x, float y) {
		super(x, y, CODE, POINTS, DURATION);
	}

	@Override
	public void effect() {
		super.effect();
		Player.getInstance().increaseFiringRate(INCREASED_FIRING_RATE_AMOUNT);
	}

	@Override
	public void resetToNormal() {
		Player.getInstance().decreaseFiringRate(INCREASED_FIRING_RATE_AMOUNT);
		super.resetToNormal();
	}

}
