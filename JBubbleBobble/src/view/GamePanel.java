package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import model.tiles.Tile;
import view.tiles.TileView;

public class GamePanel extends JPanel {
	private BufferedImage img;
	//Temp
	private PlayerView playerView;
	private TileView[] tiles;
	public GamePanel(PlayerView playerView) {
		setPanelSize();
		img = ImageLoader.importImg("/blocks/normal_blocks/block_10.png");
		//Temp
		this.playerView=playerView;
		tiles = new TileView[] {new TileView(), new TileView(), new TileView()};
		Tile[] tiles2=new Tile[] {new Tile(), new Tile(), new Tile()};
		for (int i = 0; i<3; i++) {
			tiles2[i].setX(20+i*5);
			tiles2[i].setY(20);
			tiles2[i].addObserver(tiles[i]);
		}
	}

	private void setPanelSize() {
		Dimension size = new Dimension(1280, 800);
		setPreferredSize(size);
	}
	
	@Override
	public void repaint() {
		paintComponent(getGraphics());
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if (playerView!=null)
			playerView.render(g);
		if (tiles!=null) {
			for (TileView t:tiles) {
				t.render(g);
			}
		}
			
	}

}
