package game.model;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import game.view.ImageLoader;

public class Images {

    private static final Map<String, String> imageMap;

    // Blocco statico di inizializzazione
    static {
        imageMap = new HashMap<>();
        imageMap.put("#", "/blocks/normal_blocks/block_");
        imageMap.put("P", "/player/");
        imageMap.put("E", "/enemies/image_");
        imageMap.put("B", "/bubbles/image_");
        imageMap.put("Z", "/enemies/zenchan/");
        imageMap.put("I", "/enemies/mighta/");
        imageMap.put("M", "/enemies/monsta/");
        imageMap.put("N", "/enemies/banebou/");
        imageMap.put("U", "/enemies/pulpul/");
        imageMap.put("S", "/enemies/skelmonsta/");
    }

    public static BufferedImage getImage(String type, String position) {
        return ImageLoader.importImg(imageMap.get(type) + position + ".png");		
    }
    
    public static BufferedImage getImage(String imageCode) {
    	return ImageLoader.importImg(imageMap.get(imageCode.substring(0,1)) + imageCode.substring(1,2) + ".png");
    }

}
