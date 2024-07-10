package view;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import static view.GameFrame.FRAME_WIDTH;
import static view.GameFrame.FRAME_HEIGHT;

import model.tiles.Tile;
import view.tiles.TileView;

public class GamePanel extends JPanel {
	//Temp
	private PlayerView playerView;
	private TileView[] tiles;
	public GamePanel(PlayerView playerView) {
		setPanelSize();
		//Temp
		this.playerView=playerView;
		tiles = new TileView[] {new TileView(), new TileView(), new TileView()};
		Tile[] tiles2=new Tile[] {new Tile(), new Tile(), new Tile()};
		for (int i = 0; i<3; i++) {
			tiles2[i].addObserver(tiles[i]);
			tiles2[i].setHeight(16);
			tiles2[i].setWidth(16);
			tiles2[i].setInitialPosition(20+i*20, 20);
			
		}
	}

	private void setPanelSize() {
		Dimension size = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
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
		if (tiles != null) {
			for (int i = 0; i<3; i++) {
				tiles[i].render(g);
			}
		}
	}

}
