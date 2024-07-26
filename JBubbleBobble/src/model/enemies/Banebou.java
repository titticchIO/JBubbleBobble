package model.enemies;

import static model.HelpMethods.isSolid;

import model.level.LevelLoader;

public class Banebou extends Enemy {

	public Banebou(float x, float y, float width, float height) {
		super(x, y, width, height, "Banebou");
		setxSpeed(1);
//		setAirSpeed(1);
	}

	public void switchDirection() {
		if (!isSolid(x - 1, y + height + 1, LevelLoader.getLevelData())) {
			setxSpeed(1);
		}
		if (!isSolid(x + width + 1, y + height + 1, LevelLoader.getLevelData())) {
			setxSpeed(-1);
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		switchDirection();
	}

}
