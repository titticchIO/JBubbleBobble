package game.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import static game.model.Settings.GAME_WIDTH;
import static game.model.Settings.GAME_HEIGHT;

import game.model.level.Level;

public class GamePanel extends JPanel {
	private static GamePanel instance;
	private LevelView levelView;
	private MovingEntityView playerView;
	private BufferedImage tilesImage;

	private GamePanel(LevelView level) {
		setPanelSize();
		this.playerView = level.getPlayerView();
		this.levelView = level;
		renderTilesOnce();
	}
	
	public static GamePanel getInstance() {
		if (instance == null) {
			instance = new GamePanel(new LevelView(new Level(2)));
		}
		return instance;
	}

	public MovingEntityView getPlayerView() {
		return playerView;
	}

	private void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setPreferredSize(size);
	}

	private void renderTilesOnce() {
		tilesImage = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
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
		if(levelView.getBubbles()!=null) {
			for (MovingEntityView b: levelView.getBubbles()) {
				b.render(doubleBufferedGraphics);
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