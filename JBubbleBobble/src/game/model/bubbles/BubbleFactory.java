package game.model.bubbles;

import java.util.List;
import java.util.Random;
import game.model.level.Level;
import game.model.tiles.Tile;

/**
 * Factory class responsible for creating different types of bubbles in the
 * game, such as PlayerBubble, SpecialBubble, and ExtendBubble.
 */
public class BubbleFactory {

	// List of spawn points (as floats) for bubble generation.
	private List<Float> bubblesSpawnPoints;

	/**
	 * Constructs a BubbleFactory with a given list of bubble spawn points.
	 *
	 * @param bubbleSpawnPoints A list of possible spawn points where bubbles can be
	 *                          created.
	 */
	public BubbleFactory(List<Float> bubbleSpawnPoints) {
		this.bubblesSpawnPoints = bubbleSpawnPoints;
	}

	/**
	 * Creates a PlayerBubble at the given coordinates and with the specified
	 * horizontal speed.
	 *
	 * @param x      The x-coordinate where the PlayerBubble will be created.
	 * @param y      The y-coordinate where the PlayerBubble will be created.
	 * @param xSpeed The horizontal speed of the PlayerBubble.
	 * @return A new PlayerBubble object.
	 */
	public PlayerBubble createPlayerBubble(float x, float y, float xSpeed) {
		return new PlayerBubble(x, y, Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1, xSpeed, -0.3f);
	}

	/**
	 * Creates a randomly selected special bubble (Fire, Water, Thunder, or
	 * SpecialBubble). The special bubble is positioned at one of the spawn points.
	 *
	 * @return A new SpecialBubble object or null if no spawn points are available.
	 */
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
		}
		return null;
	}

	/**
	 * Takes an existing Bubble and sets it as a special bubble, positioning it at a
	 * random spawn point.
	 *
	 * @param bubble The Bubble object to be set as a special bubble.
	 * @return The modified Bubble object, or null if no spawn points are available.
	 */
	public Bubble createSpecialBubble(Bubble bubble) {

		if (bubblesSpawnPoints.size() > 0) {
			setSpecialBubblePosition(bubble);
			return bubble;
		}
		return null;
	}

	/**
	 * Creates an ExtendBubble and positions it at one of the available spawn
	 * points.
	 *
	 * @return A new ExtendBubble object or null if no spawn points are available.
	 */
	public ExtendBubble createExtendBubble() {
		if (bubblesSpawnPoints.size() > 0) {
			ExtendBubble newExtendBubble = new ExtendBubble();
			setSpecialBubblePosition(newExtendBubble);
			return newExtendBubble;
		}
		return null;

	}

	/**
	 * Sets the position of a special bubble at a random spawn point and above the game height.
	 *
	 * @param bubble The bubble whose position will be set.
	 */
	private void setSpecialBubblePosition(Bubble bubble) {
		bubble.setPosition(bubblesSpawnPoints.get(new Random().nextInt(bubblesSpawnPoints.size())),
				Level.GAME_HEIGHT + Tile.TILE_SIZE);
	}
}