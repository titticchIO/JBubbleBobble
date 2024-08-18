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
		imageMap.put("E", "/enemies/image_");
		imageMap.put("B", "/bubbles/");
		imageMap.put("Z", "/enemies/zenchan/");
		imageMap.put("I", "/enemies/invader/");
		imageMap.put("M", "/enemies/monsta/");
		imageMap.put("N", "/enemies/banebou/");
		imageMap.put("U", "/enemies/pulpul/");
		imageMap.put("S", "/enemies/skelmonsta/");
	}

	public static BufferedImage getImage(String type, String position) {
		return ImageLoader.importImg(imageMap.get(type) + position + ".png");
	}
	
	
	
	public static BufferedImage getImage(int imageCode) {
		BufferedImage img = ImageLoader.importImg(imageMap.get(imageCode+"") + ".png");
		return img;
	}
	public static BufferedImage getImage(String imageCode) {
		BufferedImage img = ImageLoader.importImg(imageMap.get(imageCode) + "right.png");
		return img;
	}

}
