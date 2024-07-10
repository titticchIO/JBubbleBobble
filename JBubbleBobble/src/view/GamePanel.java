package view;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import static view.GameFrame.FRAME_WIDTH;
import static view.GameFrame.FRAME_HEIGHT;

public class GamePanel extends JPanel {
	//Temp
	private PlayerView playerView;

	public GamePanel(PlayerView playerView) {
		setPanelSize();
		//Temp
		this.playerView=playerView;
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
	}

}
