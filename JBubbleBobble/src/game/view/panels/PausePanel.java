package game.view.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import game.controller.ActionListenersManager;
import game.controller.Controller;
import game.model.level.Level;
import game.view.ImageLoader;

/**
 * PausePanel is a custom JPanel that represents the pause menu in the game. It
 * displays buttons for resuming the game and exiting to the main menu.
 * Additionally, it shows a blurred version of the current game screen as a
 * background.
 * 
 * The panel is initialized with two buttons: - "Resume": Allows the player to
 * continue the game. - "Exit": Returns the player to the main menu.
 * 
 * The background is a blurred version of the game's current state, which is
 * achieved by capturing the game screen and applying a blur filter.
 * 
 * The panel is built upon a given LevelPanel and the game's Controller.
 * 
 * @see JPanel
 * @see LevelPanel
 * @see Controller
 */
public class PausePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage levelImage;
	private LevelPanel levelPanel;

	/**
	 * Constructor that creates a PausePanel for the given LevelPanel and game
	 * controller. Sets up the size, layout, and buttons for the pause panel.
	 * 
	 * @param levelPanel the LevelPanel that displays the current game
	 * @param controller       the Controller that manages the game's state
	 */
	public PausePanel(LevelPanel levelPanel, Controller controller) {
		this.levelPanel = levelPanel;
		setSize(new Dimension((int) (Level.GAME_WIDTH * LevelPanel.SCALE),
				(int) (Level.GAME_HEIGHT * LevelPanel.SCALE)));

		setLayout(null);

		JButton resumeButton = new JButton();
		resumeButton.setBounds(260, 150, 180, 90);
		resumeButton.addActionListener(ActionListenersManager.resumeGame(controller));
		ImageIcon resumeButtonImageIcon = new ImageIcon(
				ImageLoader.importImg("/menu/resume.png").getScaledInstance(180, 90, Image.SCALE_SMOOTH));
		resumeButton.setIcon(resumeButtonImageIcon);
		resumeButton.setContentAreaFilled(false);
		resumeButton.setBorderPainted(false);
		resumeButton.setFocusPainted(false);

		JButton exitButton = new JButton();
		exitButton.setBounds(260, 350, 180, 90);
		exitButton.addActionListener(ActionListenersManager.backToMenu(controller));
		ImageIcon exitButtonImageIcon = new ImageIcon(
				ImageLoader.importImg("/menu/menu.png").getScaledInstance(180, 90, Image.SCALE_SMOOTH));
		exitButton.setIcon(exitButtonImageIcon);
		exitButton.setContentAreaFilled(false);
		exitButton.setBorderPainted(false);
		exitButton.setFocusPainted(false);

		add(resumeButton);
		add(exitButton);
	}

	/**
	 * Captures the current visual state of the level displayed in the LevelPanel
	 * and applies a blur effect to it. This blurred image is used as the background
	 * of the pause menu.
	 */
	public void drawBackground() {
		this.levelImage = new BufferedImage(levelPanel.getWidth(), levelPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = levelImage.getGraphics();
		levelPanel.paint(g);
		g.dispose();
		blurImage();
	}

	/**
	 * Captures the current visual state of the level displayed in the LevelPanel
	 * and applies a blur effect to it. This blurred image is used as the background
	 * of the pause menu.
	 */
	private void blurImage() {
		if (levelImage != null) {
			float[] blurKernel = { 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f,
					1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f,
					1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f };

			Kernel kernel = new Kernel(5, 5, blurKernel);
			ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
			levelImage = op.filter(levelImage, null);
		}
	}

	/**
	 * Overrides the paintComponent method to render the blurred background image.
	 * If the level image is captured and blurred, it is drawn as the background of
	 * the panel.
	 *
	 * @param g the Graphics object used to draw the panel
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (levelImage != null) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(levelImage, 0, 0, getWidth(), getHeight(), null);
		}
	}
}
