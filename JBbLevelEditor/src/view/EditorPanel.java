package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import model.LevelMaker;

public class EditorPanel extends JPanel {
	public static final int ROWS = 24;
	public static final int COLS = 30;
	public static final int DEFAULT_SQUARE_SIZE = 20;
	public static final float SCALE = 1.5f;
	public static final int SQUARE_SIZE = (int) (DEFAULT_SQUARE_SIZE * SCALE);
	public static final int PANEL_WIDTH = SQUARE_SIZE * COLS;
	public static final int PANEL_HEIGHT = SQUARE_SIZE * ROWS;
	private Map<Sprite, String> sprites;
	private SpriteSelectionScrollPane selPane;

	public EditorPanel(EditorFrame ef, SpriteSelectionScrollPane selPane) {
		this.selPane = selPane;
		setSize();
		setBackground(Color.LIGHT_GRAY);
		sprites = new HashMap<Sprite, String>();
		setLayout(new GridLayout(ROWS, COLS));
		for (int y = 0; y < ROWS; y++) {
			for (int x = 0; x < COLS; x++) {
				Sprite sprite = new Sprite(x, y, SQUARE_SIZE);
				sprite.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String imgPath = selPane.getCurrentPath();
						BufferedImage img = ImageLoader.importImg(imgPath);
						sprite.updateSprite(img);
						ef.repaint();
						String[] cord = sprites.get(sprite).split(":");
						int y = Integer.parseInt(cord[0]);
						int x = Integer.parseInt(cord[1]);
						LevelMaker.setTile(y, x, "#1");
					}
				});
				sprites.put(sprite, y + ":" + x);
				add(sprite);
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Sprite s : sprites.keySet()) {
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

	public Map<Sprite, String> getSprites() {
		return sprites;
	}
}
