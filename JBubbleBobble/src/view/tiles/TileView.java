package view.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import model.tiles.Tile;
import view.EntityView;
import view.ImageLoader;

public class TileView extends EntityView implements Observer {
	
	public TileView() {
		img = ImageLoader.importImg("/blocks/normal_blocks/block_1.png");
	}

	
	
}
