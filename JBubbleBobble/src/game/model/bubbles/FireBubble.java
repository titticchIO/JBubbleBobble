package game.model.bubbles;

import game.model.Model;
import game.model.Tile;
import game.model.bubbles.special_effects.FireBall;

/**
 * The {@code FireBubble} class represents a bubble that, when popped, releases
 * a fire ball. The bubble has an extended life span and rises upward.
 */
public class FireBubble extends Bubble {

	// Static Fields
	public static final char CODE = '-';

	// Constructors

	/**
	 * Constructs a {@code FireBubble} with default properties. The bubble rises and
	 * has an extended life span.
	 */
	public FireBubble() {
		super(0, 0, Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1, CODE);
		rise(-0.3f);
		lifeSpan = 20000;
	}

	// Other Methods

	/**
	 * Pops the bubble, removing it from the game and releasing a fire ball.
	 */
	@Override
	public void pop() {
		Model.getInstance().getCurrentLevel().getBubbleManager().removeBubble(this);
		if (lifeSpan!=0)
			Model.getInstance().getCurrentLevel().getBubbleManager().addFireBall(new FireBall(x, y));
	}
}
