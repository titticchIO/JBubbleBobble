package game.model.bubbles;

import game.model.tiles.Tile;

public class WaterBubble extends Bubble{

	public WaterBubble(float x, float y, float width, float height, String code) {
		super(0, 0, Tile.TILE_SIZE-1, Tile.TILE_SIZE-1, "/");
	}
	
	@Override
	public void pop() {
		
	}
	
}
