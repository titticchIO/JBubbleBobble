package game.view.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.Timer;

import game.controller.ActionListenersManager;
import game.model.level.Level;

/**
 * A JPanel {@code TransitionPanel} that handles the transition effect between
 * game levels.
 * <p>
 * This panel displays a transition effect where the current level image slides
 * out and the next level image slides in. The transition is controlled by a
 * timer and updates the progress of the transition from 0.0 to 1.0 over a
 * specified duration.
 * </p>
 */
public class TransitionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Image currentLevelImage;
	private Image nextLevelImage;
	private float progress;
	private Timer transitionTimer;

	/** Duration of the transition in milliseconds. */
	public static final int TRANSITION_DURATION = 2000;

	/**
	 * Constructs a new TransitionPanel with the specified GameFrame.
	 *
	 * @param gameFrame the GameFrame that this panel is associated with
	 */
	public TransitionPanel() {

		Dimension size = new Dimension((int) (Level.GAME_WIDTH * LevelPanel.SCALE),
				(int) (Level.GAME_HEIGHT * LevelPanel.SCALE));
		setPreferredSize(size);
		transitionTimer = new Timer(10, null);
	}

	public Timer getTransitionTimer() {
		return transitionTimer;
	}

	public float getProgress() {
		return progress;
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}

	/**
	 * Starts the transition effect with the specified current and next level
	 * images.
	 *
	 * @param currentImage the image of the current level to be transitioned out
	 * @param nextImage    the image of the next level to be transitioned in
	 */
	public void startTransition(Image currentImage, Image nextImage) {
		this.currentLevelImage = currentImage;
		this.nextLevelImage = nextImage;
		this.progress = 0f;

		if (transitionTimer.isRunning()) {
			transitionTimer.stop();
		}

		transitionTimer = new Timer(10, ActionListenersManager.advanceTransition(this));

		transitionTimer.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		int height = getHeight();

		int currentY = (int) (-progress * height);
		int nextY = (int) ((1 - progress) * height);

		if (currentLevelImage != null) {
			g2d.drawImage(currentLevelImage, 0, currentY, getWidth(), getHeight(), null);
		}

		if (nextLevelImage != null) {
			g2d.drawImage(nextLevelImage, 0, nextY, getWidth(), getHeight(), null);
		}
	}
}
