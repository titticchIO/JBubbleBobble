package game.model.bubbles;

import static game.model.Tile.TILE_SIZE;

import game.model.Model;
import game.model.bubbles.special_effects.Water;

/**
 * The {@code WaterBubble} class represents a bubble that creates water upon
 * popping. The bubble rises slowly and has an extended life span.
 */
public class WaterBubble extends Bubble {
	public static final char CODE = '/';
	// Constructors

	/**
	 * Constructs a {@code WaterBubble} with default parameters. The bubble rises
	 * slowly and has an extended life span.
	 */
	public WaterBubble() {
		super(0, 0, TILE_SIZE - 1, TILE_SIZE - 1, CODE);
		rise(-0.2f);
		lifeSpan = 20000;

	}

	// Methods

	/**
	 * Pops the bubble, removing it from the game and adding water to the game at
	 * the bubble's position.
	 */
	@Override
	public void pop() {
		Model.getInstance().getCurrentLevel().getBubbleManager().removeBubble(this);
		if (lifeSpan != 0)
			Model.getInstance().getCurrentLevel().getBubbleManager()
					.addWater(new Water(roundPosition(x), roundPosition(y), 7));
	}
}
