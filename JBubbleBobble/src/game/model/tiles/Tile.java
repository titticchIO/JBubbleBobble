package game.model.tiles;

import game.model.entities.Entity;

public class Tile extends Entity {

	public final static int TILE_SIZE = 16;

	private String type;

	public Tile(float x, float y, String type) {
		super(x, y, TILE_SIZE, TILE_SIZE, "#");
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
