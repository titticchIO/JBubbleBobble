package game.model.enemies;

import game.model.HelpMethods;

public class Invader extends Enemy {

	private State state;
	private boolean landed;

	public enum State {
		WALK, SHOOT
	}

	public Invader(float x, float y) {
		super(x, y, "I");
		state = State.WALK;
		setAirSpeed(1);
		setDirection(Direction.LEFT);
	}

	public void changeDirection() {
		if (randomBoolean(1))
			setDirection(Direction.RIGHT);
		else
			setDirection(Direction.LEFT);
	}

	@Override
	public void updateEntity() {
		if (landed)
			landed = false;
		if (HelpMethods.isEntityGrounded(this) && !landed) {
			landed = true;
		}
		
		changeDirection();

		if (HelpMethods.isEntityGrounded(this)) {
			updateXPos();
		}else
			setX(x+1);
		
		
		move(1);
		updateYPos();
	}

}
