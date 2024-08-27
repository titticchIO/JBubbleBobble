package game.model.enemies;

import static game.model.HelpMethods.isEntityInsideWall;
import static game.model.HelpMethods.isSolidVerticalLine;
import game.model.HelpMethods;
import game.model.Model;

public class Invader extends Enemy {

	public static final char CODE = 'I';
	public static final long ATTACK_INTERVAL = 1000;

	private boolean landed;

	public Invader(float x, float y, float width, float height) {
		super(x, y, width, height, CODE);
	}

	public Invader(float x, float y) {
		super(x, y, CODE);
		setAirSpeed(1);
		setDirection(Direction.LEFT);
		setColorState(ColorState.NORMAL);
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

	public void randomizeDirection() {
		if (randomBoolean(2)) {
			setDirection(Direction.RIGHT);
		} else {
			setDirection(Direction.LEFT);
		}
	}

	public void shootLaser() {
		if (randomBoolean(10)) 
			Model.getInstance().getCurrentLevel().getEnemyManager().addLaser(new Laser(x + 5, y + height, 6, 20));
	}

	@Override
	public void updateEntity() {
		if (!isStopped) {
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
			move(0.5f * movementSpeed);
			updateYPos();
		}
	}

}
