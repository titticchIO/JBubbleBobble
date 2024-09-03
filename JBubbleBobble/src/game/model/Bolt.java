package game.model;

import java.util.Optional;

/**
 * The {@code Bolt} class represents a bolt that moves horizontally across
 * the game screen. It can kill enemies upon collision and is removed when it
 * goes out of bounds.
 */
public class Bolt extends MovingEntity {

	// Static Fields
	public static final float BOLT_SPEED = 2;
	public static final char CODE = '?';

	// Constructors

	/**
	 * Constructs a {@code Bolt} at the specified position. The bolt's speed is
	 * determined by the player's current direction.
	 *
	 * @param x the initial x-coordinate of the bolt.
	 * @param y the initial y-coordinate of the bolt.
	 */
	public Bolt(float x, float y) {
		super(x, y, Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1, CODE);
		setBoltXSpeed();
	}

	// Other Methods

	/**
	 * Sets the horizontal speed of the bolt based on the player's direction.
	 */
	private void setBoltXSpeed() {
		setxSpeed(Player.getInstance().getDirection() == Player.Direction.LEFT ? BOLT_SPEED : -BOLT_SPEED);
	}

	/**
	 * Updates the bolt's x-coordinate position. If the bolt goes out of the game
	 * bounds, it is removed.
	 */
	@Override
	public void updateXPos() {
		if (x + xSpeed > Tile.TILE_SIZE && x + xSpeed < Level.GAME_WIDTH - 2 * Tile.TILE_SIZE) {
			setX(x + xSpeed);
		} else {
			Model.getInstance().getCurrentLevel().getBubbleManager().removeBolt(this);
		}
	}

	/**
	 * Checks for collisions with enemies. If a collision is detected, the enemy is
	 * removed from the game.
	 */
	public void killEnemy() {
		Optional<Enemy> enemy = Entity.checkCollision(this,
				Model.getInstance().getCurrentLevel().getEnemyManager().getEnemies());
		enemy.ifPresent(value -> Model.getInstance().getCurrentLevel().getEnemyManager().removeEnemy(value));
	}

	/**
	 * Updates the bolt's state, including its position and interactions with
	 * enemies.
	 */
	@Override
	public void updateEntity() {
		updateXPos();
		killEnemy();
	}
}
