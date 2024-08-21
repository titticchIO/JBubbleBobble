package game.model.enemies;

import static game.model.HelpMethods.isSolidVerticalLine;
import static game.model.HelpMethods.isEntityInsideWall;

import java.util.Random;

import game.model.Model;
import game.model.entities.MovingEntity.Color;
import game.model.level.LevelLoader;

public class ZenChan extends Enemy {
	
	
	
	public ZenChan(float x, float y) {
		super(x, y, "Z");
		setxSpeed(0.5f);
		setDirection(Direction.RIGHT);
		setJumpSpeed(-1.5f);
		setColor(Color.NORMAL);
	}
	public ZenChan(float x, float y, float width, float height) {
		super(x, y, width, height, "Z");
		setxSpeed(0.5f);
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
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		switchDirection();
		move(0.5f);
		if (randomBoolean(1000))
			jump();
	}

}
