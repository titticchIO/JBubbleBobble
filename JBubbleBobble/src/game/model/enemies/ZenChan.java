package game.model.enemies;

import static game.model.HelpMethods.isSolidVerticalLine;
import static game.model.HelpMethods.isEntityInsideWall;

public class ZenChan extends Enemy {
	public static final char CODE = 'Z';

	public ZenChan(float x, float y) {
		super(x, y, CODE);
		setDirection(Direction.RIGHT);
		setJumpSpeed(-1.5f);
		setColor(Color.NORMAL);
	}

	public ZenChan(float x, float y, float width, float height) {
		super(x, y, width, height, CODE);
		setDirection(Direction.RIGHT);
		setJumpSpeed(-1.5f);
		setColor(Color.NORMAL);
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
		default -> throw new IllegalArgumentException("Unexpected value: " + direction);
		}
	}

	@Override
	public void updateEntity() {
		if (!isStopped) {
			super.updateEntity();
			switchDirection();
			move(0.4f);
			if (randomBoolean(1000))
				jump();
		}
	}

}
