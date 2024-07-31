package game.model.enemies;

import static game.model.HelpMethods.isSolid;

import utils.LevelLoader;

public class Pulpul extends Enemy {

	public Pulpul(float x, float y, float width, float height) {
		super(x, y, width, height, "Pulpul");
		setxSpeed(1);
		setAirSpeed(1);
	}

	public boolean isOnEdge() {
		return !isSolid(x - 1, y + height + 1)
				|| !isSolid(x + width + 1, y + height + 1);
	}

	public void switchDirection() {
		if (isOnEdge()) {
			setxSpeed(getxSpeed() * -1);
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		switchDirection();
	}

}
