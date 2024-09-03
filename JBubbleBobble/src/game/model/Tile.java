package game.model;

public class Tile extends Entity {

	public final static int TILE_SIZE = 16;

	public Tile(float x, float y, char code) {
		super(x, y, TILE_SIZE, TILE_SIZE, code);
	}
}
