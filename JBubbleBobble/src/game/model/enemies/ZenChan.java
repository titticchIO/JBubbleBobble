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
		setDirection(Direction.RIGHT);
		setJumpSpeed(-1.5f);
		setColor(Color.NORMAL);
	}
	public ZenChan(float x, float y, float width, float height) {
		super(x, y, width, height, "Z");
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
		if(!isStopped) {
			super.updateEntity();
			switchDirection();
			move(0.4f);
			if (randomBoolean(1000))
				jump();
		}
	}

}
