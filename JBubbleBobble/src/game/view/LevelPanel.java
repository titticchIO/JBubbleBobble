package game.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.controller.gamestates.GameState;
import game.model.bubbles.BubbleManager;
import game.model.level.Level;

public class LevelPanel extends JPanel {
	public final static float SCALE = 1.5f;
	private LevelView levelView;
	private MovingEntityView playerView;
	private BufferedImage tilesImage;

//	private MenuPanel menuPanel

//	NON AGGIUNGERE IL PATTERN SINGLETON!!!!!

	public LevelPanel() {
		setPanelSize();		
	}

	private void initPlayingClasses(LevelView levelView) {
		this.levelView = levelView;
		BubbleManager.getInstance().addObserver(levelView);
		playerView = levelView.getPlayerView();
		renderTilesOnce();
	}

	public void startGame(LevelView levelView) {
		initPlayingClasses(levelView);
	}

	public MovingEntityView getPlayerView() {
		return playerView;
	}

	private void setPanelSize() {
		Dimension size = new Dimension((int) (Level.GAME_WIDTH * SCALE), (int) (Level.GAME_HEIGHT * SCALE));
		setPreferredSize(size);
	}

	private void renderTilesOnce() {
		tilesImage = new BufferedImage(Level.GAME_WIDTH, Level.GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = tilesImage.getGraphics();
		levelView.renderTiles(g);
		g.dispose();
	}

	
	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(SCALE, SCALE);
		g2d.scale(SCALE, SCALE);

		super.paintComponent(g2d);
		// Usa double buffering per disegnare su un'immagine temporanea prima di
		// dipingerla sul JPanel
		Image doubleBufferedImage = createImage(getWidth(), getHeight());
		Graphics doubleBufferedGraphics = doubleBufferedImage.getGraphics();

		if (tilesImage != null) {
			doubleBufferedGraphics.drawImage(tilesImage, 0, 0, this);
		}
		if (playerView != null) {
			playerView.render(doubleBufferedGraphics);
		}
		if (levelView.getEnemies() != null) {
			for (MovingEntityView e : levelView.getEnemies()) {
				if (!e.isToDelete())
					e.render(doubleBufferedGraphics);
				else
					e.delete(doubleBufferedGraphics);
			}
		}
		if (levelView.getBubbles() != null) {
			for (MovingEntityView b : levelView.getBubbles()) {
				if (!b.isToDelete())
					b.render(doubleBufferedGraphics);
				else
					b.delete(doubleBufferedGraphics);
			}
		}

		g2d.drawImage(doubleBufferedImage, 0, 0, this);
		doubleBufferedGraphics.dispose();

	}

}