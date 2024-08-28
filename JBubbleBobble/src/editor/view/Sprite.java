package editor.view;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import game.view.ImageLoader;

public class Sprite extends JButton {

	private int x, y;
	private float sideLength;
	private BufferedImage spriteImg;

	public Sprite(int x, int y, float side_length) {
		this.x = x;
		this.y = y;
		this.sideLength = side_length;
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public float getSideLength() {
		return sideLength;
	}

	public BufferedImage getSpriteImg() {
		return spriteImg;
	}

	public void updateSpriteImg(BufferedImage img) {
		this.spriteImg = img;
	}

	public void render(Graphics g) {
		// Disegna il bordo grigio
		Graphics2D g2 = (Graphics2D) g;
		float thickness = 1; // Spessore del bordo
		g2.setStroke(new BasicStroke(thickness));
		g2.setColor(Color.GRAY);
		g2.drawRect(x * EditorPanel.SQUARE_SIZE, y * EditorPanel.SQUARE_SIZE, (int) sideLength, (int) sideLength);
		if (spriteImg != null)
			g2.drawImage(spriteImg, x * EditorPanel.SQUARE_SIZE, y * EditorPanel.SQUARE_SIZE, (int) sideLength,
					(int) sideLength, null);
	}

	public void renderWithoutBorder(Graphics g) {
		if (spriteImg != null) {
			g.drawImage(spriteImg, x * EditorPanel.SQUARE_SIZE, y * EditorPanel.SQUARE_SIZE, (int) sideLength,
					(int) sideLength, null);
		} else {
			g.clearRect(x * EditorPanel.SQUARE_SIZE, y * EditorPanel.SQUARE_SIZE, (int) sideLength, (int) sideLength);
		}
	}

}
