package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	public static BufferedImage importImg(String name) {
		InputStream is = ImageLoader.class.getResourceAsStream(name);
		BufferedImage output=null;
		try {
			output = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}
	
}
