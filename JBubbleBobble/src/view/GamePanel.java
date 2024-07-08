package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private BufferedImage img;
	//Temp
	private PlayerView playerView;

	public GamePanel(PlayerView playerView) {
		setPanelSize();
//		requestFocus();
		img = ImageLoader.importImg("/sprites/bubblun/image_5.png");
		//Temp
		this.playerView=playerView;
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
//		g.drawImage(img, 10, 20, null);
	}

}
