package game.model.enemies;

import static game.model.HelpMethods.isSolid;

import game.model.level.LevelLoader;

public class Zen_chan extends Enemy {
	
	private final String type = "Z"; 

	public Zen_chan(float x, float y, float width, float height, String positionCode) {
		super(x, y, width, height, positionCode);
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

	@Override
	public String getType() {
		return type;
	}

}
