package game.model.powerups;

import game.model.Model;

public class PinkCandy extends Powerup{
	private static final int POINTS = 100;
	private static final long DURATION = 10;
	
	
	public PinkCandy(float x, float y) {
		super(x, y, "!", POINTS, DURATION);
	}

	@Override
	public void effect() {
		
	}

	@Override
	public void resetToNormal() {
		
	}
}
