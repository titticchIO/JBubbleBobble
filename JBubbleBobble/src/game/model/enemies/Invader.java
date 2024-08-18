package game.model.enemies;

import game.model.HelpMethods;

public class Invader extends Enemy {
	
	private State state;
	
	public enum State {
			WALK,
			SHOOT
	}

	public Invader(float x, float y, float width, float height) {
		super(x, y, width, height, "I");
		state = State.WALK;
		setAirSpeed(1);
		setDirection(Direction.RIGHT);
	}

	public void changeDirection() {
		if (HelpMethods.isEntityGrounded(this))
			if (randomBoolean(1))
				setDirection(Direction.RIGHT);
			else
				setDirection(Direction.LEFT);
	}

	@Override
	public void updateEntity() {
		updateXPos();
		updateYPos();
		changeDirection();
	}

}
