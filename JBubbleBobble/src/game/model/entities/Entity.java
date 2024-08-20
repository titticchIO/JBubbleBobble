package game.model.entities;

import static game.model.tiles.Tile.TILE_SIZE;

import java.util.List;
import java.util.Optional;

/**
 * 
 */
public abstract class Entity {

	protected float x, y;
	/**
	 * Width and height of the entity relative to coordinates
	 */
	protected float width, height;

	private String code;

	public Entity(float x, float y, String code) {
		this.x = x;
		this.y = y;
		this.code = code;
		width = TILE_SIZE - 1;
		height = TILE_SIZE - 1;
	}

	/**
	 * Public entity constructor
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Entity(float x, float y, float width, float height, String code) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.code = code;
	}

	/**
	 * Setters
	 */

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
	 * @param width
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * @param height
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	public Entity setPosition(float x, float y) {
		setX(x);
		setY(y);

		return this;
	}

	/**
	 * Getters
	 */

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

	public String getCode() {
		return code;
	}
	
	/**
	 * 
	 * @param entity
	 * @return	distance from another Entity
	 */
	public float getDistanceFrom(Entity entity) {
	    // Calcola la distanza minima sull'asse X
	    float xDist = Math.max(0, Math.max(entity.x - (this.x + this.width), this.x - (entity.x + entity.width)));
	    
	    // Calcola la distanza minima sull'asse Y
	    float yDist = Math.max(0, Math.max(entity.y - (this.y + this.height), this.y - (entity.y + entity.height)));

	    // Se c'è una sovrapposizione su uno dei due assi, la distanza sarà zero
	    if (xDist == 0 || yDist == 0) {
	        return Math.max(xDist, yDist);
	    }
	    
	    // Se non c'è sovrapposizione, calcola la distanza euclidea tra i punti più vicini dei bordi delle entità
	    return (float) Math.sqrt(xDist * xDist + yDist * yDist);
	}

	
	
	/**
	 * @return array with entity's coordinates
	 */
	public float[] getPosition() {
		return new float[] { x, y };
	}

	public float[][] getTopPoints() {
		return new float[][] { { x, y }, { x + width, y } };
	}

	public float[][] getBottomPoints() {
		return new float[][] { { x, y + height }, { x + width, y + height } };
	}

	/**
	 * @return four entity's edges
	 */
	public float[][] getPoints() {
		return new float[][] { { x, y }, { x + width, y }, { x, y + height }, { x + width, y + height } };
	}

	/**
	 * Method that checks any top collision with another entity
	 * 
	 * @param entity
	 * @return boolean
	 */
	public boolean topHit(Entity entity) {
        return this.y >= entity.y + entity.height &&
               this.y < entity.y + entity.height + this.height &&
               this.x < entity.x + entity.width &&
               this.x + this.width > entity.x;
    }

	/**
	 * Method that checks any bottom collision with another entity
	 * 
	 * @param entity
	 * @return boolean
	 */
	public boolean bottomHit(Entity entity) {
        return this.y + this.height <= entity.y &&
               this.y + this.height > entity.y - this.height &&
               this.x < entity.x + entity.width &&
               this.x + this.width > entity.x;
    }

	/**
	 * Method that checks any collision with another entity
	 * 
	 * @param entity
	 * @return boolean
	 */
	public boolean hit(Entity entity) {
        return this.x < entity.x + entity.width &&
               this.x + this.width > entity.x &&
               this.y < entity.y + entity.height &&
               this.y + this.height > entity.y;
    }
	
	public static <T extends Entity, U extends Entity> Optional<U> checkCollision(T entity, List<U> list) {
		return list.stream().filter(x -> x.hit(entity)).findFirst();
	}

	public static <T extends Entity, U extends Entity> Optional<U> checkTopCollision(T entity, List<U> list) {
		return list.stream().filter(x -> x.bottomHit(entity)).findFirst();
	}

	public static <T extends Entity, U extends Entity> Optional<U> checkBottomCollision(T entity, List<U> list) {
		return list.stream().filter(x -> x.topHit(entity)).findFirst();
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
