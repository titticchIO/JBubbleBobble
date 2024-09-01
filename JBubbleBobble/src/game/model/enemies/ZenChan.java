package game.model.enemies;

import static game.model.HelpMethods.isSolidVerticalLine;
import static game.model.HelpMethods.isEntityInsideWall;

public class ZenChan extends Enemy {
	public static final char CODE = 'Z';

	public ZenChan(float x, float y) {
		super(x, y, CODE);
		setDirection(Direction.RIGHT);
		setJumpSpeed(-1.5f);
		setColorState(ColorState.NORMAL);
	}

	private void switchDirection() {
		if (isEntityInsideWall(this))
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
		updateYPos();
		if (isDead()) {
			removeEnemy();
		} else {
			updateXPos();
			gravity();
			if (!isStopped) {
				switchDirection();
				move(0.4f * movementSpeed);
				if (randomBoolean(600))
					jump();
			}
		}
	}

}
