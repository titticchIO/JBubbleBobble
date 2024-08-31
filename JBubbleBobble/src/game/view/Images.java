package game.view;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Images {

	private static final Map<Character, String> imageMap;

	// Blocco statico di inizializzazione
	static {
		imageMap = new HashMap<>();
//		BLOCKS:
		imageMap.put('1', "/blocks/normal_blocks/block_1");
		imageMap.put('2', "/blocks/normal_blocks/block_2");
		imageMap.put('3', "/blocks/normal_blocks/block_3");
		imageMap.put('4', "/blocks/normal_blocks/block_4");
		imageMap.put('5', "/blocks/normal_blocks/block_5");
		imageMap.put('6', "/blocks/normal_blocks/block_6");
		imageMap.put('7', "/blocks/normal_blocks/block_7");
		imageMap.put('8', "/blocks/normal_blocks/block_8");
		imageMap.put('9', "/blocks/normal_blocks/block_9");

//		ENTITIES:
		imageMap.put('P', "/player/");
		imageMap.put('B', "/enemies/boss/");
		imageMap.put('Z', "/enemies/zenchan/");
		imageMap.put('I', "/enemies/invader/");
		imageMap.put('M', "/enemies/monsta/");
		imageMap.put('N', "/enemies/banebou/");
		imageMap.put('U', "/enemies/pulpul/");
		imageMap.put('S', "/enemies/skelmonsta/");

//		BUBBLES:
		imageMap.put('°', "/bubbles/player_bubble");
		imageMap.put('-', "/bubbles/fire_bubble");
		imageMap.put('+', "/bubbles/thunder_bubble");
		imageMap.put('/', "/bubbles/water_bubble");
		imageMap.put('(', "/bubbles/e_bubble");
		imageMap.put(')', "/bubbles/ex_bubble");
		imageMap.put('[', "/bubbles/ext_bubble");
		imageMap.put(']', "/bubbles/exte_bubble");
		imageMap.put('{', "/bubbles/exten_bubble");
		imageMap.put('}', "/bubbles/extend_bubble");
		imageMap.put('%', "/bubbles/special_bubble");

//		FRUITS:
		imageMap.put('~', "/fruits/banana");
		imageMap.put(';', "/fruits/peach");
		imageMap.put('<', "/fruits/watermelon");
		imageMap.put('>', "/fruits/pear");
		imageMap.put(':', "/fruits/orange");

//		POWERUPS:
		imageMap.put('!', "/powerups/pink_candy");
		imageMap.put('£', "/powerups/blue_candy");
		imageMap.put('$', "/powerups/yellow_candy");
		imageMap.put('§', "/powerups/shoes");
		imageMap.put('@', "/powerups/parasol/");
		imageMap.put('*', "/powerups/clock");
		imageMap.put('^', "/powerups/dynamite");
		imageMap.put('&', "/powerups/crystal_ring");
		imageMap.put('=', "/powerups/amethyst_ring");
		imageMap.put('.', "/powerups/ruby_ring");

//		EFFECTS:
		imageMap.put('"', "/enemies/invader/red");
		imageMap.put('#', "/bubbles/special_effects/fire_ball");
		imageMap.put('?', "/bubbles/special_effects/bolt");
		imageMap.put('_', "/bubbles/special_effects/water_horizontal");
		imageMap.put('|', "/bubbles/special_effects/water_vertical");
	}

	public static BufferedImage getImage(Character type, String position) {
		return ImageLoader.importImg(imageMap.get(type) + position + ".png");
	}

	public static BufferedImage getImage(Character imageCode) {
		BufferedImage img;
		if (Character.isAlphabetic(imageCode))
			img = ImageLoader.importImg(imageMap.get(imageCode) + "right.png");
		else
			img = ImageLoader.importImg(imageMap.get(imageCode) + ".png");
		return img;
	}

	public static void main(String[] args) {
		char codeChar = '*';
		System.out.println(imageMap.keySet());
		System.out.println(imageMap.keySet().contains(codeChar));
	}

}
