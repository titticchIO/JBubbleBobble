package game.model.enemies;

import static game.model.HelpMethods.isSolidHorizontalLine;
import static game.model.HelpMethods.isSolidVerticalLine;
import game.model.HelpMethods;

public class Monsta extends Enemy {
	public static final char CODE = 'M';
	private static final float FLIGHT_SPEED = 0.3f; // Velocità minima per evitare che il nemico si blocchi

	public Monsta(float x, float y) {
		super(x, y, CODE);
		setxSpeed(0.3f);
		setAirSpeed(0.3f);
		setDirection(Direction.RIGHT);
		setColorState(ColorState.NORMAL);
	}

	public Monsta(float x, float y, float width, float height) {
		super(x, y, width, height, CODE);
		setxSpeed(0.3f);
		setAirSpeed(0.3f);
		setDirection(Direction.RIGHT);
		setColorState(ColorState.NORMAL);
	}

	public void bounce() {
		// GO DOWN
		if (isSolidHorizontalLine(x, x + width, y - 1)) {
			setAirSpeed(FLIGHT_SPEED * movementSpeed);
			if (randomBoolean(10) && HelpMethods.canMoveHere(x, y + 3, width, height)) {
				setY(y + 3);
			}
			// DO UP
		} else if (isSolidHorizontalLine(x, x + width, y + height + 1)) {
			setAirSpeed(-FLIGHT_SPEED * movementSpeed);
			if (randomBoolean(10) && HelpMethods.canMoveHere(x, y - 3, width, height))
				setY(y - 3);
		}
		// GO RIGHT
		if (isSolidVerticalLine(x - 1, y, y + height)) {
			setxSpeed(FLIGHT_SPEED * movementSpeed);
			setDirection(Direction.RIGHT);
			if (randomBoolean(10) && HelpMethods.canMoveHere(x - 3, y, width, height))
				setX(x - 3);
			// GO LEFT
		} else if (isSolidVerticalLine(x + width + 1, y, y + height)) {
			setxSpeed(-FLIGHT_SPEED * movementSpeed);
			setDirection(Direction.LEFT);
			if (randomBoolean(10) && HelpMethods.canMoveHere(x + 3, y, width, height))
				setX(x + 3);
		}
	}

	@Override
	public void updateEntity() {
		updateYPos();
		if (isDead()) {
			removeEnemy();
		} else {
			if (!isStopped) {
				bounce();
				updateXPos();
			}
		}
	}
}
