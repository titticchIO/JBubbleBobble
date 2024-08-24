package game.model.enemies;

import static game.model.HelpMethods.isEntityInsideWall;
import static game.model.HelpMethods.isSolidVerticalLine;
import game.model.HelpMethods;
import game.model.Model;
import java.util.Timer;
import java.util.TimerTask;

public class Invader extends Enemy {

	public static final long ATTACK_INTERVAL = 1000;

	private boolean landed;
	private boolean startedShooting;

	public Invader(float x, float y, float width, float height) {
		super(x, y, width, height, "I");
	}

	public Invader(float x, float y) {
		super(x, y, "I");
		setAirSpeed(1);
		setDirection(Direction.LEFT);
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

	public void randomizeDirection() {
		if (randomBoolean(2)) {
			setDirection(Direction.RIGHT);
		} else {
			setDirection(Direction.LEFT);
		}
	}

	private void shootLaser() {
		if (randomBoolean(3)) {
			Model.getInstance().getCurrentLevel().getEnemyManager().addLaser(new Laser(x + 5, y + height, 6, 20));
		}
		new Timer("Invader Laser").schedule(new TimerTask() {

			@Override
			public void run() {
				shootLaser();
			}
		}, ATTACK_INTERVAL);

	}

	@Override
	public void updateEntity() {
		if (!startedShooting) {
			shootLaser();
			startedShooting = true;
		}
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
			move(0.5f);
			updateYPos();
		}
	}

}
