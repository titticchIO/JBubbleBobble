package view;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import static view.GameFrame.FRAME_WIDTH;
import static view.GameFrame.FRAME_HEIGHT;

import model.level.Level;
import model.tiles.Tile;
import view.tiles.TileView;

public class GamePanel extends JPanel {
	private Level level;
	private PlayerView playerView;

	public GamePanel(PlayerView playerView, Level level) {
		setPanelSize();
		// Temp
		this.playerView = playerView;
		this.level = level;
	}
	
	private void setPanelSize() {
		Dimension size = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
		setPreferredSize(size);
	}


	public void tempPaint() {
		
	}

	@Override
	public void repaint() {
		paintComponent(getGraphics());
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if (playerView != null)
			playerView.render(g);
		if (level!=null) {
			level.testPaint(g);
		}
	}

}
