package game.view;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Images {

	private static final Map<String, String> imageMap;

	// Blocco statico di inizializzazione
	static {
		imageMap = new HashMap<>();
		imageMap.put("1", "/blocks/normal_blocks/block_1");
		imageMap.put("2", "/blocks/normal_blocks/block_2");
		imageMap.put("3", "/blocks/normal_blocks/block_3");
		imageMap.put("4", "/blocks/normal_blocks/block_4");
		imageMap.put("P", "/player/");
		imageMap.put("°", "/bubbles/player_bubble");
		imageMap.put("-", "/bubbles/fire_bubble");
		imageMap.put("+", "/bubbles/thunder_bubble");
		imageMap.put("/", "/bubbles/water_bubble");
		imageMap.put("%", "/bubbles/special_bubble");
		imageMap.put("#", "/bubbles/special_effects/fire_ball");
		imageMap.put("?", "/bubbles/special_effects/bolt");
		imageMap.put("_", "/bubbles/special_effects/water_horizontal");		
		imageMap.put("|", "/bubbles/special_effects/water_vertical");		
		imageMap.put("Z", "/enemies/zenchan/");
		imageMap.put("I", "/enemies/invader/");
		imageMap.put("M", "/enemies/monsta/");
		imageMap.put("N", "/enemies/banebou/");
		imageMap.put("U", "/enemies/pulpul/");
		imageMap.put("S", "/enemies/skelmonsta/");
		imageMap.put("!", "/powerups/pink_candy");
		imageMap.put("£", "/powerups/blue_candy");
		imageMap.put("$", "/powerups/yellow_candy");
		imageMap.put("§", "/powerups/shoes");
		imageMap.put("@", "/powerups/parasol/");
		imageMap.put("*", "/powerups/clock");	
		imageMap.put("^", "/powerups/dynamite");
		imageMap.put("&", "/powerups/crystal_ring");
		imageMap.put("(", "/powerups/amethyst_ring");
		

	}

	public static BufferedImage getImage(String type, String position) {
		return ImageLoader.importImg(imageMap.get(type) + position + ".png");
	}
	
	public static BufferedImage getImage(String imageCode) {
		BufferedImage img;
		if (imageCode.matches("[A-Za-z]"))
			img = ImageLoader.importImg(imageMap.get(imageCode) + "right.png");
		else
			img = ImageLoader.importImg(imageMap.get(imageCode+"") + ".png");
		return img;
	}

}
