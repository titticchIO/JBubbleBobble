package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class EditorPanel extends JPanel{
	public static final int ROWS = 25;
	public static final int COLS = 30;
	private static final int DEFAULT_SQUARE_SIZE = 30;
	private static final float SCALE = 1.0f;
	private static final int SQUARE_SIZE = DEFAULT_SQUARE_SIZE * (int) SCALE;
	private static final int PANEL_WIDTH=SQUARE_SIZE*COLS;
	private static final int PANEL_HEIGHT=SQUARE_SIZE*ROWS;
	private List<Sprite> sprites;

	public EditorPanel() {
		setSize();
		
		sprites=new ArrayList<Sprite>();
		setLayout(new GridLayout(ROWS, COLS));
		for (int y = 0; y < ROWS; y++) {
			for (int x = 0; x < COLS; x++) {
				Sprite sprite=new Sprite(x*SQUARE_SIZE, y*SQUARE_SIZE, SQUARE_SIZE, null);
				add(sprite);
				sprites.add(sprite);
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Sprite s:sprites) {
			s.render(g);
		}
	}

	private void setSize() {
		Dimension size = new Dimension(PANEL_WIDTH,PANEL_HEIGHT);
		setPreferredSize(size);
	}

	public List<Sprite> getSprites() {
		return sprites;
	}
}
