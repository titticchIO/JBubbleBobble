package game.view;

import javax.swing.ImageIcon;

import game.model.Paths;
import game.model.enemies.Enemy.ColorState;
import game.model.entities.MovingEntity.Direction;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code AnimationAndImagesLoader} class is responsible for loading and
 * managing animations and images used in the game, including player animations,
 * entity animations, and other game assets like bubbles and enemies.
 * 
 * <p>
 * This class statically loads player animations and provides methods to
 * dynamically load animations and images for other entities. It supports
 * multiple entity types and includes methods for accessing animations and
 * images based on parameters such as direction and color state.
 * </p>
 */
public class AnimationAndImagesLoader {

	// Map to store the player animations loaded statically
	private static final Map<String, ImageIcon> playerAnimations = new HashMap<>();

	// Player animation file paths
	private static final String PLAYER_WALK_LEFT = "player/left_walk.gif";
	private static final String PLAYER_WALK_RIGHT = "player/right_walk.gif";
	private static final String PLAYER_STUN_LEFT = "player/stun_left.gif";
	private static final String PLAYER_STUN_RIGHT = "player/stun_right.gif";
	private static final String PLAYER_BUBBLE_LEFT = "player/bubble_left.gif";
	private static final String PLAYER_BUBBLE_RIGHT = "player/bubble_right.gif";

	// Static block to load player animations
	static {
		// Carica le animazioni del player staticamente
		playerAnimations.put("walk_left", loadImageIcon(PLAYER_WALK_LEFT));
		playerAnimations.put("walk_right", loadImageIcon(PLAYER_WALK_RIGHT));
		playerAnimations.put("stun_left", loadImageIcon(PLAYER_STUN_LEFT));
		playerAnimations.put("stun_right", loadImageIcon(PLAYER_STUN_RIGHT));
		playerAnimations.put("bubble_left", loadImageIcon(PLAYER_BUBBLE_LEFT));
		playerAnimations.put("bubble_right", loadImageIcon(PLAYER_BUBBLE_RIGHT));
	}

	/**
	 * Loads an {@code ImageIcon} from a given relative path.
	 *
	 * @param relativePath the relative path to the image
	 * @return the loaded {@code ImageIcon}, or {@code null} if the loading fails
	 */
	private static ImageIcon loadImageIcon(String relativePath) {
		String absolutePath = Paths.getAbsolutePath(relativePath);
		return new ImageIcon(absolutePath);
	}

	/**
	 * Retrieves a player animation based on a key.
	 *
	 * @param animationKey the key representing the player animation (e.g.,
	 *                     "walk_left")
	 * @return the corresponding {@code ImageIcon}, or {@code null} if not found
	 */
	private static ImageIcon getPlayerAnimation(String animationKey) {
		return playerAnimations.get(animationKey);
	}

	/**
	 * Retrieves a player animation based on a key.
	 *
	 * @param animationKey the key representing the player animation (e.g.,
	 *                     "walk_left")
	 * @return the corresponding {@code ImageIcon}, or {@code null} if not found
	 */
	public static Image getPlayerImage(String animationKey) {
		ImageIcon icon = getPlayerAnimation(animationKey);
		return icon != null ? icon.getImage() : null;
	}

	/**
	 * Dynamically loads an animation for a non-player entity from a given relative
	 * path.
	 *
	 * @param relativePath the relative path to the entity animation
	 * @return the loaded {@code ImageIcon}, or {@code null} if the loading fails
	 */
	private static ImageIcon loadEntityAnimation(String relativePath) {
		return loadImageIcon(relativePath);
	}

	/**
	 * Loads and retrieves the image for a non-player entity.
	 *
	 * @param relativePath the relative path to the entity image
	 * @return the corresponding {@code Image}, or {@code null} if not found
	 */
	public static Image loadEntityImage(String relativePath) {
		ImageIcon icon = loadEntityAnimation(relativePath);
		return icon != null ? icon.getImage() : null;
	}

	/**
	 * Loads the image of a bubble popping animation.
	 *
	 * @return the bubble popping {@code Image}, or {@code null} if not found
	 */
	public static Image loadBubblePoppingImage() {
		return loadEntityImage("/bubbles/bubble_pops.gif");
	}

	/**
	 * Loads the image of a bubbled enemy based on the enemy code.
	 *
	 * @param code the character code representing the enemy
	 * @return the bubbled enemy {@code Image}, or {@code null} if not found
	 */
	public static Image loadBubbleEnemyImage(char code) {
		return loadEntityImage(Paths.getPath(code) + "bubbled.gif");
	}

	/**
	 * Loads the image of an enemy based on the enemy code, direction, and color
	 * state.
	 *
	 * @param code      the character code representing the enemy
	 * @param direction the direction in which the enemy is facing
	 * @param color     the color state of the enemy
	 * @return the enemy {@code Image}, or {@code null} if not found
	 */
	public static Image loadEnemyImage(char code, Direction direction, ColorState color) {
		if (code == 'I' || code == 'U')
			return loadEntityImage(Paths.getPath(code) + "default-" + color.name().toLowerCase() + ".gif");
		return loadEntityImage(
				Paths.getPath(code) + direction.name().toLowerCase() + "-" + color.name().toLowerCase() + ".gif");
	}

	/**
	 * Loads the image of a dead enemy based on the enemy code.
	 *
	 * @param code the character code representing the enemy
	 * @return the dead enemy {@code Image}, or {@code null} if not found
	 */
	public static Image loadDeadEnemyImage(char code) {
		return loadEntityImage(Paths.getPath(code) + "dead.gif");
	}

	/**
	 * Retrieves an image using a character code.
	 *
	 * @param imageCode the character code representing the image
	 * @return the {@code BufferedImage}, or {@code null} if not found
	 */
	public static BufferedImage getImage(char imageCode) {
		BufferedImage img;
		if (Character.isAlphabetic(imageCode))
			img = ImageLoader.importImg(Paths.getPath(imageCode) + "image.png");
		else
			img = ImageLoader.importImg(Paths.getPath(imageCode) + ".png");
		return img;
	}

	/**
	 * Retrieves an image using a character type and a position.
	 *
	 * @param type     the character type
	 * @param position the position string associated with the image
	 * @return the {@code BufferedImage}, or {@code null} if not found
	 */
	public static BufferedImage getImage(Character type, String position) {
		return ImageLoader.importImg(Paths.getPath(type) + position + ".png");
	}

}
