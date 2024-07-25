package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import model.entities.Entity;

public class StaticEntityView implements Observer {

	protected BufferedImage img;
	protected float x, y;
	protected float width, height;

	public StaticEntityView(String entityName) {
		img = switch (entityName) {
		case "tile1" -> ImageLoader.importImg("/blocks/normal_blocks/block_1.png");
		case "tile2" -> ImageLoader.importImg("/blocks/normal_blocks/block_3.png");
		case "tile3" -> ImageLoader.importImg("/blocks/normal_blocks/block_5.png");
		case "tile4" -> ImageLoader.importImg("/blocks/normal_blocks/block_6.png");
		case "tile5" -> ImageLoader.importImg("/blocks/normal_blocks/block_10.png");
		case "tile6" -> ImageLoader.importImg("/blocks/normal_blocks/block_11.png");
		default -> throw new IllegalArgumentException("Unexpected value: " + entityName);
		};

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

	public void render(Graphics g) {
		g.drawImage(img, (int) x, (int) y, (int) width, (int) height, null);
//		g.setColor(Color.BLACK);
//		g.drawRect((int) x, (int) y, (int) width, (int) height);
	}

	@Override
	public void update(Observable o, Object arg) {
		Entity entity = (Entity) o;
		setX(entity.getX());
		setY(entity.getY());
		setWidth(entity.getWidth());
		setHeight(entity.getHeight());
	}

}
