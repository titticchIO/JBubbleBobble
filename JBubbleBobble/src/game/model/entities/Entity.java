package game.model.entities;

import static game.model.tiles.Tile.TILE_SIZE;
import java.util.List;
import java.util.Optional;

/**
 * The {@code Entity} class serves as the base class for all game entities. It
 * provides the basic properties and methods needed for collision detection,
 * positioning, and size management of entities in the game world.
 */
public abstract class Entity {

	/**
	 * Checks if two entities collide with each other.
	 * 
	 * @param <T>         The type of the first entity.
	 * @param <U>         The type of the second entity.
	 * @param entity      The first entity.
	 * @param otherEntity The second entity to check collision against.
	 * @return {@code true} if the entities collide, {@code false} otherwise.
	 */
	public static <T extends Entity, U extends Entity> Optional<U> checkCollision(T entity, U otherEntity) {
		if (entity.hit(otherEntity))
			return Optional.of(otherEntity);
		return Optional.empty();
	}

	/**
	 * Checks if an entity collides with any entity in a list.
	 * 
	 * @param <T>    The type of the entity to check.
	 * @param <U>    The type of entities in the list.
	 * @param entity The entity to check for collision.
	 * @param list   The list of entities to check against.
	 * @return An {@code Optional} containing the first entity in the list that
	 *         collides with the given entity, or {@code Optional.empty()} if no
	 *         collision is detected.
	 */
	public static <T extends Entity, U extends Entity> Optional<U> checkCollision(T entity, List<U> list) {
		return list.stream().filter(x -> x.hit(entity)).findFirst();
	}

	/**
	 * Checks if an entity collides with the bottom of any entity in a list.
	 * 
	 * @param <T>    The type of the entity to check.
	 * @param <U>    The type of entities in the list.
	 * @param entity The entity to check for bottom collision.
	 * @param list   The list of entities to check against.
	 * @return An {@code Optional} containing the first entity in the list that
	 *         collides with the bottom of the given entity, or
	 *         {@code Optional.empty()} if no collision is detected.
	 */
	public static <T extends Entity, U extends Entity> Optional<U> checkBottomCollision(T entity, List<U> list) {
		return list.stream().filter(x -> x.topHit(entity)).findFirst();
	}

	/**
	 * Checks if an entity collides with the top of any entity in a list.
	 * 
	 * @param <T>    The type of the entity to check.
	 * @param <U>    The type of entities in the list.
	 * @param entity The entity to check for top collision.
	 * @param list   The list of entities to check against.
	 * @return An {@code Optional} containing the first entity in the list that
	 *         collides with the top of the given entity, or
	 *         {@code Optional.empty()} if no collision is detected.
	 */
	public static <T extends Entity, U extends Entity> Optional<U> checkTopCollision(T entity, List<U> list) {
		return list.stream().filter(x -> x.bottomHit(entity)).findFirst();
	}

	public static <T extends Entity, U extends Entity> Optional<U> checkCollisions(List<T> list1, List<U> list2) {
		return list1.stream().flatMap(x -> list2.stream().filter(x::hit)).findFirst();
	}

	// Position and dimensions of the entity
	protected float x, y;
	protected float width, height;

	// Unique code identifier for the entity
	private char code;

	/**
	 * Constructs an {@code Entity} with specified position, dimensions, and code.
	 * 
	 * @param x      The x-coordinate of the entity.
	 * @param y      The y-coordinate of the entity.
	 * @param width  The width of the entity.
	 * @param height The height of the entity.
	 * @param code   The unique code representing the entity.
	 */
	public Entity(float x, float y, float width, float height, char code) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.code = code;
	}

	/**
	 * Constructs an {@code Entity} with specified position and code. The width and
	 * height are set to the default tile size minus 1.
	 * 
	 * @param x    The x-coordinate of the entity.
	 * @param y    The y-coordinate of the entity.
	 * @param code The unique code representing the entity.
	 */
	public Entity(float x, float y, char code) {
		this.x = x;
		this.y = y;
		this.code = code;
		this.width = TILE_SIZE - 1;
		this.height = TILE_SIZE - 1;
	}

	/**
	 * Gets the x-coordinate of the entity.
	 * 
	 * @return The x-coordinate.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Gets the y-coordinate of the entity.
	 * 
	 * @return The y-coordinate.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Gets the width of the entity.
	 * 
	 * @return The width.
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Gets the height of the entity.
	 * 
	 * @return The height.
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Gets the unique code representing the entity.
	 * 
	 * @return The entity's code.
	 */
	public char getCode() {
		return code;
	}

	/**
	 * Gets the position of the entity as an array.
	 * 
	 * @return A float array containing the x and y coordinates of the entity.
	 */
	public float[] getPosition() {
		return new float[] { x, y };
	}

	/**
	 * Sets the x-coordinate of the entity.
	 * 
	 * @param x The new x-coordinate.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Sets the y-coordinate of the entity.
	 * 
	 * @param y The new y-coordinate.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Sets the position of the entity.
	 * 
	 * @param x The new x-coordinate.
	 * @param y The new y-coordinate.
	 */
	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}

	/**
	 * Sets the height of the entity.
	 * 
	 * @param height The new height.
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * Sets the width of the entity.
	 * 
	 * @param width The new width.
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * Checks if this entity collides with another entity.
	 * 
	 * @param entity The other entity to check collision against.
	 * @return {@code true} if there is a collision, {@code false} otherwise.
	 */
	public boolean hit(Entity entity) {
		return this.x < entity.x + entity.width && this.x + this.width > entity.x && this.y < entity.y + entity.height
				&& this.y + this.height > entity.y;
	}

	/**
	 * Checks if this entity has collided with the top of another entity.
	 * 
	 * @param entity The other entity to check top collision against.
	 * @return {@code true} if there is a top collision, {@code false} otherwise.
	 */
	public boolean topHit(Entity entity) {
		return this.y >= entity.y + entity.height && this.y < entity.y + entity.height + this.height
				&& this.x < entity.x + entity.width && this.x + this.width > entity.x;
	}

	/**
	 * Checks if this entity has collided with the bottom of another entity.
	 * 
	 * @param entity The other entity to check bottom collision against.
	 * @return {@code true} if there is a bottom collision, {@code false} otherwise.
	 */
	public boolean bottomHit(Entity entity) {
		return this.y + this.height <= entity.y && this.y + this.height > entity.y - this.height
				&& this.x < entity.x + entity.width && this.x + this.width > entity.x;
	}

	/**
	 * Calculates the distance from this entity to another entity.
	 * 
	 * @param entity The other entity to calculate the distance from.
	 * @return The distance between the closest edges of the two entities.
	 */
	public float getDistanceFrom(Entity entity) {
		// Calculate minimum distance on the x-axis
		float xDist = Math.max(0, Math.max(entity.x - (this.x + this.width), this.x - (entity.x + entity.width)));

		// Calculate minimum distance on the y-axis
		float yDist = Math.max(0, Math.max(entity.y - (this.y + this.height), this.y - (entity.y + entity.height)));

		// If there is an overlap on either axis, the distance is zero
		if (xDist == 0 || yDist == 0) {
			return Math.max(xDist, yDist);
		}

		// If there is no overlap, calculate Euclidean distance between the closest
		// edges
		return (float) Math.sqrt(xDist * xDist + yDist * yDist);
	}

	/**
	 * Returns a hash code value for the object. This method is supported for the
	 * benefit of hash tables.
	 *
	 * @return a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		int k = 13;
		k = k * 17 + (int) x;
		k = k * 17 + (int) y;
		k = k * 17 + (int) width;
		k = k * 17 + (int) width;
		return k;
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * The {@code equals} method implements an equivalence relation on non-null
	 * object references.
	 * 
	 * @param obj the reference object with which to compare.
	 * @return {@code true} if this object is the same as the {@code obj} argument;
	 *         {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		return height == other.height && width == other.width && x == other.x && y == other.y;
	}
}
