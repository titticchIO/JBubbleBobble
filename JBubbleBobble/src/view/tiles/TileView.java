package view.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import model.tiles.Tile;
import view.ImageLoader;

public class TileView implements Observer {
	private BufferedImage img;
	private float x, y;
	
	public TileView() {
		img = ImageLoader.importImg("/sprites/bubblun/image_5.png");
	}

	@Override
	public void update(Observable o, Object arg) {
		if (((String) arg).equals("created")) {
			Tile obj = (Tile) o;
			setX(obj.getX());
			setY(obj.getY());
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(img, (int) x, (int) y, null);
	}
	
	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}
