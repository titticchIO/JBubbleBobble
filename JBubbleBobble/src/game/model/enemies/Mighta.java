package game.model.enemies;

import static game.model.HelpMethods.isSolid;

import utils.LevelLoader;

public class Mighta extends Enemy {

	public Mighta(float x, float y, float width, float height, String imageCode) {
		super(x, y, width, height, imageCode);
		setxSpeed(0.5f);
		setxSpeed(0.25f);
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
	
	
	public void throwStone() {
		// implementare il comportamento con cui Maita lancia la roccia (che implementaremo come una bolla)
	}

}
