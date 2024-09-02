package game.model.bubbles;

import game.model.Model;
import game.model.tiles.Tile;
import game.model.bubbles.special_effects.Bolt;
import game.model.entities.Entity;
import game.model.entities.Player;

public class ThunderBubble extends Bubble {
	public static final char CODE = '+';

	public ThunderBubble() {
		super(0, 0, Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1, CODE);
		rise(-0.2f);
		lifeSpan *= 10;
	}

	@Override
	public void pop() {
		Model.getInstance().getCurrentLevel().getBubbleManager().removeBubble(this);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (Entity.checkCollision(this, Player.getInstance()).isPresent()) {
			Model.getInstance().getCurrentLevel().getBubbleManager().addBolt(new Bolt(x, y));
			pop();
		}
	}
}
