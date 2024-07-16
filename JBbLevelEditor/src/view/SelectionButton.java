package view;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class SelectionButton extends JButton {
	private BufferedImage img;

	public SelectionButton(Sprites spriteName) {
		img = switch (spriteName) {
		case PLAYER -> ImageLoader.importImg("/sprites/bubblun/image_5.png");
		case BLOCK -> ImageLoader.importImg("/blocks/normal_blocks/block_3.png");
		case ENEMY -> ImageLoader.importImg("/sprites/invader/image_1.png");
		default -> throw new IllegalArgumentException("Unexpected value: " + spriteName);
		};
		setIcon(new ImageIcon(img));
	}
}
