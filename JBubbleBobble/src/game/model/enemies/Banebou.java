package game.model.enemies;

import java.util.Random;

import game.model.HelpMethods;

public class Banebou extends Enemy {

	private long lastChangeTime;
	private long changeInterval;

	public Banebou(float x, float y) {
		super(x, y, "N");
		setxSpeed(0.7f);
		setJumpSpeed(-1.5f);
		setDirection(Direction.RIGHT);
		setColor(Color.NORMAL);
		lastChangeTime = System.currentTimeMillis();
		changeInterval = 8000;
	}

	public Banebou(float x, float y, float width, float height) {
		super(x, y, width, height, "N");
		setxSpeed(0.7f);
		setJumpSpeed(-1.5f);
		setDirection(Direction.RIGHT);
		setColor(Color.NORMAL);
		lastChangeTime = System.currentTimeMillis();
		changeInterval = 8000;
	}

	private void changeDirection() {
		changeInterval = new Random().nextLong(8000, 10000);
		switch (direction) {
		case Direction.LEFT:
			setDirection(Direction.RIGHT);
			setxSpeed(0.7f);
			break;
		case Direction.RIGHT:
			setDirection(Direction.LEFT);
			setxSpeed(-0.7f);
			break;
		default:
			break;
		}
	}

	public void checkAndChangeDirection() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastChangeTime > changeInterval) {
			changeDirection();
			lastChangeTime = currentTime;
		}
	}

	@Override
	public void updateEntity() {
		if (!isStopped) {
			super.updateEntity();
			if ((!HelpMethods.canMoveHere(x + xSpeed, y, width, height) || randomBoolean(1000))
					&& !HelpMethods.isEntityInsideWall(x, y, width, height))
				changeDirection();
			jump();
		}
	}

}