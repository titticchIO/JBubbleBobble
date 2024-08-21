package game.model.powerups;

public class BlueCandy extends Powerup {
	private static final int POINTS = 100;
	private static final long DURATION = 10;

	public BlueCandy(float x, float y) {
		super(x, y, "£", POINTS, DURATION);
	}

	@Override
	public void effect() {

	}

	@Override
	public void resetToNormal() {

	}
}
