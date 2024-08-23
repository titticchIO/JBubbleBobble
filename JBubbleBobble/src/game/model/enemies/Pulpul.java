
package game.model.enemies;

import java.util.Random;

import game.model.HelpMethods;
import game.model.Settings;
import game.model.entities.MovingEntity.Color;
import game.model.level.Level;

import static game.model.HelpMethods.isSolid;

public class Pulpul extends Enemy {

	public Pulpul(float x, float y) {
		super(x, y, "U");
		setxSpeed(0.5f);
		setAirSpeed(0);
		setColor(Color.NORMAL);
	}

	public Pulpul(float x, float y, float width, float height) {
		super(x, y, width, height, "U");
		setxSpeed(0.5f);
		setAirSpeed(0);
		setColor(Color.NORMAL);
	}

	private void changeDirection() {
		if (randomBoolean(10))
			randomizeDirection();

		if (xSpeed != 0) {
			// Switch between left and right
			setxSpeed(-xSpeed);
			setAirSpeed(0);
		} else if (airSpeed != 0) {
			// Switch between up and down
			setxSpeed(0);
			setAirSpeed(-airSpeed);
		} else {
			// If both are zero, randomize direction again
			randomizeDirection();
		}
	}

	private void randomizeDirection() {
		int randomInt = new Random().nextInt(4);
		switch (randomInt) {
		case 0:
			setAirSpeed(-0.5f);
			setxSpeed(0);
			break;
		case 1:
			setAirSpeed(0.5f);
			setxSpeed(0);
			break;
		case 2:
			direction = Direction.LEFT;
			setxSpeed(-0.5f);
			setAirSpeed(0);
			break;
		case 3:
			direction = Direction.RIGHT;
			setxSpeed(0.5f);
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
		if (!isStopped) {
			if (randomBoolean(200))
				randomizeDirection();
				// Aggiornamento della posizione basato sulla direzione corrente
				updateXPos();
				updateYPos();
		}
	}
}