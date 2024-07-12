package view;

import java.awt.image.BufferedImage;

public class StaticEntityView {
	
	public StaticEntityView(String filePath) {
		img = ImageLoader.importImg(filePath);
	}
		
	protected BufferedImage img;
	protected float x, y;
	protected float width, height;

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

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

}
