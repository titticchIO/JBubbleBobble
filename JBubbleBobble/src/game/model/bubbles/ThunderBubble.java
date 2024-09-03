package game.model.bubbles;

import game.model.Model;
import game.model.tiles.Tile;
import game.model.bubbles.special_effects.Bolt;
import game.model.entities.Entity;
import game.model.entities.Player;

/**
 * The {@code ThunderBubble} class represents a bubble that spawns a lightning
 * bolt when it collides with the player.
 */
public class ThunderBubble extends Bubble {

	// Static Fields
	public static final char CODE = '+';

	// Constructors

	/**
	 * Constructs a {@code ThunderBubble} with default parameters. The bubble rises
	 * slowly and has an extended lifespan.
	 */
	public ThunderBubble() {
		super(0, 0, Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1, CODE);
		rise(-0.2f);
		lifeSpan *= 10;

	}

	// Methods

	/**
	 * Pops the bubble, removing it from the game.
	 */
	@Override
	public void pop() {
		Model.getInstance().getCurrentLevel().getBubbleManager().removeBubble(this);
	}

	/**
	 * Updates the state of the bubble. If the bubble collides with the player, it
	 * spawns a lightning bolt and then pops.
	 */
	@Override
	public void updateEntity() {
		super.updateEntity();
		Entity.checkCollision(this, Player.getInstance()).ifPresent(collision -> {
			Model.getInstance().getCurrentLevel().getBubbleManager().addBolt(new Bolt(x, y));
			pop();
		});
	}
}
