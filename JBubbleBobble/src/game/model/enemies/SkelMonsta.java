package game.model.enemies;

import java.util.Timer;
import java.util.TimerTask;

import game.model.entities.Player;
import game.model.level.Level;
import game.model.tiles.Tile;

/**
 * The {@code SkelMonsta} class represents an enemy that can move horizontally
 * and vertically, changing direction upon hitting the boundaries of the game
 * area. It extends the {@link Enemy} class.
 */
public class SkelMonsta extends Enemy {

	// Static Fields
	public static final char CODE = 'S';
	public static final float FLIGHT_SPEED = 0.4f;
	private boolean isMoving;
	// Constructors

	/**
	 * Constructs a {@code SkelMonsta} with the specified position.
	 *
	 * @param x the x-coordinate of the SkelMonsta.
	 * @param y the y-coordinate of the SkelMonsta.
	 */
	public SkelMonsta(float x, float y) {
		super(x, y, CODE);
		initializeSkelMonsta();
	}

	/**
	 * Constructs a {@code SkelMonsta} with the specified position and size.
	 *
	 * @param x      the x-coordinate of the SkelMonsta.
	 * @param y      the y-coordinate of the SkelMonsta.
	 * @param width  the width of the SkelMonsta.
	 * @param height the height of the SkelMonsta.
	 */
	public SkelMonsta(float x, float y, float width, float height) {
		super(x, y, width, height, CODE);
		initializeSkelMonsta();
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	// Private Methods

	private void trackPlayer() {
		// UPDATE AIRSPEED
		if (y > Player.getInstance().getY())
			setAirSpeed(-0.2f);
		else if (y < Player.getInstance().getY())
			setAirSpeed(0.2f);
		else
			setAirSpeed(0);

		// UPDATE XSPEED
		if (x > Player.getInstance().getX())
			setxSpeed(-0.2f);
		else if (x < Player.getInstance().getX())
			setxSpeed(0.2f);
		else
			setxSpeed(0);
	}

	/**
	 * Initializes the common properties of a {@code SkelMonsta}.
	 */
	private void initializeSkelMonsta() {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				setMoving(!isMoving());

			}
		}, 0, 2500);
		setDirection(Direction.RIGHT);
		setColorState(ColorState.NORMAL);
	}

	// Override Methods

	/**
	 * Updates the horizontal position of the SkelMonsta based on its current speed
	 * and direction.
	 */
	@Override
	public void updateXPos() {
		setX(x + xSpeed);
	}

	/**
	 * Updates the vertical position of the SkelMonsta based on its current speed
	 * and direction.
	 */
	@Override
	public void updateYPos() {
		setY(y + airSpeed);
	}

	/**
	 * Updates the state of the SkelMonsta each game tick.
	 */
	@Override
	public void updateEntity() {
		if (isDead()) {
			updateYPos();
			removeEnemy();}
		else if (!isStopped && isMoving) {
			trackPlayer();
			updateYPos();
			updateXPos();
		}
	}
}
