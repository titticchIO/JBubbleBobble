package game.model.enemies;

import java.util.Random;

import game.model.level.Level;
import game.model.tiles.Tile;

public class SkelMonsta extends Enemy {

	private static final float FLIGHT_SPEED = 0.4f;
	

	public SkelMonsta(float x, float y) {
		super(x, y, "S");
		setxSpeed(0.3f);
		setAirSpeed(0.3f);
		setDirection(Direction.RIGHT);
	}

	public SkelMonsta(float x, float y, float width, float height) {
		super(x, y, width, height, "S");
		setxSpeed(0.3f);
		setAirSpeed(0.3f);
		setDirection(Direction.RIGHT);
	}

	public void bounce() {
		// GO DOWN
		if (y - 1 <= Tile.TILE_SIZE) {
			setAirSpeed(FLIGHT_SPEED);
			if (randomBoolean(10)) {
				setY(y + 3);
			}
			// DO UP
		} else if (y + height + 1 >= Level.GAME_HEIGHT - Tile.TILE_SIZE) {
			setAirSpeed(-FLIGHT_SPEED);
			if (randomBoolean(10))
				setY(y - 3);
		}
		
		// GO RIGHT
		if (x - 1 <= Tile.TILE_SIZE) {
			setxSpeed(FLIGHT_SPEED);
			setDirection(Direction.RIGHT);
			if (randomBoolean(10))
				setX(x - 3);
			// GO LEFT
		} else if (x + width + 1 >= Level.GAME_WIDTH - Tile.TILE_SIZE) {
			setxSpeed(-FLIGHT_SPEED);
			setDirection(Direction.LEFT);
			if (randomBoolean(10))
				setX(x + 3);
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
		bounce();
		updateYPos();
		updateXPos();
	}

}
