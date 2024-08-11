package game.model.enemies;

import game.model.HelpMethods;
import game.model.Settings;
import game.model.entities.MovingEntity.Direction;
import game.model.level.Level;

import java.util.Random;

import static game.model.HelpMethods.isSolidHorizontalLine;
import static game.model.HelpMethods.isSolidVerticalLine;

public class Monsta extends Enemy {

	private Random random;
	private static final float MIN_SPEED = 0.5f; // Velocità minima per evitare che il nemico si blocchi
	private static final float MAX_SPEED = 0.6f; // Velocità massima per limitare il movimento del nemico

	public Monsta(float x, float y) {
		super(x, y, "M");
		setxSpeed(0.3f);
		setAirSpeed(0.3f);
		setDirection(Direction.RIGHT);
		random = new Random();
	}

	public Monsta(float x, float y, float width, float height) {
		super(x, y, width, height, "M");
		setxSpeed(0.3f);
		setAirSpeed(0.3f);
		setDirection(Direction.RIGHT);
		random = new Random();
	}

	public void bounce() {
		if (y <= 0 || y + height >= Level.GAME_HEIGHT || isSolidHorizontalLine(x, x + width, y - 1)
				|| isSolidHorizontalLine(x, x + width, y + height + 1)) {
			airSpeed = -airSpeed * (0.8f + random.nextFloat() * 0.4f); // Velocità tra 80% e 120% dell'attuale
			airSpeed = Math.max(Math.min(airSpeed, MAX_SPEED), -MAX_SPEED);
			if (Math.abs(airSpeed) < MIN_SPEED) {
				airSpeed = Math.signum(airSpeed) * MIN_SPEED;
			}
			if (airSpeed == 0) {
				airSpeed = MIN_SPEED;
			}
		}
		if (x <= 0 || x + width >= Level.GAME_WIDTH || isSolidVerticalLine(x - 1, y, y + height)
				|| isSolidVerticalLine(x + width + 1, y, y + height)) {
			// Invertire la direzione orizzontale
			switch (direction) {
				case LEFT -> {
						setDirection(Direction.RIGHT);
					}
				case RIGHT -> {
						setDirection(Direction.LEFT);
					}
			}
			xSpeed = -xSpeed * (0.8f + random.nextFloat() * 0.4f); // Velocità tra 80% e 120% dell'attuale
			xSpeed = Math.max(Math.min(xSpeed, MAX_SPEED), -MAX_SPEED);
			if (Math.abs(xSpeed) < MIN_SPEED) {
				xSpeed = Math.signum(xSpeed) * MIN_SPEED;
			}
		}
	}

	@Override
	public void updateEntity() {
		bounce();
		move(0.5f);  // Usa il metodo move per gestire la direzione
		updateYPos();
		updateXPos();
	}
}
