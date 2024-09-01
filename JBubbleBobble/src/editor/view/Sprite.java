package editor.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JButton;

/**
 * Represents a sprite that can be rendered in a graphical environment.
 */
public class Sprite extends JButton {

    private static final long serialVersionUID = 1L;
    
    private int x;
    private int y;
    private float sideLength;
    private BufferedImage spriteImg;

    /**
     * Constructs a new Sprite with the specified position and side length.
     * 
     * @param x          the x-coordinate of the sprite
     * @param y          the y-coordinate of the sprite
     * @param sideLength the length of each side of the sprite
     */
    public Sprite(int x, int y, float sideLength) {
        this.x = x;
        this.y = y;
        this.sideLength = sideLength;
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    /**
     * Returns the x-coordinate of the sprite.
     * 
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the sprite.
     * 
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the length of each side of the sprite.
     * 
     * @return the side length
     */
    public float getSideLength() {
        return sideLength;
    }

    /**
     * Returns the image of the sprite.
     * 
     * @return the sprite image
     */
    public BufferedImage getSpriteImg() {
        return spriteImg;
    }

    /**
     * Updates the image of the sprite.
     * 
     * @param img the new image to set
     */
    public void updateSpriteImg(BufferedImage img) {
        this.spriteImg = img;
    }

    /**
     * Renders the sprite with a gray border.
     * 
     * @param g the graphics context
     */
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        float thickness = 1; // Border thickness
        g2.setStroke(new BasicStroke(thickness));
        g2.setColor(Color.GRAY);
        g2.drawRect(x * EditorPanel.SQUARE_SIZE, y * EditorPanel.SQUARE_SIZE, 
                    (int) sideLength, (int) sideLength);
        if (spriteImg != null)
            g2.drawImage(spriteImg, x * EditorPanel.SQUARE_SIZE, y * EditorPanel.SQUARE_SIZE, 
                        (int) sideLength, (int) sideLength, null);
    }

    /**
     * Renders the sprite without a border.
     * 
     * @param g the graphics context
     */
    public void renderWithoutBorder(Graphics g) {
        if (spriteImg != null)
            g.drawImage(spriteImg, x * EditorPanel.SQUARE_SIZE, y * EditorPanel.SQUARE_SIZE, 
                        (int) sideLength, (int) sideLength, null);
        else
            g.clearRect(x * EditorPanel.SQUARE_SIZE, y * EditorPanel.SQUARE_SIZE, 
                        (int) sideLength, (int) sideLength);
    }
}
