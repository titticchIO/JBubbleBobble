package game.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import game.model.level.Level;

public class WinPanel extends JPanel{
	
	public WinPanel() {
		setSize(new Dimension((int) (Level.GAME_WIDTH * LevelPanel.SCALE),
                (int) (Level.GAME_HEIGHT * LevelPanel.SCALE)));
	}

	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
        BufferedImage winImage = ImageLoader.importImg("/WinScreen.png");
        if (winImage != null) {
            g.drawImage(winImage, 0, 0, getWidth(), getHeight(), null);
        }
	}
}
