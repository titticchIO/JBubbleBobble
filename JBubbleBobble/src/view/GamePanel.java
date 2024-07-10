package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	//Temp
	private PlayerView playerView;

	public GamePanel(PlayerView playerView) {
		setPanelSize();
		//Temp
		this.playerView=playerView;
	}

	private void setPanelSize() {
		Dimension size = new Dimension(1000, 400);
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
	}

}
