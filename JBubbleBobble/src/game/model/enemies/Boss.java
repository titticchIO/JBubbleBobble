package game.model.enemies;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import game.model.Fruit;
import game.model.HelpMethods;
import game.model.Model;
import game.model.Tile;
import game.model.Fruit.FruitType;
import game.model.interfaces.ChangeDirection;
import game.model.interfaces.Vulnerable;
import game.model.level.Level;

/**
 * The {@code Boss} class represents a powerful enemy in the game with multiple
 * lives and the ability to change directions randomly. It implements both
 * Vulnerable and ChangeDirection interfaces to handle interactions with the
 * player and movement behavior.
 */
public class Boss extends Enemy implements Vulnerable, ChangeDirection {

	public static final char CODE = 'B';
	private int lives;
	private Timer invulnerabilityTimer;
	private boolean isInvulnerable;

	/**
	 * Constructs a new Boss instance at the specified x and y coordinates with
	 * default attributes.
	 * 
	 * @param x The initial x-coordinate of the Boss.
	 * @param y The initial y-coordinate of the Boss.
	 */
	public Boss(float x, float y) {
		super(x, y, 2 * Tile.TILE_SIZE, 2 * Tile.TILE_SIZE, CODE);
		setxSpeed(0.3f);
		setAirSpeed(0);
		setDirection(Direction.RIGHT);
		lives = 3;
	}

	/**
	 * Gets the invulnerability timer associated with the Boss.
	 * 
	 * @return The invulnerability timer.
	 */
	public Timer getInvulnerabilityTimer() {
		return invulnerabilityTimer;
	}

	/**
	 * Gets the remaining lives of the Boss.
	 * 
	 * @return The number of lives the Boss has.
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * Sets the invulnerability timer for the Boss.
	 * 
	 * @param invincibilityTimer The Timer to set for invulnerability.
	 */
	public void setInvulnerabilityTimer(Timer invincibilityTimer) {
		this.invulnerabilityTimer = invincibilityTimer;
	}

	/**
	 * Randomly changes the direction of the Boss based on current movement state.
	 * The Boss may switch between moving left, right, up, or down.
	 */
	@Override
	public void changeDirection() {
		if (randomBoolean(10))
			randomizeDirection();

		if (xSpeed != 0) {
			// Switch between left and right
			setxSpeed(-xSpeed);
			setAirSpeed(0);
		} else if (airSpeed != 0) {
			// Switch between up and down
			setxSpeed(0);
			setAirSpeed(-airSpeed);
		} else {
			// If both are zero, randomize direction again
			randomizeDirection();
		}
	}

	/**
	 * Randomizes the movement direction of the Boss, choosing between up, down,
	 * left, and right.
	 */
	private void randomizeDirection() {
		int randomInt = new Random().nextInt(4);
		switch (randomInt) {
		case 0:
			setAirSpeed(-0.5f * movementSpeed);
			setxSpeed(0);
			break;
		case 1:
			setAirSpeed(0.5f * movementSpeed);
			setxSpeed(0);
			break;
		case 2:
			direction = Direction.LEFT;
			setxSpeed(-0.5f * movementSpeed);
			setAirSpeed(0);
			break;
		case 3:
			direction = Direction.RIGHT;
			setxSpeed(0.5f * movementSpeed);
			setAirSpeed(0);
			break;
		}
	}

	/**
	 * Updates the x-coordinate of the Boss based on the current xSpeed and checks
	 * for collisions with tiles.
	 */
	@Override
	public void updateXPos() {
		if (HelpMethods.canMoveHere(x + xSpeed, y, width, height)) {
			setX(x + xSpeed);
		} else {
			changeDirection();
		}
	}

	/**
	 * Updates the y-coordinate of the Boss based on the current airSpeed and checks
	 * for collisions. If the Boss moves off the top or bottom of the level, its
	 * position is wrapped around.
	 */
	@Override
	public void updateYPos() {
		if (y > Level.GAME_HEIGHT + 1) {
			setY(-1);
		} else if (y < -2) {
			setY(Level.GAME_HEIGHT);
		} else {
			if (HelpMethods.canMoveHere(x, y + airSpeed, width, height)) {
				setY(y + airSpeed);
			} else {
				changeDirection();
			}
		}
	}

	/**
	 * Removes the Boss from the game. Upon removal, the Boss drops two fruits if it
	 * is grounded.
	 */
	@Override
	public void removeEnemy() {
		if (HelpMethods.isEntityGrounded(this)) {
			Fruit orange1 = new Fruit(x, y + Tile.TILE_SIZE, FruitType.ORANGE);
			Fruit orange2 = new Fruit(x + Tile.TILE_SIZE, y + Tile.TILE_SIZE, FruitType.ORANGE);
			Model.getInstance().getCurrentLevel().getFruitManager().addFruit(orange1);
			Model.getInstance().getCurrentLevel().getFruitManager().addFruit(orange2);
			Model.getInstance().getCurrentLevel().getEnemyManager().removeEnemy(this);
		}
	}

	/**
	 * Updates the Boss's state, including its movement, life count, and whether it
	 * is dead or alive. When the Boss dies, it drops fruits and removes itself from
	 * the level.
	 */
	@Override
	public void updateEntity() {
		if (lives == 0 && !isDead()) {
			kill();
			Model.getInstance().sendNotification("bossKill");
		}
		if (isDead()) {
			updateYPos();
			removeEnemy();
		} else {
			if (!isStopped) {
				updateYPos();
				if (randomBoolean(200))
					randomizeDirection();
				updateXPos();
			}
		}
	}

	/**
	 * Reduces the Boss's life count by one if it is not currently invulnerable, and
	 * triggers invulnerability for a short duration.
	 */
	@Override
	public void looseLife() {
		if (invulnerabilityTimer == null && !isInvulnerable) {
			lives--;
			isInvulnerable = true;
			Model.getInstance().sendNotification("bossHit");
			invulnerabilityTimer = new Timer();
			invulnerabilityTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					isInvulnerable = false;
					invulnerabilityTimer.cancel();
					invulnerabilityTimer = null;
				}
			}, 1000);
		}

	}
}
