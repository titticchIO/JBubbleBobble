package game.model.bubbles;

import java.util.Timer;
import java.util.TimerTask;

import game.model.Model;
import game.model.entities.Player;
import game.model.tiles.Tile;

public class SpecialBubble extends Bubble {
	public static final char CODE = '%';

	public SpecialBubble() {
		super(0, 0, Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1, CODE);
		rise(-0.1f);
		lifeSpan *= 10;
	}

	@Override
	public void pop() {
		Player.getInstance().setShooting(true);
		new Timer("SpecialBubble").schedule(new TimerTask() {
			@Override
			public void run() {
				Player.getInstance().setShooting(false);
			}
		}, 10000);

		Model.getInstance().getCurrentLevel().getBubbleManager().removeBubble(this);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
	}

}
