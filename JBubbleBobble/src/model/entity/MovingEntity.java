package model.entity;

import model.level.LevelLoader;
import view.GameFrame;
import model.HelpMethods;

public abstract class MovingEntity extends Entity {
	
	// positive up, negative down
	private float xSpeed;
	// positive right, negative left
	private float ySpeed;
	private boolean moving;
	private int playerSpeed = 2;
	
	private boolean up, left, down, right;
	
	
	
	//jumping and gravity
	private boolean jump = false;
	private float airSpeed = 0f;
	private float gravity = 0.04f * GameFrame.SCALE;
	private float jumpSpeed = -2.25f * GameFrame.SCALE;
	private float fallSpeedAfterCollision = 0.5f * GameFrame.SCALE;
	private boolean inAir = false;
	
	
	
	public MovingEntity(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	/**
	 * Getters and Setters
	 */

	public float getxSpeed() {
		return xSpeed;
	}

	public void setxSpeed(float xSpeed) {
		this.xSpeed = xSpeed;
	}

	public float getySpeed() {
		return ySpeed;
	}

	public void setySpeed(float ySpeed) {
		this.ySpeed = ySpeed;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isJump() {
		return jump;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	/**
	 * Acceleration
	 */
	public void setxAcceleration(float n) {
		setxSpeed(getxSpeed() + n);
	}

	public void setyAcceleration(float n) {
		setySpeed(getySpeed() + n);
	}
	
	/**
	 * Deceleration
	 */
	public void setxDeceleration(float n) {
		setxSpeed(getxSpeed() - n);
	}
	
	public void setyDeceleration(float n) {
		setySpeed(getySpeed() - n);
	}
	
	
	

	/**
	 * Movement
	 */
	public void move() {
		moving = false;
		if(jump) jump();
		if (!left && !right && !inAir) return;

		//if(jump) jump();

		float xSpeed = 0;

		if(left) xSpeed -= playerSpeed; 
		if(right) xSpeed += playerSpeed;

		
		
		
		if (inAir) {
			if (HelpMethods.canMoveHere(x, y + airSpeed, (int)width, (int)height, LevelLoader.getLevelData())) {
				y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			} else {
				y += HelpMethods.getEntityPosUnderRoofOrAboveFloor(this, airSpeed);
				//notifyPosition();
				if(airSpeed > 0) 
					resetInAir();
				else 
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}
		} else {
			updateXPos(xSpeed);
		}
		
		moving = true;
		//setX(x); // Aggiornamento finale delle coordinate x
		//setY(y); // Aggiornamento finale delle coordinate y
	}

	private void updateXPos(float xSpeed) {
		if (HelpMethods.canMoveHere(x + xSpeed, y, (int)width, (int)height, LevelLoader.getLevelData())) {
			this.x += xSpeed;
			notifyPosition();
		} else {
			//this.x = HelpMethods.getEntityXPosNextToWall(this, xSpeed);
			//notifyPosition();
		}
			
		//}
	}
	
	public void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}
	
	public void jump() {
		if(!inAir) {
			inAir = true;
			airSpeed = jumpSpeed;
		}
	}
		
	
		
		
		
		/*
		if(HelpMethods.canMoveHere(x + xSpeed, y + ySpeed, (int)width, (int)height, LevelLoader.getLevelData())) {				
			this.x += xSpeed;
			this.y += ySpeed;
		}
		setX(getX());
		setY(getY());
		*/
	
	
	
	

	public void updateEntity() {
		move();
	};
}