package game.model.enemies;

import static game.model.HelpMethods.isSolid;

import java.util.Random;

import game.model.HelpMethods;

public class Banebou extends Enemy {
	
	private final String type = "N";
	
	private long lastChangeTime;
	private long changeInterval;

	public Banebou(float x, float y, float width, float height, String imageCode) {
		super(x, y, width, height, imageCode);
		setxSpeed(0.7f);
		setJumpSpeed(-1.5f);
		lastChangeTime = System.currentTimeMillis();
		changeInterval = 8000;
	}

	private void changeDirection() {
		changeInterval = new Random().nextLong(8000, 10000);
		switch (direction) {
		case Direction.LEFT: 
			direction = Direction.RIGHT;
			setxSpeed(0.7f);
			break;
		case Direction.RIGHT:
			direction = Direction.LEFT;
			setxSpeed(-0.7f);
			break;
		default:
			break;
		}
	}

	
	public void checkAndChangeDirection() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastChangeTime > changeInterval) {
			changeDirection();
			lastChangeTime = currentTime;
		}
	}
	
	@Override
	public void updateXPos() {
		if (HelpMethods.canMoveHere(x + xSpeed, y, width, height) || HelpMethods.isEntityInsideWall(x, y, width, height)) {
			setX(x + xSpeed);
		} else {
			// Cambia direzione se incontra un ostacolo
			changeDirection();
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		jump();
		checkAndChangeDirection(); // Verifica se deve cambiare direzione
		// Aggiornamento della posizione basato sulla direzione corrente
		setChanged();
		notifyObservers();
	}
	
	@Override
	public String getType() {
		return type;
	}
}