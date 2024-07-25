package view;

import java.awt.image.BufferedImage;

public enum Images {
	PLAYER("P"), BLOCK1("1"), BLOCK2("2"), BLOCK3("3"), BLOCK4("4"), BLOCK5("5"), BLOCK6("6"), EMPTY_BLOCK("0");

	BufferedImage img;

	Images(String code) {
		img = switch (code) {
		case "P" -> ImageLoader.importImg("/sprites/bubblun/image_5.png");
		case "1" -> ImageLoader.importImg("/blocks/normal_blocks/block_1.png");
		case "2" -> ImageLoader.importImg("/blocks/normal_blocks/block_3.png");
		case "3" -> ImageLoader.importImg("/blocks/normal_blocks/block_5.png");
		case "4" -> ImageLoader.importImg("/blocks/normal_blocks/block_6.png");
		case "5" -> ImageLoader.importImg("/blocks/normal_blocks/block_10.png");
		case "6" -> ImageLoader.importImg("/blocks/normal_blocks/block_11.png");

		case "0" -> ImageLoader.importImg("/EmptyTile.png");

		default -> throw new IllegalArgumentException("Unexpected value: " + code);
		};
	}

	public BufferedImage getImg() {
		return img;
	}
}
