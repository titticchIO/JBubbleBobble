package game.view.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import game.model.level.Level;
import game.view.ImageLoader;

/**
 * The {@code EndPanel} is shown when the game ends, displaying a different
 * image depending on the result of it.
 */
public class EndPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * The different outcomes of the game
	 */
	public enum Ending {
		WIN, LOSS
	}

	private Image img; // the image to be displayed

	/**
	 * Constructor for the EndPanel
	 * 
	 * @param ending decides what image to show
	 */
	public EndPanel(Ending ending) {
		img = switch (ending) {
		case WIN -> ImageLoader.importImg("/menu/WinScreen.png");
		case LOSS -> ImageLoader.importImg("/menu/LooseScreen.png");
		};
		setSize(new Dimension((int) (Level.GAME_WIDTH * LevelPanel.SCALE),
				(int) (Level.GAME_HEIGHT * LevelPanel.SCALE)));
	}

	/**
	 * Draws the selected image
	 * 
	 * @param g the {@code Graphics} object used for rendering.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
	}
}
