package game.model.tiles;

import game.model.entities.Entity;

public class Tile extends Entity {
	//private String type;
	private final String type = "#";

	public Tile(float x, float y, float width, float height, String imageCode) {
		super(x, y, width, height, imageCode);
	}
	
	public String getType() {
		return type;
	}
}
