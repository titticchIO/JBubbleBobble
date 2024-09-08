package game.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code Paths} class is responsible for managing and providing file paths
 * based on specific characters. It includes mappings between characters and
 * their respective file paths for different game elements such as blocks,
 * entities, bubbles, fruits, power-ups, and effects. Additionally, the class
 * can resolve absolute paths for resources.
 */
public class Paths {

	/**
	 * A map that associates each character with a corresponding file path. This map
	 * contains paths for various game components including:
	 * <ul>
	 * <li>Blocks</li>
	 * <li>Entities</li>
	 * <li>Bubbles</li>
	 * <li>Fruits</li>
	 * <li>Power-ups</li>
	 * <li>Special effects</li>
	 * </ul>
	 */
	private static final Map<Character, String> pathsMap = new HashMap<>();

	static {
		// Initialize the paths for different characters and components

		// BLOCKS:
		pathsMap.put('1', "/blocks/normal_blocks/block_1");
		pathsMap.put('2', "/blocks/normal_blocks/block_80");
		pathsMap.put('3', "/blocks/normal_blocks/block_3");
		pathsMap.put('4', "/blocks/normal_blocks/block_10");
		pathsMap.put('5', "/blocks/normal_blocks/block_68");
		pathsMap.put('6', "/blocks/normal_blocks/block_6");
		pathsMap.put('7', "/blocks/normal_blocks/block_25");
		pathsMap.put('8', "/blocks/normal_blocks/block_31");
		pathsMap.put('9', "/blocks/normal_blocks/block_48");

		// ENTITIES:
		pathsMap.put('P', "/player/");
		pathsMap.put('B', "/enemies/boss/");
		pathsMap.put('Z', "/enemies/zenchan/");
		pathsMap.put('I', "/enemies/invader/");
		pathsMap.put('M', "/enemies/monsta/");
		pathsMap.put('N', "/enemies/banebou/");
		pathsMap.put('U', "/enemies/pulpul/");
		pathsMap.put('S', "/enemies/skelmonsta/");

		// BUBBLES:
		pathsMap.put('°', "/bubbles/player_bubble");
		pathsMap.put('-', "/bubbles/fire_bubble");
		pathsMap.put('+', "/bubbles/thunder_bubble");
		pathsMap.put('/', "/bubbles/water_bubble");
		pathsMap.put('%', "/bubbles/special_bubble");
		pathsMap.put('(', "/bubbles/e_bubble");
		pathsMap.put(')', "/bubbles/ex_bubble");
		pathsMap.put('[', "/bubbles/ext_bubble");
		pathsMap.put(']', "/bubbles/exte_bubble");
		pathsMap.put('{', "/bubbles/exten_bubble");
		pathsMap.put('}', "/bubbles/extend_bubble");

		// FRUITS:
		pathsMap.put('~', "/fruits/banana");
		pathsMap.put(';', "/fruits/peach");
		pathsMap.put('<', "/fruits/watermelon");
		pathsMap.put('>', "/fruits/pear");
		pathsMap.put(':', "/fruits/orange");

		// POWER-UPS:
		pathsMap.put('!', "/powerups/pink_candy");
		pathsMap.put('£', "/powerups/blue_candy");
		pathsMap.put('$', "/powerups/yellow_candy");
		pathsMap.put('§', "/powerups/shoes");
		pathsMap.put('@', "/powerups/parasol/");
		pathsMap.put('*', "/powerups/clock");
		pathsMap.put('^', "/powerups/dynamite");
		pathsMap.put('&', "/powerups/crystal_ring");
		pathsMap.put('=', "/powerups/amethyst_ring");
		pathsMap.put('.', "/powerups/ruby_ring");

		// EFFECTS:
		pathsMap.put('"', "/enemies/invader/red");
		pathsMap.put('#', "/bubbles/special_effects/fire_ball");
		pathsMap.put('?', "/bubbles/special_effects/bolt");
		pathsMap.put('_', "/bubbles/special_effects/water_horizontal");
		pathsMap.put('|', "/bubbles/special_effects/water_vertical");
	}

	 /**
     * Retrieves the file path associated with a given character.
     *
     * @param letter the character representing a specific game element
     * @return the file path corresponding to the given character,
     *         or {@code null} if the character is not mapped
     */
	public static String getPath(Character letter) {
		return pathsMap.get(letter);
	}

	 /**
     * Returns the absolute path for a given relative resource path.
     *
     * @param relativePath the relative path to the resource
     * @return the absolute path of the resource file
     * @throws IllegalArgumentException if the file does not exist
     */
	public static String getAbsolutePath(String relativePath) {
		File file = new File("resources/" + relativePath);
		if (file.exists()) {
			return file.getAbsolutePath();
		} else {
			throw new IllegalArgumentException("File non trovato: " + relativePath);
		}
	}
}
