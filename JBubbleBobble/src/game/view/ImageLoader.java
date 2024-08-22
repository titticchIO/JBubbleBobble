package game.view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class ImageLoader {

	public static BufferedImage importImg(String path) {
		BufferedImage img = null;
		InputStream is = ImageLoader.class.getResourceAsStream(path);
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			System.err.println(path);
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				System.err.println(path);
				e.printStackTrace();
			}
		}
		return img;
	}
	
}
