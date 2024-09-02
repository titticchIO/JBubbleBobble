package game.model.bubbles.special_effects;

import static game.model.tiles.Tile.TILE_SIZE;
import static game.model.entities.MovingEntity.Direction.*;

import game.model.Fruit;
import game.model.Fruit.FruitType;
import game.model.HelpMethods;
import game.model.Model;
import game.model.enemies.Enemy;
import game.model.entities.MovingEntity;
import game.model.entities.Player;
import game.model.level.Level;

/**
 * The {@code Water} class represents a special effect entity that moves
 * horizontally across the level and has a limited lifespan. Upon expiration, it
 * may spawn a fruit.
 */
public class Water extends MovingEntity {

	// Static Fields
	public static final char CODE = '_';
	private MovingEntity capturedEntity;

	// Instance Fields
	private int watersToSpawn;
	private int lifeSpan;
	private FruitType fruitType;

	// Constructors

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
	 * Sets the type of fruit that will be spawned when the water expires.
	 *
	 * @param fruitType the type of fruit to spawn.
	 */
	public void setFruit(FruitType fruitType) {
		this.fruitType = fruitType;
	}

	// Other Methods

	/**
	 * Deletes the water entity from the level.
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
	 * Updates the position of the water entity. It attempts to move left or right
	 * based on the current direction and spawns additional water entities if
	 * applicable.
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
	 * checking if it should be deleted or spawn a fruit, and updating its position.
	 */
	@Override
	public void updateEntity() {

		lifeSpan--;
		if (lifeSpan <= 0) {
			if (fruitType != null) {
				Model.getInstance().getCurrentLevel().getFruitManager().addFruit(new Fruit(x, y, fruitType));
			}
			delete();
		}
		if (y == Level.GAME_HEIGHT) {
			setY(-TILE_SIZE);
		}

		updatePosition();
	}

	public MovingEntity getCapturedEntity() {
		return capturedEntity;
	}

	public void setCapturedEntity(MovingEntity capturedEntity) {
		this.capturedEntity = capturedEntity;
	}
}
