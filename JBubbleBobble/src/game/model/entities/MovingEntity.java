package game.model.entities;

import game.model.HelpMethods;
import game.model.Settings;

import static game.model.Settings.SCALE;

public abstract class MovingEntity extends Entity {
	
	protected boolean toChange = false;

	// Direzioni di movimento possibili
    public enum Directions {
        UP, DOWN, LEFT, RIGHT
    }

	/**
	 * Movement speed on x axis: positive up and negative down
	 */
	protected float xSpeed;

	protected Directions direction;

//	private boolean moving;
//	private int playerSpeed = 2;

	// jumping and gravity
	protected float airSpeed;
	protected float gravity;
	protected float jumpSpeed;
	protected float fallSpeedAfterCollision;
	protected float maxFallingSpeed;
	protected boolean inAir;

	/**
	 * MovingEntity constructor calls Entity's super constructor
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param type TODO
	 */

	public MovingEntity(float x, float y, float width, float height, String positionCode) {
		super(x, y, width, height, positionCode);
		direction = Directions.RIGHT;
		airSpeed = 0;
		gravity = 0.02f * SCALE;
		jumpSpeed = -2.0f * SCALE;
		fallSpeedAfterCollision = 0.3f * SCALE;
		maxFallingSpeed = 2;
		inAir = false;
	}

	/**
	 * Setters
	 */

	/**
	 * @param xSpeed
	 */
	public void setxSpeed(float xSpeed) {
		this.xSpeed = xSpeed;
	}

	/**
	 * Getters
	 */

	public String getPositionCode() {
		return positionCode;
	}

	/**
	 * @return movement speed on x axis
	 */
	public float getxSpeed() {
		return xSpeed;
	}
	
	public boolean isToChange() {
		return toChange;
	}

	protected void updateYPos() {
		if (y > Settings.GAME_HEIGHT+1) {
			setY(-1);
		}else if(y<-2) {
			setY(Settings.GAME_HEIGHT);
		} else if (airSpeed <= 0 || HelpMethods.isEntityInsideWall(x, y, width, height)) {
			setY(y + airSpeed);
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

	public void updateXPos() {
		if (HelpMethods.canMoveHere(x + xSpeed, y, (int) width, (int) height)
				|| (HelpMethods.isEntityInsideWall(x, y, width, height) && (x + xSpeed >= Settings.TILE_SIZE
						&& x + xSpeed + width <= Settings.GAME_WIDTH - Settings.TILE_SIZE))) {
			setX(x + xSpeed);
		} else {
			float delta = 0;
			if (xSpeed > 0) {
				while (delta < xSpeed && HelpMethods.canMoveHere(x + delta, y, width, height))
					delta += 0.01;
				if (delta < xSpeed)
					setX(x - 1);
			} else {
				while (delta > xSpeed && HelpMethods.canMoveHere(x + delta, y, width, height))
					delta -= 0.01;
				if (delta > xSpeed)
					setX(x + 1);
			}
			setX(x + delta);
		}
	}

	public void jump() {
		if (!inAir && !HelpMethods.isEntityInsideWall(x, y, width, height)) {
			inAir = true;
			airSpeed = jumpSpeed;
		}
	}

	public void resetInAir() {
		inAir = false;
		airSpeed = 0.0f;
	}

	public float getAirSpeed() {
		return airSpeed;
	}

	public void setAirSpeed(float airSpeed) {
		this.airSpeed = airSpeed;
	}

	public void stop() {
		setxSpeed(0);
	}

	public void move(Directions dir) {
		if (dir == Directions.LEFT)
			setxSpeed((int) -1);
		else
			setxSpeed((int) 1);
	}

	public void gravity() {
		if (!HelpMethods.isEntityGrounded(this) && airSpeed < maxFallingSpeed) {
			inAir = true;
			airSpeed += gravity;
		}
	}

	public void setDirections(Directions direction) {
		this.direction = direction;
	}
	
	public void updateImage() {
		toChange = false;
		//if((x < 0 || x > Settings.GAME_WIDTH) || (y < 0 || y + height > Settings.GAME_HEIGHT)) pop();
		if (xSpeed < 0 && !positionCode.equals("left")) {
			toChange = true;
			setPositionCode("left");
		} else if (xSpeed >= 0 && !positionCode.equals("right")){
			toChange = true;
			setPositionCode("right");
		} 
	}
	
	

	public void updateEntity() {
		updateImage();
		updateXPos();
		updateYPos();
		gravity();
		setChanged();
		notifyObservers();
	}
	
	
	
	
	public abstract String getType();
	
	

}