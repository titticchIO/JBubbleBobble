package game.model.bubbles;

import game.model.Model;
import game.model.entities.Player;
import game.model.tiles.Tile;

public class ExtendBubble extends Bubble {
	private static final char[] CODES = { '(', ')', '[', ']', '{', '}'};
	private static int codesIndex;

	public static void incrementCodesIndex() {
		codesIndex = (codesIndex < CODES.length-1) ? codesIndex + 1 : 0;
	}

	public ExtendBubble() {
		super(0, 0, Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1, CODES[codesIndex]);
		rise(-0.3f);
		lifeSpan *= 10;
	}

	@Override
	public void pop() {
		if (codesIndex == CODES.length-1)
			Player.getInstance().heal();
		Model.getInstance().getCurrentLevel().getBubbleManager().removeBubble(this);
	}

}
