package game.model.enemies;

import static game.model.HelpMethods.isSolidVerticalLine;
import static game.model.HelpMethods.isEntityInsideWall;

import java.util.Random;

import game.model.level.LevelLoader;

public class Zen_chan extends Enemy {

	public Zen_chan(float x, float y, float width, float height) {
		super(x, y, width, height, "Z");
		setxSpeed(0.5f);
		setJumpSpeed(-1.5f);
	}

	public void switchDirection() {
		if (isEntityInsideWall(x, y, width, height))
			return;
		switch (direction) {
		case LEFT -> {
			if (isSolidVerticalLine(x - 1, y, y + height))
				setDirection(Direction.RIGHT);
		}
		case RIGHT -> {
			if (isSolidVerticalLine(x + width + 1, y, y + height))
				setDirection(Direction.LEFT);
		}
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		switchDirection();
		move(0.5f);
		if (new Random().nextInt(0, 500) == 0)
			jump();
	}

}
