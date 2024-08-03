package game.model.enemies;

import game.model.HelpMethods;
import utils.LevelLoader;

import static game.model.HelpMethods.isSolidHorizontalLine;
import static game.model.HelpMethods.isSolidVerticalLine;

public class Monsta extends Enemy {

	public Monsta(float x, float y, float width, float height, String imageCode) {
		super(x, y, width, height, imageCode, "Monsta");
		setxSpeed(1);
		setAirSpeed(1);
	}

	public void bounce() {
		if (isSolidHorizontalLine(x, x + width, y - 1) || isSolidHorizontalLine(x, x + width, y + height + 1)) {
			airSpeed *= -1;
		}
		if (isSolidVerticalLine(x - 1, y, y + height) || isSolidVerticalLine(x + width + 1, y, y + height)) {
			xSpeed *= -1;
		}
	}

	public void updateXPos() {
		if (HelpMethods.canMoveHere(x + xSpeed, y, width, height)) {
			setX(x + xSpeed);
		} else {
			x = HelpMethods.getEntityXPosNextToWall(this);
		}
	}

	@Override
	public void updateEntity() {
		bounce();
		updateYPos();
		updateXPos();
	}
}
