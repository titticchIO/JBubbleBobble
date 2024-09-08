package game.model;

import game.model.entities.Entity;
import game.model.entities.MovingEntity;
import game.model.level.Level;

/**
 * The {@code HelpMethods} class provides utility methods related to level
 * physics, collisions, and entity movements. These methods help determine
 * whether an entity can move to a specific location or if an entity is
 * colliding with solid tiles in the game world. The class includes functions to
 * check if an entity is inside a wall, grounded, or adjusting its position to
 * fit under a roof or above the floor.
 */
public class HelpMethods {
	/**
	 * Checks if an entity can move to a given position. It verifies that no part of
	 * the entity's bounding box (defined by x, y, width, and height) intersects
	 * with solid tiles.
	 * 
	 * @param x      The x-coordinate of the entity's top-left corner.
	 * @param y      The y-coordinate of the entity's top-left corner.
	 * @param width  The width of the entity.
	 * @param height The height of the entity.
	 * @return true if the entity can move to the specified position without
	 *         intersecting solid tiles, false otherwise.
	 */
	public static boolean canMoveHere(float x, float y, float width, float height) {
		if (!isSolid(x, y) && !isSolid(x + width, y) && !isSolid(x, y + height) && !isSolid(x + width, y + height))
			return true;
		return false;
	}

	/**
	 * Determines if the specified position (x, y) is a solid tile. The method
	 * checks if the coordinates are within the boundaries of the level and checks
	 * the level data to see if the tile is marked as solid.
	 * 
	 * @param x The x-coordinate to check.
	 * @param y The y-coordinate to check.
	 * @return true if the position is solid, false otherwise.
	 */
	private static boolean isSolid(float x, float y) {
		char[][] lvlData = Model.getInstance().getCurrentLevel().getLvlData();

		if (x < 0 || x >= Level.GAME_WIDTH) {
//			System.out.println("OUT OF BOUNDS");
			return true;
		}
		if (y < 0 || y >= Level.GAME_HEIGHT) {
//			System.out.println("OUT OF BOUNDS");
			return false;
		}

		float xIndex = x / Tile.TILE_SIZE;
		float yIndex = y / Tile.TILE_SIZE;

		char value = lvlData[(int) yIndex][(int) xIndex];

		return Character.isDigit(value);
	}

	/**
	 * Checks whether the entity is inside a wall. This method checks if any corner
	 * of the entity's bounding box intersects with solid tiles.
	 * 
	 * @param entity The entity to check.
	 * @return true if the entity is inside a wall, false otherwise.
	 */
	public static boolean isEntityInsideWall(Entity entity) {
		float x = entity.getX();
		float y = entity.getY();
		float width = entity.getWidth();
		float height = entity.getHeight();
		return isSolid(x, y) || isSolid(x + width, y) || isSolid(x, y + height) || isSolid(x + width, y + height);
	}

	/**
	 * Checks if an entity (defined by x, y, width, and height) intersects with any
	 * solid tiles.
	 * 
	 * @param x      The x-coordinate of the top-left corner of the entity.
	 * @param y      The y-coordinate of the top-left corner of the entity.
	 * @param width  The width of the entity.
	 * @param height The height of the entity.
	 * @return true if the entity is inside a wall, false otherwise.
	 */
	public static boolean isEntityInsideWall(float x, float y, float width, float height) {
		return isSolid(x, y) || isSolid(x + width, y) || isSolid(x, y + height) || isSolid(x + width, y + height);
	}

	/**
	 * Checks if the moving entity is grounded, meaning it is standing on a solid
	 * tile. The method verifies that at least one pixel along the bottom of the
	 * entity's bounding box (i.e., just below the entity's feet) is solid.
	 * 
	 * Additionally, the method ensures that the row of pixels directly above the
	 * entity's feet is non-solid to ensure that the entity is not inside a wall.
	 * 
	 * @param movingEntity The entity to check.
	 * @return true if the entity is grounded, false otherwise.
	 */
	public static boolean isEntityGrounded(MovingEntity movingEntity) {
		return isSolidHorizontalLine(movingEntity.getX(), movingEntity.getX() + movingEntity.getWidth(),
				movingEntity.getY() + movingEntity.getHeight() + 1)
				&& !isSolidHorizontalLine(movingEntity.getX(), movingEntity.getX() + movingEntity.getWidth(),
						movingEntity.getY() + movingEntity.getHeight() - 1);
	}

	/**
	 * Checks if any pixel along a horizontal line is solid, used for determining
	 * collisions along a horizontal path.
	 * 
	 * @param x1 The starting x-coordinate of the line.
	 * @param x2 The ending x-coordinate of the line.
	 * @param y  The y-coordinate of the line.
	 * @return true if any pixel along the line is solid, false otherwise.
	 */
	public static boolean isSolidHorizontalLine(float x1, float x2, float y) {
		for (float x = x1; x <= x2; x += 0.1) {
			if (isSolid(x, y))
				return true;
		}
		return false;
	}

	/**
	 * Checks if any pixel along a vertical line is solid, used for determining
	 * collisions along a vertical path.
	 * 
	 * @param x  The x-coordinate of the line.
	 * @param y1 The starting y-coordinate of the line.
	 * @param y2 The ending y-coordinate of the line.
	 * @return true if any pixel along the line is solid, false otherwise.
	 */
	public static boolean isSolidVerticalLine(float x, float y1, float y2) {
		for (float y = y1; y <= y2; y += 0.1) {
			if (isSolid(x, y))
				return true;
		}
		return false;
	}

}
