package editor.view;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import game.view.ImageLoader;

public class Sprite extends JButton {
	private static BufferedImage EMPTY_SPRITE = ImageLoader.importImg("/editor/Black.png");
	
	private int x, y;
	private float sideLength;
	private BufferedImage img;
	
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

	public BufferedImage getImg() {
		return img;
	}

	public void updateSprite(BufferedImage img) {
		this.img = img;
	}

	public void drawSprite(Graphics g) {
		if (img != null)
			g.drawImage(img, x * EditorPanel.SQUARE_SIZE, y * EditorPanel.SQUARE_SIZE, (int) sideLength,
					(int) sideLength, null);
		else
			g.clearRect(x * EditorPanel.SQUARE_SIZE + 2, y * EditorPanel.SQUARE_SIZE + 3, (int) sideLength - 3,
					(int) sideLength - 3);
	}

	public void render(Graphics g) {
	    // Disegna l'immagine vuota come sfondo (puoi mantenere questa parte se necessario)
	    ((Graphics2D)g).drawImage(EMPTY_SPRITE, x * EditorPanel.SQUARE_SIZE, y * EditorPanel.SQUARE_SIZE, (int) sideLength, (int) sideLength, null);
	    
	    // Disegna il bordo giallo
	    Graphics2D g2 = (Graphics2D) g;
	    float thickness = 1; // Spessore del bordo
	    g2.setStroke(new BasicStroke(thickness));
	    g2.setColor(Color.GRAY);
	    g2.drawRect(x * EditorPanel.SQUARE_SIZE, y * EditorPanel.SQUARE_SIZE, (int) sideLength, (int) sideLength);
	}


}
