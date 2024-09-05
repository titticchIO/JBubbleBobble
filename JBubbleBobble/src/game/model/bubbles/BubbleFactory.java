package game.model.bubbles;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import game.model.Model;
import game.model.level.Level;
import game.model.tiles.Tile;

public class BubbleFactory {

	private List<Float> bubblesSpawnPoints;

	public BubbleFactory(List<Float> bubbleSpawnPoints) {
		this.bubblesSpawnPoints = bubbleSpawnPoints;
	}

	public PlayerBubble createPlayerBubble(float x, float y, float xSpeed) {
		PlayerBubble pb = new PlayerBubble(x, y, Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1, xSpeed, -0.3f);
		return pb;
	}

	public Bubble createSpecialBubble() {

		if (bubblesSpawnPoints.size() > 0) {
			Bubble specialBubble = switch (new Random().nextInt(4)) {

			case 0 -> new FireBubble();
			case 1 -> new WaterBubble();
			case 2 -> new SpecialBubble();
			default -> new ThunderBubble();
			};
			setSpecialBubblePosition(specialBubble);
			return specialBubble;
		} else
			return null;
	}

	public Bubble createSpecialBubble(Bubble bubble) {

		if (bubblesSpawnPoints.size() > 0) {
			setSpecialBubblePosition(bubble);
			return bubble;
		} else
			return null;
	}

	public ExtendBubble createExtendBubble() {
		if (bubblesSpawnPoints.size() > 0) {
			ExtendBubble newExtendBubble = new ExtendBubble();
			setSpecialBubblePosition(newExtendBubble);
			return newExtendBubble;
		} else
			return null;

	}

	private void setSpecialBubblePosition(Bubble bubble) {
		bubble.setPosition(bubblesSpawnPoints.get(new Random().nextInt(bubblesSpawnPoints.size())),
				Level.GAME_HEIGHT + Tile.TILE_SIZE);
	}
}