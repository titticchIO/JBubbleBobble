package game.model.tiles;

import game.model.entities.Entity;

public class Tile extends Entity {

	private String type;

	public Tile(float x, float y, float width, float height, String type) {
		super(x, y, width, height,"#");
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
