package game.model.bubbles.special_effects;

import static game.model.tiles.Tile.TILE_SIZE;
import static game.model.entities.MovingEntity.Direction.*;
import game.model.HelpMethods;
import game.model.Model;
import game.model.enemies.Enemy;
import game.model.entities.MovingEntity;
import game.model.entities.Player;
import game.model.level.Level;

/**
 * The {@code Water} class represents a special effect entity that moves
 * horizontally across the level, captures entities, and has a limited lifespan.
 * Once the lifespan expires, the water entity is deleted and may release any
 * captured entity. The water entity can spawn additional water entities while
 * moving.
 */

public class Water extends MovingEntity {

	// Static Fields
	public static final char CODE = '_'; // Code representing water in the game grid.

	// Instance Fields
	private MovingEntity capturedEntity; // Entity captured by the water, if any.
	private int watersToSpawn; // Number of additional water entities to spawn.
	private int lifeSpan; // Lifespan of the water entity before deletion.

	// Constructor

	/**
	 * Constructs a {@code Water} entity at the specified position with a given
	 * number of subsequent waters to spawn and initializes its lifespan.
	 *
	 * @param x             the initial x-coordinate of the water entity.
	 * @param y             the initial y-coordinate of the water entity.
	 * @param watersToSpawn the number of additional water entities to spawn.
	 */
	public Water(float x, float y, int watersToSpawn) {
		super(x, y, CODE);
		setDirection(RIGHT);
		this.watersToSpawn = watersToSpawn;
		this.lifeSpan = 70;
	}

	// Getters and Setters

	/**
	 * Returns the entity currently captured by the water, if any.
	 *
	 * @return the captured entity, or {@code null} if no entity is captured.
	 */
	public MovingEntity getCapturedEntity() {
		return capturedEntity;
	}

	/**
	 * Sets the entity to be captured by the water.
	 *
	 * @param capturedEntity the entity to capture.
	 */
	public void setCapturedEntity(MovingEntity capturedEntity) {
		this.capturedEntity = capturedEntity;
	}

	// Other Methods

	/**
	 * Deletes the water entity from the level and releases any captured entity. If
	 * an enemy is captured, it is killed; if a player is captured, they are no
	 * longer stunned.
	 */
	private void delete() {
		Model.getInstance().getCurrentLevel().getBubbleManager().removeWater(this);
		if (capturedEntity != null) {
			if (capturedEntity instanceof Enemy enemy)
				enemy.kill();
			else if (capturedEntity instanceof Player player)
				player.setStunned(false);
		}
	}

	/**
	 * /** Updates the position of the water entity. It attempts to move left or
	 * right based on the current direction and spawns additional water entities if
	 * applicable. If the water entity cannot move, it will attempt to change
	 * direction or be deleted.
	 */
	private void updatePosition() {
		if (watersToSpawn > 0) {
			Model.getInstance().getCurrentLevel().getBubbleManager().addWater(new Water(x, y, watersToSpawn - 1));
			watersToSpawn = 0;
		}

		if (!HelpMethods.isEntityGrounded(this)) {
			setY(y + TILE_SIZE);
			return;
		}

		float newX = direction == LEFT ? x - TILE_SIZE : x + TILE_SIZE;
		if (HelpMethods.canMoveHere(newX, y, width, height)) {
			setX(newX);
		} else {
			// Try moving in the opposite direction if the first direction fails
			newX = direction == LEFT ? x + TILE_SIZE : x - TILE_SIZE;
			if (HelpMethods.canMoveHere(newX, y, width, height)) {
				direction = direction == LEFT ? RIGHT : LEFT;
				setX(newX);
			} else {
				delete();
			}
		}
		if (capturedEntity != null) {
			capturedEntity.setPosition(x, y - 7);
		}
	}

	/**
	 * Updates the state of the water entity. This includes decreasing its lifespan,
	 * checking if it should be deleted, handling boundary conditions, and updating
	 * its position.
	 */
	@Override
	public void updateEntity() {

		lifeSpan--;
		if (lifeSpan <= 0) {
			delete();
		}
		if (y == Level.GAME_HEIGHT) {
			setY(-TILE_SIZE);
		}

		updatePosition();
	}
}
