package game.model.enemies;

import static game.model.HelpMethods.isEntityInsideWall;
import static game.model.HelpMethods.isSolidVerticalLine;

import game.model.HelpMethods;
import game.model.Model;
import game.model.entities.MovingEntity.Direction;

public class Invader extends Enemy {


	private State state;
	private boolean landed;

	public enum State {
		WALK, SHOOT
	}

	public Invader(float x, float y, float width, float height) {
		super(x, y, width, height, "I");
	}
	
	
	public Invader(float x, float y) {
		super(x, y, "I");
		state = State.WALK;
		setAirSpeed(1);
		setDirection(Direction.LEFT);
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

	public void randomizeDirection() {
		if (randomBoolean(2)) {
			setDirection(Direction.RIGHT);
		} else {
			setDirection(Direction.LEFT);
		}
	}
	
	private void shootLaser() {
		Model.getInstance().getCurrentLevel().getEnemyManager().addLaser(new Laser(x+4, y+height, 8, 20));
	}

	@Override
	public void updateEntity() {
		if (!HelpMethods.isEntityGrounded(this) && landed)
			landed = false;
		if (HelpMethods.isEntityGrounded(this) && !landed) {
			landed = true;
			randomizeDirection();
		}
		switchDirection();

		if (!inAir) {
			updateXPos();
		} else
			setAirSpeed(0.5f);
		move(0.5f);
		updateYPos();
		if (randomBoolean(300))
			shootLaser();
	}




}
