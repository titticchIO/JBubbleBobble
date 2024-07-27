package game.model;

public class Settings {
	public final static int TILE_DEFAULT_SIZE = 16;
	public final static float SCALE = 1;
	public final static int HORIZONTAL_TILES = 30;
	public final static int VERTICAL_TILES = 24;
	public final static int TILE_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILE_SIZE * HORIZONTAL_TILES;
	public final static int GAME_HEIGHT = TILE_SIZE * VERTICAL_TILES;
}
