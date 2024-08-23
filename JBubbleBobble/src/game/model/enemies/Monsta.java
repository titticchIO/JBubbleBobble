package game.model.enemies;

import game.model.HelpMethods;
import game.model.Settings;
import game.model.entities.MovingEntity.Color;
import game.model.entities.MovingEntity.Direction;
import game.model.level.Level;

import java.util.Random;

import static game.model.HelpMethods.isSolidHorizontalLine;
import static game.model.HelpMethods.isSolidVerticalLine;

public class Monsta extends Enemy {

	private static final float FLIGHT_SPEED = 0.3f; // Velocità minima per evitare che il nemico si blocchi

	public Monsta(float x, float y) {
		super(x, y, "M");
		setxSpeed(0.3f);
		setAirSpeed(0.3f);
		setDirection(Direction.RIGHT);
		setColor(Color.NORMAL);
	}
	
	public Monsta(float x, float y, String code) {
		super(x, y, code);
		setxSpeed(0.3f);
		setAirSpeed(0.3f);
		setDirection(Direction.RIGHT);
		setColor(Color.NORMAL);
	}

	public Monsta(float x, float y, float width, float height) {
		super(x, y, width, height, "M");
		setxSpeed(0.3f);
		setAirSpeed(0.3f);
		setDirection(Direction.RIGHT);
	}
	
	public Monsta(float x, float y, float width, float height, String code) {
		super(x, y, width, height, code);
		setxSpeed(0.3f);
		setAirSpeed(0.3f);
		setDirection(Direction.RIGHT);
	}

	public void bounce() {
		// GO DOWN
		if (isSolidHorizontalLine(x, x + width, y - 1)) {
			setAirSpeed(FLIGHT_SPEED);
			if (randomBoolean(10) && HelpMethods.canMoveHere(x, y + 3, width, height)) {
				setY(y + 3);
			}
			// DO UP
		} else if (isSolidHorizontalLine(x, x + width, y + height + 1)) {
			setAirSpeed(-FLIGHT_SPEED);
			if (randomBoolean(10) && HelpMethods.canMoveHere(x, y - 3, width, height))
				setY(y - 3);
		}
		// GO RIGHT
		if (isSolidVerticalLine(x - 1, y, y + height)) {
			setxSpeed(FLIGHT_SPEED);
			setDirection(Direction.RIGHT);
			if (randomBoolean(10) && HelpMethods.canMoveHere(x - 3, y, width, height))
				setX(x - 3);
			// GO LEFT
		} else if (isSolidVerticalLine(x + width + 1, y, y + height)) {
			setxSpeed(-FLIGHT_SPEED);
			setDirection(Direction.LEFT);
			if (randomBoolean(10) && HelpMethods.canMoveHere(x + 3, y, width, height))
				setX(x + 3);
		}
	}

	@Override
	public void updateEntity() {
		if (!isStopped) {
			bounce();
			updateYPos();
			updateXPos();
		}
	}
}
