package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageLoader {

    public static BufferedImage importImg(String path) {
        InputStream is = ImageLoader.class.getResourceAsStream(path);
        if (is == null) {
            throw new IllegalArgumentException("Image not found at path: " + path);
        }
        try {
            BufferedImage img = ImageIO.read(is);
            is.close();
            return img;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image from path: " + path, e);
        }
    }
}
