package game.model.bubbles;

import game.model.Model;
import game.model.tiles.Tile;
import game.model.bubbles.special_effects.Bolt;

public class ThunderBubble extends Bubble {
	public static final char CODE = '+';

	public ThunderBubble() {
		super(0, 0, Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1, CODE);
		rise(-0.1f);
		lifeSpan *= 10;
	}

	@Override
	public void pop() {
		BubbleManager bm = Model.getInstance().getCurrentLevel().getBubbleManager();
		bm.addBolt(new Bolt(x, y));
		bm.removeBubble(this);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
	}
}
