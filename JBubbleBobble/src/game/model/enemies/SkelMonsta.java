package game.model.enemies;

import static game.model.HelpMethods.isSolidHorizontalLine;
import static game.model.HelpMethods.isSolidVerticalLine;

import java.util.Random;

import game.model.HelpMethods;
import game.model.Settings;
import game.model.entities.MovingEntity.Direction;
import game.model.level.Level;
import game.model.tiles.Tile;

public class SkelMonsta extends Monsta {

	private static final float FLIGHT_SPEED = 0.4f;
	
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

	@Override
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
		// if((x < 0 && x > Settings.GAME_WIDTH) || (y < 0 && y > Settings.GAME_HEIGHT))
		// pop();
//		updateImage();
		bounce();
		updateYPos();
		updateXPos();
	}

}
