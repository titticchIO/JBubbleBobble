package game.model.enemies;

import static game.model.HelpMethods.isEntityInsideWall;
import static game.model.HelpMethods.isSolidVerticalLine;
import game.model.HelpMethods;
import game.model.Model;
import java.util.Timer;
import java.util.TimerTask;

public class Invader extends Enemy {

	public static final long ATTACK_INTERVAL = 2000;

	private State state;
	private boolean landed;
	private Timer attackTimer;
	private boolean canShoot;

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
		setColor(Color.NORMAL);
		canShoot=true;
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
		if (canShoot && randomBoolean(3) ) {
			Model.getInstance().getCurrentLevel().getEnemyManager().addLaser(new Laser(x + 5, y + height, 6, 20));
			canShoot = false;
			if (attackTimer != null) {
				attackTimer.cancel();
			}
			// Crea un nuovo Timer per l'attacco
			attackTimer = new Timer();
			attackTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					// Dopo attackSpeed millisecondi, il player può sparare di nuovo
					canShoot = true;
					attackTimer.cancel(); // Ferma il timer una volta completato
				}
			}, ATTACK_INTERVAL); // Imposta il timer in base alla velocità di attacco

		}

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
		shootLaser();
	}

}
