package model.enemies;

import model.HelpMethods;
import static model.HelpMethods.isSolidHorizontalLine;
import static model.HelpMethods.isSolidVerticalLine;
import model.level.LevelLoader;

public class Monsta extends Enemy {

	public Monsta(float x, float y, float width, float height) {
		super(x, y, width, height, "Monsta");
		setxSpeed(1);
		setAirSpeed(1);
	}

	public void bounce() {
		if (isSolidHorizontalLine(x, x + width, y - 1) || isSolidHorizontalLine(x, x + width, y + height + 1)) {
			airSpeed *= -1;
		}
		if (isSolidVerticalLine(x - 1, y, y + height) || isSolidVerticalLine(x + width + 1, y, y + height)) {
			System.out.println("horizontal bounce");
			xSpeed *= -1;
		}
	}

	protected void updateXPos() {
		if (HelpMethods.canMoveHere(x + xSpeed, y, (int) width, (int) height, LevelLoader.getLevelData())) {
			setX(x + xSpeed);
		} else {
			x = HelpMethods.getEntityXPosNextToWall(this, xSpeed);
		}
	}

	@Override
	public void updateEntity() {
		bounce();
		updateYPos();
		updateXPos();
	}
}