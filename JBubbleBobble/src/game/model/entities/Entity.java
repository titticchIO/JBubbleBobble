package game.model.entities;

import static game.model.tiles.Tile.TILE_SIZE;
import java.util.List;
import java.util.Optional;

/**
 * This class describes all game entities
 */
public abstract class Entity {

	/**
	 * Checks collision with other entity
	 * 
	 * @param <T>
	 * @param <U>
	 * @param entity
	 * @param otherEntity
	 * @return
	 */
	public static <T extends Entity, U extends Entity> boolean checkCollision(T entity, U otherEntity) {
		return entity.hit(otherEntity);
	}

	/**
	 * Checks collision with list of entities
	 * 
	 * @param <T>
	 * @param <U>
	 * @param entity
	 * @param list
	 * @return
	 */
	public static <T extends Entity, U extends Entity> Optional<U> checkCollision(T entity, List<U> list) {
		return list.stream().filter(x -> x.hit(entity)).findFirst();
	}

	/**
	 * Checks bottom collision with list of entities
	 * 
	 * @param <T>
	 * @param <U>
	 * @param entity
	 * @param list
	 * @return
	 */
	public static <T extends Entity, U extends Entity> Optional<U> checkBottomCollision(T entity, List<U> list) {
		return list.stream().filter(x -> x.topHit(entity)).findFirst();
	}

	/**
	 * Checks top collision with list of entities
	 * 
	 * @param <T>
	 * @param <U>
	 * @param entity
	 * @param list
	 * @return
	 */
	public static <T extends Entity, U extends Entity> Optional<U> checkTopCollision(T entity, List<U> list) {
		return list.stream().filter(x -> x.bottomHit(entity)).findFirst();
	}

	protected float x, y;
	protected float width, height;
	private String code;

	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param code
	 */
	public Entity(float x, float y, float width, float height, String code) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.code = code;
	}

	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 * @param code
	 */
	public Entity(float x, float y, String code) {
		this.x = x;
		this.y = y;
		this.code = code;
		width = TILE_SIZE - 1;
		height = TILE_SIZE - 1;
	}

	/**
	 * @return x coordinate
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return y coordinate
	 */
	public float getY() {
		return y;
	}

	/**
	 * @return entity's width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @return entity's height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @return entity's code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return array with entity's coordinates
	 */
	public float[] getPosition() {
		return new float[] { x, y };
	}

	/**
	 * @return top entity's edges
	 */
	public float[][] getTopVertexes() {
		return new float[][] { { x, y }, { x + width, y } };
	}

	/**
	 * @return bottom entity's edges
	 */
	public float[][] getBottomVertexes() {
		return new float[][] { { x, y + height }, { x + width, y + height } };
	}

	/**
	 * @return all entity's edges
	 */
	public float[][] getVertexes() {
		return new float[][] { { x, y }, { x + width, y }, { x, y + height }, { x + width, y + height } };
	}

	/**
	 * @param x
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @param y
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}

	/**
	 * @param height
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * @param width
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * Method that checks any top collision with another entity
	 * 
	 * @param entity
	 * @return if there is a top hit
	 */
	public boolean topHit(Entity entity) {
		return this.y >= entity.y + entity.height && this.y < entity.y + entity.height + this.height
				&& this.x < entity.x + entity.width && this.x + this.width > entity.x;
	}

	/**
	 * Checks bottom collision with other entity
	 * 
	 * @param entity
	 * @return if there is a bottom hit
	 */
	public boolean bottomHit(Entity entity) {
		return this.y + this.height <= entity.y && this.y + this.height > entity.y - this.height
				&& this.x < entity.x + entity.width && this.x + this.width > entity.x;
	}

	/**
	 * Method that checks any collision with another entity
	 * 
	 * @param entity
	 * @return if there is a hit
	 */
	public boolean hit(Entity entity) {
		return this.x < entity.x + entity.width && this.x + this.width > entity.x && this.y < entity.y + entity.height
				&& this.y + this.height > entity.y;
	}

	/**
	 * Calculates distance from another entity
	 * 
	 * @param entity
	 * @return the distance from another Entity
	 */
	public float getDistanceFrom(Entity entity) {
		// Calculates minimum distance on x axis
		float xDist = Math.max(0, Math.max(entity.x - (this.x + this.width), this.x - (entity.x + entity.width)));

		// Calculates minimum distance on y axis
		float yDist = Math.max(0, Math.max(entity.y - (this.y + this.height), this.y - (entity.y + entity.height)));

		// If there is an overlap on one of the two axis distance is zero
		if (xDist == 0 || yDist == 0) {
			return Math.max(xDist, yDist);
		}

		// If there is no overlap, calculates Euclidean distance between closest
		// entities edges
		return (float) Math.sqrt(xDist * xDist + yDist * yDist);
	}

	@Override
	public int hashCode() {
		int k = 13;
		k = k * 17 + (int) x;
		k = k * 17 + (int) y;
		k = k * 17 + (int) width;
		k = k * 17 + (int) width;
		return k;
	}

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
