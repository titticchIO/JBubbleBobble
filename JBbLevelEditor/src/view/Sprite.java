package view;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

public class Sprite extends JButton{
	private int x, y;
	private float side_length;
	private BufferedImage img;

	public Sprite(int x, int y, float side_length, BufferedImage img) {
		this.x = x;
		this.y = y;
		this.side_length = side_length;
		this.img = img;
		setVisible(false);
		setEnabled(true);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public float getSide_length() {
		return side_length;
	}

	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;  // Cast to Graphics2D
	    float thickness = 2;  // Define the thickness you want
	    g2.setStroke(new BasicStroke(thickness));  // Set the stroke thickness
	    g2.drawRect(x, y, (int) side_length, (int) side_length);  // Draw the rectangle
	}
	
}
