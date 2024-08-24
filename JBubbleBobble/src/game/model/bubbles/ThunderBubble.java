package game.model.bubbles;

import game.model.Model;
import game.model.tiles.Tile;
import game.model.bubbles.special_effects.Bolt;

public class ThunderBubble extends Bubble {

	public ThunderBubble() {
		super(0, 0, Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1, "+");
		rise(-0.1f);
		lifeSpan *= 10;
	}

	@Override
	public void pop() {
		System.out.println("Popped");
		Model.getInstance().getCurrentLevel().getBubbleManager().addBolt(new Bolt(x, y));
		Model.getInstance().getCurrentLevel().getBubbleManager().removeBubble(this);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
	}
}
