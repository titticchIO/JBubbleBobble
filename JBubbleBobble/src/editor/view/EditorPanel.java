package editor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

import editor.model.LevelManager;
import static editor.controller.ActionListenersManager.updateSpriteClick;
import static editor.controller.ActionListenersManager.updateSpriteDrag;
import game.view.AnimationAndImagesLoader;

/**
 * EditorPanel is responsible for displaying and interacting with the game
 * editor grid. It handles mouse events to update the grid with selected
 * sprites.
 */
public class EditorPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	// Public static constants
	public static final int ROWS = 24;
	public static final int COLS = 30;
	public static final int DEFAULT_SQUARE_SIZE = 20;
	public static final float SCALE = 1;
	public static final int SQUARE_SIZE = (int) (DEFAULT_SQUARE_SIZE * SCALE);
	public static final int PANEL_WIDTH = SQUARE_SIZE * COLS;
	public static final int PANEL_HEIGHT = SQUARE_SIZE * ROWS;

	// Instance fields
	private Sprite[][] sprites;
	private SpriteSelectionScrollPane selectionPane;
	private boolean mousePressed;

	/**
	 * Constructor that initializes the editor panel with a grid of sprites.
	 * 
	 * @param ef      the editor frame
	 * @param selPane the sprite selection scroll pane
	 */
	public EditorPanel(EditorFrame ef, SpriteSelectionScrollPane selPane) {
		this.selectionPane = selPane;
		this.sprites = new Sprite[ROWS][COLS];
		this.mousePressed = false;

		setSize();
		setBackground(Color.BLACK);
		setLayout(new GridLayout(ROWS, COLS));

		initializeGrid();
	}

	/**
	 * Retrieves the 2D array of sprites representing the grid.
	 * 
	 * @return the 2D array of sprites
	 */
	public Sprite[][] getSprites() {
		return sprites;
	}

	/**
	 * Retrieves the SpriteSelectionPane
	 * 
	 * @return
	 */
	public SpriteSelectionScrollPane getSelectionPane() {
		return selectionPane;
	}
	
	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean isMousePressed) {
		this.mousePressed = isMousePressed;
	}

	/**
	 * Initializes the grid with sprites and sets up mouse listeners for
	 * interactions.
	 */
	private void initializeGrid() {
		for (int y = 0; y < ROWS; y++) {
			for (int x = 0; x < COLS; x++) {
				Sprite sprite = new Sprite(x, y, SQUARE_SIZE);
				sprite.addMouseListener(updateSpriteClick(this, x, y));
				sprite.addMouseMotionListener(updateSpriteDrag(this, x, y));
				sprites[y][x] = sprite;
				add(sprite);
			}
		}
	}

	/**
	 * Custom painting of the panel, renders each sprite in the grid.
	 * 
	 * @param g the Graphics object used for painting
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Sprite[] row : sprites)
			for (Sprite s : row)
				s.render(g);
	}

	/**
	 * Sets the size of the panel based on the predefined dimensions.
	 */
	private void setSize() {
		Dimension size = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
		setPreferredSize(size);
	}

	/**
	 * Loads a level into the editor panel, updating sprites based on the given
	 * level data.
	 * 
	 * @param levelData a 2D array representing the level structure
	 */
	public void loadLevel(char[][] levelData) {
		for (int y = 0; y < levelData.length; y++) {
			for (int x = 0; x < levelData[y].length; x++) {
				if (levelData[y][x] != ' ')
					sprites[y][x].updateSpriteImg(AnimationAndImagesLoader.getImage(levelData[y][x]));
			}
		}
		repaint(); // Repaint the panel to reflect the changes
	}

	/**
	 * Resets the editor panel by clearing all sprites.
	 */
	public void reset() {
		for (Sprite[] row : sprites)
			for (Sprite sprite : row)
				sprite.updateSpriteImg(null);
		repaint(); // Repaint the panel to reflect the changes
	}

	/**
	 * Updates the sprite at a specific position with the selected sprite image.
	 * 
	 * @param x      the x-coordinate of the sprite in the grid
	 * @param y      the y-coordinate of the sprite in the grid
	 */
	public void updateSprite(int x, int y) {
		SelectionButton button = selectionPane.getCurrentButton();
		if (button.getCode() != ' ')
			sprites[y][x].updateSpriteImg(button.getImg());
		else
			sprites[y][x].updateSpriteImg(null);
		LevelManager.setTile(y, x, button.getCode());
		repaint();
	}
}
