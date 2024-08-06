package game.model.enemies;

import static game.model.HelpMethods.isSolid;

import utils.LevelLoader;

public class Zen_chan extends Enemy {

	public Zen_chan(float x, float y, float width, float height, String imageCode) {
		super(x, y, width, height, imageCode);
		setxSpeed(0.5f);
	}

	public void switchDirection() {
		if (!isSolid(x - 1, y + height + 1)) {
			setxSpeed(0.5f);
		} else if (!isSolid(x + width + 1, y + height + 1)) {
			setxSpeed(-0.5f);
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		switchDirection();
	}

}
