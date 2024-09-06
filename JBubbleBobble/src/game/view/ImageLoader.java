package game.view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * The {@code ImageLoader} class provides a static method to access th images in
 * the resources folder.
 */
public class ImageLoader {
	/**
	 * Static method to access th images in the resources folder.
	 * 
	 * @param path the path of the image to obtain
	 * @return the image retrieved from the resources
	 */
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
