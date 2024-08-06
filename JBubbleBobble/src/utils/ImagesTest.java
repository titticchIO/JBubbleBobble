package utils;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ImagesTest {

    private static final Map<String, String> imageMap;

    // Blocco statico di inizializzazione
    static {
        imageMap = new HashMap<>();
        imageMap.put("#", "/blocks/normal_blocks/block_");
        imageMap.put("P", "/players/image_");
        imageMap.put("E", "/enemies/image_");
        imageMap.put("B", "/bubbles/image_");
        imageMap.put("Z", "/enemies/zenchan/image_");
        imageMap.put("I", "/enemies/mighta/image_");
        imageMap.put("M", "/enemies/monsta/image_");
        imageMap.put("N", "/enemies/banebou/image_");
        imageMap.put("U", "/enemies/pulpul/image_");
        imageMap.put("S", "/enemies/skelmonsta/image_");
    }

    public static BufferedImage getImage(String type, String number) {
        return ImageLoader.importImg(imageMap.get(type) + number + ".png");		
    }
    
    public static BufferedImage getImage(String imageCode) {
    	return ImageLoader.importImg(imageMap.get(imageCode.substring(0,1)) + imageCode.substring(1,2) + ".png");
    }

}
