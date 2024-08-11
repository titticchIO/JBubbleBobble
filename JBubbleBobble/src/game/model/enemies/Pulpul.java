
package game.model.enemies;

import java.util.Random;

import game.model.HelpMethods;
import game.model.Settings;
import game.model.level.Level;

import static game.model.HelpMethods.isSolid;

public class Pulpul extends Enemy {

	public Pulpul(float x, float y) {
		super(x, y, "U");
		setxSpeed(0.7f);
		setAirSpeed(0.7f);
		setDirection(Direction.DOWN);
	}

	public Pulpul(float x, float y, float width, float height) {
		super(x, y, width, height, "U");
		setxSpeed(0.7f);
		setAirSpeed(0.7f);
		setDirection(Direction.DOWN);
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
		if (y > Level.GAME_HEIGHT + 1) {
			setY(-1);
		} else if (y < -2) {
			setY(Level.GAME_HEIGHT);
		} else {
			if (HelpMethods.canMoveHere(x, y + airSpeed, width, height)) {
				setY(y + airSpeed);
			} else {
				// Cambia direzione se incontra un ostacolo
				changeDirection();
			}
		}
	}

	@Override
	public void updateEntity() {
		if (randomBoolean(200))
			randomizeDirection();
		// Aggiornamento della posizione basato sulla direzione corrente
		if (direction == Direction.LEFT || direction == Direction.RIGHT)
			updateXPos();
		else
			updateYPos();
		setChanged();
		notifyObservers();
	}
}