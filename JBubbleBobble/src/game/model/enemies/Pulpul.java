
package game.model.enemies;

import java.util.Random;

import game.model.HelpMethods;
import game.model.Settings;

import static game.model.HelpMethods.isSolid;

public class Pulpul extends Enemy {

	private long lastChangeTime;
	private static final long CHANGE_INTERVAL = 2000; // Intervallo di cambio direzione in millisecondi

	public Pulpul(float x, float y, float width, float height) {
		super(x, y, width, height, "U");
		setxSpeed(0.7f);
		setAirSpeed(0.7f);
		lastChangeTime = System.currentTimeMillis();
	}

	private void changeDirection() {
		switch (direction) {
		case Direction.LEFT:
			direction = Direction.RIGHT;
			setxSpeed(0.7f);
			setAirSpeed(0);
			break;
		case Direction.RIGHT:
			direction = Direction.LEFT;
			setxSpeed(-0.7f);
			setAirSpeed(0);
			break;
		case Direction.UP:
			direction = Direction.DOWN;
			setAirSpeed(0.7f);
			setxSpeed(0);
			break;
		case Direction.DOWN:
			direction = Direction.UP;
			setAirSpeed(-0.7f);
			setxSpeed(0);
			break;
		}
	}

	private void randomizeDirection() {
		Random random = new Random();
		int randomInt = random.nextInt(4);
		switch (randomInt) {
		case 0:
			direction = Direction.UP;
			setAirSpeed(-0.7f);
			setxSpeed(0);
			break;
		case 1:
			direction = Direction.DOWN;
			setAirSpeed(0.7f);
			setxSpeed(0);
			break;
		case 2:
			direction = Direction.LEFT;
			setxSpeed(-0.7f);
			setAirSpeed(0);
			break;
		case 3:
			direction = Direction.RIGHT;
			setxSpeed(0.7f);
			setAirSpeed(0);
			break;
		}
	}

	private void checkAndChangeDirection() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastChangeTime > CHANGE_INTERVAL) {
			randomizeDirection();
			lastChangeTime = currentTime;
		}
	}

	@Override
	public void updateEntity() {
		checkAndChangeDirection(); // Verifica se deve cambiare direzione
		// Aggiornamento della posizione basato sulla direzione corrente
		if (direction == Direction.LEFT || direction == Direction.RIGHT)
			updateXPos();
		else
			updateYPos();
		setChanged();
		notifyObservers();
	}

	@Override
	public void updateXPos() {
		if (HelpMethods.canMoveHere(x + xSpeed, y, width, height)) {
			setX(x + xSpeed);
		} else {
			// Cambia direzione se incontra un ostacolo
			changeDirection();
		}
	}

	@Override
	public void updateYPos() {
		if (y > Settings.GAME_HEIGHT + 1) {
			setY(-1);
		} else if (y < -2) {
			setY(Settings.GAME_HEIGHT);
		} else {
			if (HelpMethods.canMoveHere(x, y + airSpeed, width, height)) {
				setY(y + airSpeed);
			} else {

				// Cambia direzione se incontra un ostacolo
				changeDirection();
			}
		}
	}
}