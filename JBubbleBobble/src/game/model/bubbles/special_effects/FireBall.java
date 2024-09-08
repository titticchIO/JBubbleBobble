package game.model.bubbles.special_effects;

import static game.model.tiles.Tile.TILE_SIZE;
import java.util.Timer;
import java.util.TimerTask;
import game.model.HelpMethods;
import game.model.Model;
import game.model.entities.MovingEntity;

/**
 * The {@code FireBall} class represents a fire ball entity in the game that can
 * fall and burn. It has a state of either falling or burning and can spawn
 * additional fire balls when it expands.
 */
public class FireBall extends MovingEntity {

	// Static Fields
	/**
	 * the code if the FireBall class
	 */
	public static final char CODE = '#';

	// Instance Fields
	/**
	 * the current state of the fireball
	 */
	public FireState fireState;

	/**
	 * Enum representing the state of the fire ball: either falling or burning.
	 */
	public enum FireState {
		FALL, BURN
	}

	// Constructors

	/**
	 * Constructs a FireBall with the specified position and state.
	 *
	 * @param x         the initial x-coordinate of the fire ball.
	 * @param y         the initial y-coordinate of the fire ball.
	 * @param fireState the initial state of the fire ball.
	 */
	public FireBall(float x, float y, FireState fireState) {
		super(x, y, CODE);
		airSpeed = 2.0f;
		this.fireState = fireState;
		scheduleRemoval(5000);
	}

	/**
	 * Constructs a FireBall with the specified position and a default state of
	 * FALL.
	 *
	 * @param x the initial x-coordinate of the fire ball.
	 * @param y the initial y-coordinate of the fire ball.
	 */
	public FireBall(float x, float y) {
		super(x, y, CODE);
		airSpeed = 2.0f;
		fireState = FireState.FALL;
	}

	// Getters

	/**
	 * Gets the current state of the fire ball.
	 *
	 * @return the current fire state.
	 */
	public FireState getFireState() {
		return fireState;
	}

	// Other Methods

	/**
	 * Updates the vertical position of the fire ball.
	 */
	@Override
	protected void updateYPos() {
		setY(y + airSpeed);
	}

	/**
	 * Spawns additional fire balls when the current fire ball expands.
	 */
	private void spawnFireBalls() {
		char[][] lvlData = Model.getInstance().getCurrentLevel().getLvlData();
		int xPos = (int) (x / TILE_SIZE);
		int yPos = (int) (y / TILE_SIZE);
		for (int i = 1; i < 3; i++) {
			if (Character.isDigit(lvlData[yPos + 1][xPos + i])) {
				Model.getInstance().getCurrentLevel().getBubbleManager()
						.addFireBall(new FireBall((xPos + i) * TILE_SIZE, yPos * TILE_SIZE, FireState.BURN));
			}
			if (Character.isDigit(lvlData[yPos + 1][xPos - i])) {
				Model.getInstance().getCurrentLevel().getBubbleManager()
						.addFireBall(new FireBall((xPos - i) * TILE_SIZE, yPos * TILE_SIZE, FireState.BURN));
			}
		}
	}

	/**
	 * Expands the fire ball, changing its state to BURN and spawning additional
	 * fire balls.
	 */
	public void expand() {
		fireState = FireState.BURN;
		spawnFireBalls();
		scheduleRemoval(5000);
	}

	/**
	 * Schedules the removal of the fire ball from the game after a specified delay.
	 *
	 * @param delay the delay in milliseconds before the fire ball is removed.
	 */
	private void scheduleRemoval(long delay) {
		new Timer("Remove FireBall").schedule(new TimerTask() {
			@Override
			public void run() {
				Model.getInstance().getCurrentLevel().getBubbleManager().removeFireBall(FireBall.this);
			}
		}, delay);
	}

	/**
	 * Updates the fireball's state and position, triggering expansion when
	 * grounded.
	 */
	@Override
	public void updateEntity() {
		if (fireState == FireState.FALL) {
			updateYPos();
			if (HelpMethods.isEntityGrounded(this))
				expand();
		}
	}
}
