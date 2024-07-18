package view;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import static view.EditorPanel.SCALE;

public class SelectionButton extends JToggleButton {
	private BufferedImage img;

	public SelectionButton(String path) {
		img = ImageLoader.importImg(path);
		setIcon(new ImageIcon(img));
		setPreferredSize(new Dimension(20 * (int) SCALE, 20 * (int) SCALE));
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

/*
 * public SelectionButton(Sprites spriteName) { img = switch (spriteName) { case
 * PLAYER -> ImageLoader.importImg("/sprites/bubblun/image_5.png"); case BLOCK
 * -> ImageLoader.importImg("/blocks/normal_blocks/block_3.png"); case ENEMY ->
 * ImageLoader.importImg("/sprites/invader/image_1.png"); default -> throw new
 * IllegalArgumentException("Unexpected value: " + spriteName); }; setIcon(new
 * ImageIcon(img)); }
 */