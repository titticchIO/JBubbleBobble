package model.enemies;

import static model.HelpMethods.isSolid;

import model.level.LevelLoader;

public class Maita extends Enemy {

	public Maita(float x, float y, float width, float height) {
		super(x, y, width, height, "Maita");
		setxSpeed(0.5f);
		setxSpeed(0.25f);
	}

	public boolean isOnEdge() {
		return !isSolid(x - 1, y + height + 1, LevelLoader.getLevelData())
				|| !isSolid(x + width + 1, y + height + 1, LevelLoader.getLevelData());
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
	
	
	public void throwStone() {
		// implementare il comportamento con cui Maita lancia la roccia (che implementaremo come una bolla)
	}

}
