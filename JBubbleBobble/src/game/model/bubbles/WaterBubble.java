package game.model.bubbles;

import game.model.Model;
import game.model.bubbles.special_effects.Water;

import static game.model.tiles.Tile.TILE_SIZE;

public class WaterBubble extends Bubble {

	public WaterBubble() {
		super(0, 0, TILE_SIZE - 1, TILE_SIZE - 1, "_");
		rise(-0.3f);
		lifeSpan *= 10;
	}

	private float roundPosition(float num) {
		return (int) (num / TILE_SIZE) * TILE_SIZE;
	}

	@Override
	public void pop() {
		Model.getInstance().getCurrentLevel().getBubbleManager().removeBubble(this);
		Model.getInstance().getCurrentLevel().getBubbleManager()
				.addWater(new Water(roundPosition(x), roundPosition(y), 7));
	}
}
