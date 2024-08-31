package game.model.enemies;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import game.model.HelpMethods;
import game.model.level.Level;
import game.model.tiles.Tile;

public class Boss extends Enemy {
	public static final char CODE = 'B';
	private int lives;

	public Boss(float x, float y) {
		super(x, y, 2*Tile.TILE_SIZE, 2*Tile.TILE_SIZE, CODE);
		setxSpeed(0.3f);
		setAirSpeed(0);
		setDirection(Direction.RIGHT);
		lives = 3;
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
			setAirSpeed(-0.5f * movementSpeed);
			setxSpeed(0);
			break;
		case 1:
			setAirSpeed(0.5f * movementSpeed);
			setxSpeed(0);
			break;
		case 2:
			direction = Direction.LEFT;
			setxSpeed(-0.5f * movementSpeed);
			setAirSpeed(0);
			break;
		case 3:
			direction = Direction.RIGHT;
			setxSpeed(0.5f * movementSpeed);
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
		System.out.println("Vite boss: "+lives);
		if (!isStopped) {
			if (randomBoolean(200))
				randomizeDirection();
			// Aggiornamento della posizione basato sulla direzione corrente
			updateXPos();
			updateYPos();
		}
	}

	public int getLives() {
		return lives;
	}

	public void looseLives() {
		lives--;
	}
}
