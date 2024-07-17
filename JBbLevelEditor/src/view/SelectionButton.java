package view;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;

public class SelectionButton extends JToggleButton {
	private BufferedImage img;

	/*
	public SelectionButton(Sprites spriteName) {
		img = switch (spriteName) {
		case PLAYER -> ImageLoader.importImg("/sprites/bubblun/image_5.png");
		case BLOCK -> ImageLoader.importImg("/blocks/normal_blocks/block_3.png");
		case ENEMY -> ImageLoader.importImg("/sprites/invader/image_1.png");
		default -> throw new IllegalArgumentException("Unexpected value: " + spriteName);
		};
		setIcon(new ImageIcon(img));
	}
	*/
	
	
	public SelectionButton(String path) {
		img = ImageLoader.importImg(path);
		setIcon(new ImageIcon(img));
	}
}
