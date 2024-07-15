package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import model.entities.Entity;
import model.entities.MovingEntity;

public class MovingEntityView implements Observer {

	private BufferedImage img;
	private float x, y;
	private float width, height;
	private Animation animation;
	private Iterator<BufferedImage> animationIterator;

	public MovingEntityView(String entityName) {
		width=GameFrame.TILE_SIZE;
		height=GameFrame.TILE_SIZE;
		
		img = switch (entityName) {
		case "player" -> ImageLoader.importImg("/sprites/bubblun/image_5.png");
		default -> throw new IllegalArgumentException("Unexpected value: " + entityName);
		};
		if (entityName.equals("player")) {
			animation = new Animation();
			animationIterator = animation.iterator();
		}
	}

	public void updateAnimationImg() {
		img = animationIterator.next();
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

		//DRAW HITBOX
//		g.setColor(Color.BLACK);
//		g.drawRect((int) x, (int) y, (int) width, (int) height);
	}

	@Override
	public void update(Observable o, Object arg) {
		MovingEntity entity = (MovingEntity) o;
		String msg = (String) arg;

		switch (msg) {
		case "initial":
			setX(entity.getX());
			setY(entity.getY());
			break;
		case "walking":
			setX(entity.getX());
			break;
		case "y":
			setY(entity.getY());
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + msg);
		}

	}

}
