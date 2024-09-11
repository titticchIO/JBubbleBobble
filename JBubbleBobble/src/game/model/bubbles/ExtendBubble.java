package game.model.bubbles;

import game.model.Model;
import game.model.Tile;
import game.model.entities.Player;

/**
 * The {@code ExtendBubble} class represents a specialized bubble that can
 * restore the player's lives.
 */
public class ExtendBubble extends Bubble {

	// Static Fields
	public static final char[] CODES = { '(', ')', '[', ']', '{', '}' };
	private static int codesIndex;

	// Constructors

	/**
	 * Constructs an {@code ExtendBubble} with default properties. The bubble rises
	 * and has an extended life span.
	 */
	public ExtendBubble() {
		super(0, 0, Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1, CODES[codesIndex]);
		rise(-0.3f);
		lifeSpan = 20000;
	}

	// Static Methods

	/**
	 * Increments the index to the next code in the {@code CODES} array. Resets to 0
	 * if the end of the array is reached.
	 */
	public static void incrementCodesIndex() {
		codesIndex = (codesIndex < CODES.length - 1) ? codesIndex + 1 : 0;
	}

	/**
	 * Resets the index to 0.
	 */
	public static void resetCodesIndex() {
		codesIndex = 0;
	}
	// Other Methods

	/**
	 * Pops the bubble, potentially healing the player if the last code is used. The
	 * bubble is removed from the game level.
	 */
	@Override
	public void pop() {
		if (codesIndex == CODES.length - 1)
			Player.getInstance().heal();
		Model.getInstance().getCurrentLevel().getBubbleManager().removeBubble(this);
	}
}
