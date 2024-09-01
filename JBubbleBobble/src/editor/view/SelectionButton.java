package editor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

/**
 * A button with an image and a code.
 */
public class SelectionButton extends JToggleButton {

    private static final long serialVersionUID = 1L;

    /** The image associated with this button. */
    private BufferedImage img;

    /** The code associated with this button. */
    private char code;

    /**
     * Constructs a SelectionButton with the specified image and code.
     *
     * @param img the image to be displayed on the button
     * @param code the code associated with this button
     */
    public SelectionButton(BufferedImage img, char code) {
        this.img = img;
        this.code = code;
        Image scaledImg = img.getScaledInstance(40 * (int) EditorPanel.SCALE, 40 * (int) EditorPanel.SCALE, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaledImg));
        setPreferredSize(new Dimension(40 * (int) EditorPanel.SCALE, 40 * (int) EditorPanel.SCALE));
        setBackground(Color.BLACK);
    }

    /**
     * Returns the image associated with this button.
     *
     * @return the image
     */
    public BufferedImage getImg() {
        return img;
    }

    /**
     * Returns the code associated with this button.
     *
     * @return the code
     */
    public char getCode() {
        return code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(img);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SelectionButton other = (SelectionButton) obj;
        return Objects.equals(img, other.img);
    }
}
