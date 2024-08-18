package editor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import editor.model.LevelManager;
import game.view.Images;

public class EditorPanel extends JPanel {
	public static final int ROWS = 24;
	public static final int COLS = 30;
	public static final int DEFAULT_SQUARE_SIZE = 20;
	public static final float SCALE = 1;
	public static final int SQUARE_SIZE = (int) (DEFAULT_SQUARE_SIZE * SCALE);
	public static final int PANEL_WIDTH = SQUARE_SIZE * COLS;
	public static final int PANEL_HEIGHT = SQUARE_SIZE * ROWS;
	private Sprite[][] sprites;
	private SpriteSelectionScrollPane selPane;
	private boolean isMousePressed = false;

	public EditorPanel(EditorFrame ef, SpriteSelectionScrollPane selPane) {
		this.selPane = selPane;
		setSize();
//		setBackground(Color.WHITE);
		sprites = new Sprite[ROWS][COLS];
		setLayout(new GridLayout(ROWS, COLS));
		for (int y = 0; y < ROWS; y++) {
			for (int x = 0; x < COLS; x++) {
				final int xCopy = x;
				final int yCopy = y;
				Sprite sprite = new Sprite(x, y, SQUARE_SIZE);
				sprite.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						isMousePressed = true;
						updateSprite(sprite, xCopy, yCopy);
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						isMousePressed = false;
					}
				});
				sprite.addMouseMotionListener(new MouseAdapter() {
					@Override
					public void mouseDragged(MouseEvent e) {
						if (isMousePressed) {
							// Get the absolute position of the mouse event
							int x = (e.getXOnScreen() - getLocationOnScreen().x) / SQUARE_SIZE;
							int y = (e.getYOnScreen() - getLocationOnScreen().y) / SQUARE_SIZE;
							try {
								updateSprite(sprites[y][x], x, y);
							} catch (Exception e2) {
								System.err.println("Coordinate outside level bounds");
							}

						}
					}
				});
				sprites[y][x] = sprite;
				add(sprite);
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Sprite[] row : sprites)
			for (Sprite s : row) {
				s.render(g);
				if (s.getImg() != null) {
					s.drawSprite(g);
				}
			}
	}

	private void setSize() {
		Dimension size = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
		setPreferredSize(size);
	}

	public void loadLevel(String[][] levelData) {
		for (int y = 0; y < levelData.length; y++) {
			for (int x = 0; x < levelData[y].length; x++) {
				if (!levelData[y][x].equals(" ")) {
					sprites[y][x].updateSprite(Images.getImage(levelData[y][x]));
				}
			}
		}
		repaint(); // Ridisegna il pannello per riflettere le modifiche

	}

	public Sprite[][] getSprites() {
		return sprites;
	}

	public static void setEmptySprites(Sprite[][] sprites) {
		for (int y = 0; y < ROWS; y++) {
			for (int x = 0; x < COLS; x++) {
				sprites[y][x].updateSprite(null); // Imposta il sprite come vuoto
			}
		}
	}

	public void setSprites(Sprite[][] sprites) {
		this.sprites = sprites;
	}

	private void updateSprite(Sprite sprite, int x, int y) {
		SelectionButton button = selPane.getCurrentButton();
		if (button != null)
			sprite.updateSprite(button.getImg());
		else
			sprite.updateSprite(null);
		LevelManager.setTile(y, x, button.getCode());
		repaint();
	}
}
