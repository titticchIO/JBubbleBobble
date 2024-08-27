package game.model.enemies;

import game.model.Model;
import game.model.entities.MovingEntity;
import game.model.level.Level;

public class Laser extends MovingEntity {
	public static final char CODE = '"';

	public Laser(float x, float y, float width, float height) {
		super(x, y, width, height, CODE);
		setAirSpeed(2);
	}

	@Override
	protected void updateYPos() {
		setY(y + airSpeed);
	}

	@Override
	public void updateEntity() {
		updateYPos();
		if (y > Level.GAME_HEIGHT)
			Model.getInstance().getCurrentLevel().getEnemyManager().removeLaser(this);
	}

}
