package game.model.bubbles;

import game.model.Model;
import static game.model.tiles.Tile.TILE_SIZE;
import game.model.bubbles.special_effects.FireBall;
import game.model.tiles.Tile;

public class FireBubble extends Bubble {

	public FireBubble() {
		super(0, 0, Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1, "-");
		rise(-0.3f);
		lifeSpan *= 10;
	}

	@Override
	public void pop() {
		Model.getInstance().getCurrentLevel().getBubbleManager().removeBubble(this);
//		float xPos=(int)(x/TILE_SIZE)*TILE_SIZE;
		Model.getInstance().getCurrentLevel().getBubbleManager().addFireBall(new FireBall(x, y));

	}

	@Override
	public void updateEntity() {
		super.updateEntity();
	}
}
