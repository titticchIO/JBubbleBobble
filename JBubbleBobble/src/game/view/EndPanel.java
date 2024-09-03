package game.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import game.model.Level;

public class EndPanel extends JPanel {

	public enum Ending {
		WIN, LOSS
	}

	private Image img;

	public EndPanel(Ending ending) {
		img = switch (ending) {
		case WIN -> ImageLoader.importImg("/menu/WinScreen.png");
		case LOSS -> AnimationAndImagesLoader.loadEntityImage("/menu/LooseScreen.gif");
		};
		setSize(new Dimension((int) (Level.GAME_WIDTH * LevelPanel.SCALE),
				(int) (Level.GAME_HEIGHT * LevelPanel.SCALE)));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
	}
}
