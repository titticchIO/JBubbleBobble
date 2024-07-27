package game.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import static game.view.GameFrame.FRAME_WIDTH;
import static game.view.GameFrame.FRAME_HEIGHT;

import game.model.level.Level;

public class GamePanel extends JPanel {
	private LevelView levelView;
	private MovingEntityView playerView;
	private BufferedImage tilesImage;

	public GamePanel(LevelView level) {
		setPanelSize();
		this.playerView = level.getPlayerView();
		this.levelView = level;
		renderTilesOnce();
	}

	public MovingEntityView getPlayerView() {
		return playerView;
	}

	private void setPanelSize() {
		Dimension size = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
		setPreferredSize(size);
	}

	private void renderTilesOnce() {
		tilesImage = new BufferedImage(FRAME_WIDTH, FRAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = tilesImage.getGraphics();
		levelView.renderTiles(g);
		g.dispose();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
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
				e.render(doubleBufferedGraphics);
			}
		}
		g.drawImage(doubleBufferedImage, 0, 0, this);
		doubleBufferedGraphics.dispose();
	}

	@Override
	public void repaint() {
		if (playerView != null) {
			super.repaint((int) playerView.getX(), (int) playerView.getY(), (int) playerView.getWidth(),
					(int) playerView.getHeight());
		} else {
			super.repaint();
		}
	}
}