package game.model.tiles;

import game.model.entities.Entity;

public class Tile extends Entity {

	public final static int TILE_SIZE = 16;

	public Tile(float x, float y, String code) {
		super(x, y, TILE_SIZE, TILE_SIZE, code);
	}
}
