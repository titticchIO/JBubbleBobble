package game.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import game.model.entities.Entity;

public class StaticEntityView implements Observer {

	protected BufferedImage img;
	protected float x, y;
	protected float width, height;

	public StaticEntityView(String entityName) {
		img = switch (entityName) {
		case "tile1" -> Images.BLOCK1.getImg();
		case "tile2" -> Images.BLOCK2.getImg();
		case "tile3" -> Images.BLOCK3.getImg();
		case "tile4" -> Images.BLOCK4.getImg();
		case "tile5" -> Images.BLOCK5.getImg();
		case "tile6" -> Images.BLOCK6.getImg();
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
