package game.model.tiles;

import game.model.entities.Entity;

public class Tile extends Entity {

	public final static int TILE_SIZE = 16;

	private String type;

	public Tile(float x, float y, float width, float height, String type) {
		super(x, y, width, height, "#");
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
