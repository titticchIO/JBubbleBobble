package game.model.tiles;

import game.model.entities.Entity;

/**
 * The {@code Tile} represents the blocks that form the level, with which the
 * entities collide.
 */
public class Tile extends Entity {

	public final static int TILE_SIZE = 16;

	/**
	 * Constructor for Tile
	 * 
	 * @param x    the x coordinate of the Tile
	 * @param y    the y coordinate of the Tile
	 * @param code the type of tile
	 */
	public Tile(float x, float y, char code) {
		super(x, y, TILE_SIZE, TILE_SIZE, code);
	}
}
