package game.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Paths {

	// Mappa per associare ogni lettera a un percorso
	private static final Map<Character, String> pathsMap = new HashMap<>();

	static {
		// Aggiungi altri percorsi per le altre lettere...
//      BLOCKS:
		pathsMap.put('1', "/blocks/normal_blocks/block_1");
		pathsMap.put('2', "/blocks/normal_blocks/block_2");
		pathsMap.put('3', "/blocks/normal_blocks/block_3");
		pathsMap.put('4', "/blocks/normal_blocks/block_4");
		pathsMap.put('5', "/blocks/normal_blocks/block_5");
		pathsMap.put('6', "/blocks/normal_blocks/block_6");
		pathsMap.put('7', "/blocks/normal_blocks/block_7");
		pathsMap.put('8', "/blocks/normal_blocks/block_8");
		pathsMap.put('9', "/blocks/normal_blocks/block_9");

//    		ENTITIES:
		pathsMap.put('P', "/player/");
		pathsMap.put('B', "/enemies/boss/");
		pathsMap.put('Z', "/enemies/zenchan/");
		pathsMap.put('I', "/enemies/invader/");
		pathsMap.put('M', "/enemies/monsta/");
		pathsMap.put('N', "/enemies/banebou/");
		pathsMap.put('U', "/enemies/pulpul/");
		pathsMap.put('S', "/enemies/skelmonsta/");

//    		BUBBLES:
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

//    		FRUITS:
		pathsMap.put('~', "/fruits/banana");
		pathsMap.put(';', "/fruits/peach");
		pathsMap.put('<', "/fruits/watermelon");
		pathsMap.put('>', "/fruits/pear");
		pathsMap.put(':', "/fruits/orange");

//    		POWERUPS:
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

//    		EFFECTS:
		pathsMap.put('"', "/enemies/invader/red");
		pathsMap.put('#', "/bubbles/special_effects/fire_ball");
		pathsMap.put('?', "/bubbles/special_effects/bolt");
		pathsMap.put('_', "/bubbles/special_effects/water_horizontal");
		pathsMap.put('|', "/bubbles/special_effects/water_vertical");
	}

	// Metodo per ottenere il percorso associato a una lettera
	public static String getPath(Character letter) {
		return pathsMap.get(letter);
	}

	public static String getAbsolutePath(String relativePath) {
		File file = new File("resources/" + relativePath);
		if (file.exists()) {
			return file.getAbsolutePath();
		} else {
			throw new IllegalArgumentException("File non trovato: " + relativePath);
		}
	}
}
