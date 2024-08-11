package game.model.enemies;

import static game.model.HelpMethods.isSolidHorizontalLine;
import static game.model.HelpMethods.isSolidVerticalLine;

import java.util.Random;

import game.model.HelpMethods;
import game.model.Settings;
import game.model.entities.MovingEntity.Direction;
import game.model.level.Level;
import game.model.tiles.Tile;

public class SkelMonsta extends Enemy {

	private Random random;
	private static final float MIN_SPEED = 0.1f; // Velocità minima per evitare che il nemico si blocchi
	private static final float MAX_SPEED = 0.6f; // Velocità massima per limitare il movimento del nemico

	public SkelMonsta(float x, float y) {
		super(x, y, "S");
		setxSpeed(0.3f);
		setAirSpeed(0.3f);
		setDirection(Direction.RIGHT);
		random = new Random();
	}

	public SkelMonsta(float x, float y, float width, float height) {
		super(x, y, width, height, "S");
		setxSpeed(0.3f);
		setAirSpeed(0.3f);
		setDirection(Direction.RIGHT);
		random = new Random();
	}

	public void bounce() {
		

		// Controlla se il nemico ha raggiunto i bordi superiore o inferiore del gioco
		if (y <= Tile.TILE_SIZE || y + height >= Level.GAME_HEIGHT - Tile.TILE_SIZE) {
			// System.out.println("siuuum sotto");
			// Inverti la direzione verticale e applica un elemento casuale alla velocità
			airSpeed = -airSpeed * (0.8f + random.nextFloat() * 0.4f); // Velocità tra 80% e 120% dell'attuale
			// Assicurati che la velocità non sia inferiore a MIN_SPEED o superiore a
			// MAX_SPEED
			airSpeed = Math.max(Math.min(airSpeed, MAX_SPEED), -MAX_SPEED);
			if (Math.abs(airSpeed) < MIN_SPEED) {
				airSpeed = Math.signum(airSpeed) * MIN_SPEED;
			}
		}
		
		// Controlla se il nemico ha raggiunto i bordi sinistro o destro del gioco
		if (x <= Tile.TILE_SIZE || x + width >= Level.GAME_WIDTH - Tile.TILE_SIZE) {
			switch (direction) {
			case LEFT -> {
					setDirection(Direction.RIGHT);
				}
			case RIGHT -> {
					setDirection(Direction.LEFT);
				}
		}
			// System.out.println("siuuum sopra");
			// Inverti la direzione orizzontale e applica un elemento casuale alla velocità
			xSpeed = -xSpeed * (0.8f + random.nextFloat() * 0.4f); // Velocità tra 80% e 120% dell'attuale
			// Assicurati che la velocità non sia inferiore a MIN_SPEED o superiore a
			// MAX_SPEED
			xSpeed = Math.max(Math.min(xSpeed, MAX_SPEED), -MAX_SPEED);
			if (Math.abs(xSpeed) < MIN_SPEED) {
				xSpeed = Math.signum(xSpeed) * MIN_SPEED;
			}
		}
	}

	@Override
	public void updateXPos() {
		setX(x + xSpeed);
	}

	@Override
	public void updateYPos() {
		setY(y + airSpeed);
	}

	@Override
	public void updateEntity() {
		// if((x < 0 && x > Settings.GAME_WIDTH) || (y < 0 && y > Settings.GAME_HEIGHT))
		// pop();
//		updateImage();
		bounce();
		updateYPos();
		updateXPos();
	}

}
