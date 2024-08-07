package game.model.enemies;

import static game.model.HelpMethods.isSolid;

import game.model.HelpMethods;
import game.model.Settings;

public class Banebou extends Enemy {
	
	private final String type = "N";

	public Banebou(float x, float y, float width, float height, String imageCode) {
		super(x, y, width, height, imageCode);
		setxSpeed(1);
		setAirSpeed(0);
	}

	public void switchDirection() {
		if (isSolid(x - 1, y + height) || isSolid(x + width + 1, y + height)) {
			setxSpeed(-getxSpeed());
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		jump();
		switchDirection();
	}

	@Override
	public void jump() {
		System.out.println(inAir);
		if (!inAir) {
			inAir = true;
			airSpeed = jumpSpeed;
		}
	}

	@Override
	public void updateYPos() {
		if (y > Settings.GAME_HEIGHT) {
			setY(-1);
		} else if (airSpeed <= 0 || isSolid(x, y + height)) {
			setAirSpeed(0);
		} else {
			float delta = 0;
			while (delta < airSpeed && HelpMethods.canMoveHere(x, y + delta, width, height))
				delta += 0.01;
			if (delta < airSpeed) {
				setY(y - 1);
				resetInAir();
			}
			setY(y + delta);
		}
	}

	@Override
	public void gravity() {
		if (!HelpMethods.isEntityGrounded(this) && airSpeed < maxFallingSpeed) {
			inAir = true;
			airSpeed += gravity;
		}
	}

	@Override
	public String getType() {
		return type;
	}
}