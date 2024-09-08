package game.model.bubbles;

import java.util.Timer;
import java.util.TimerTask;
import game.model.Model;
import game.model.Tile;
import game.model.entities.Player;

/**
 * The {@code SpecialBubble} class represents a bubble that, when popped, grants
 * the player's ability to shoot for a limited time.
 */
public class SpecialBubble extends Bubble {

	// Static Fields
	public static final char CODE = '%';

	// Constructors

	/**
	 * Constructs a {@code SpecialBubble} with default parameters. The bubble rises
	 * slowly and has an extended life span.
	 */
	public SpecialBubble() {
		super(0, 0, Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1, CODE);
		rise(-0.1f);
		lifeSpan = 20000;
	}

	// Overridden Methods

	/**
	 * Pops the bubble, enabling the player's shooting ability for a limited time.
	 * Removes the bubble from the game.
	 */
	@Override
	public void pop() {
		Model.getInstance().getCurrentLevel().getBubbleManager().removeBubble(this);
		if (lifeSpan != 0) {
			Player.getInstance().setSpecialBubbleActive(true);
			new Timer("SpecialBubble").schedule(new TimerTask() {
				@Override
				public void run() {
					Player.getInstance().setSpecialBubbleActive(false);
				}
			}, 10000);
		} // Enables shooting for 10 seconds
	}

	/**
	 * Updates the state of the bubble. Calls the superclass method to handle
	 * general bubble behavior.
	 */
	@Override
	public void updateEntity() {
		super.updateEntity();
	}
}
