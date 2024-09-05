package game.model.bubbles;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import game.model.Model;
import game.model.level.Level;
import game.model.tiles.Tile;

public class BubbleFactory {

	public PlayerBubble createPlayerBubble(float x, float y, float xSpeed) {
		PlayerBubble pb = new PlayerBubble(x, y, Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1, xSpeed, -0.3f);
		return pb;
	}

	public Bubble createSpecialBubble() {
		List<Float> bubblesSpawnPoints=Model.getInstance().getCurrentLevel().getBubblesSpawnPoints();
		Bubble specialBubble=null;
		if (bubblesSpawnPoints.size() != 0) {
			specialBubble = switch (new Random().nextInt(4)) {

			case 0 -> new FireBubble();
			case 1 -> new WaterBubble();
			case 2 -> new SpecialBubble();
			default -> new ThunderBubble();
			};
			specialBubble.setPosition(bubblesSpawnPoints.get(new Random().nextInt(bubblesSpawnPoints.size())), Level.GAME_HEIGHT+Tile.TILE_SIZE);
		}
		return specialBubble;
	}
	public Bubble createSpecialBubble(Bubble bubble) {
		List<Float> bubblesSpawnPoints=Model.getInstance().getCurrentLevel().getBubblesSpawnPoints();
		
		if (bubblesSpawnPoints.size() != 0) {
			bubble.setPosition(bubblesSpawnPoints.get(new Random().nextInt(bubblesSpawnPoints.size())), Level.GAME_HEIGHT+Tile.TILE_SIZE);
		}
		return bubble;
	}
}