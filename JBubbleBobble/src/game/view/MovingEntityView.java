package game.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import static game.model.Settings.TILE_SIZE;
import game.model.entities.MovingEntity;

public class MovingEntityView implements Observer {

	private BufferedImage img;
	private float x, y;
	private float width, height;
	private Animation animation;
	private Iterator<BufferedImage> animationIterator;

	public MovingEntityView(String entityName) {
		width = TILE_SIZE;
		height = TILE_SIZE;

		img = switch (entityName) {
		case "player" -> Images.PLAYER.getImg();
		case "Zen-Chan" -> Images.ZEN_CHAN.getImg();
		case "Monsta" -> Images.MONSTA.getImg();
		case "Banebou" -> Images.BANEBOU.getImg();
		case "Mighta" -> Images.MIGHTA.getImg();
		case "Pulpul" -> Images.PULPUL.getImg();
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

		// DRAW HITBOX
		// g.setColor(Color.BLACK);
		// g.drawRect((int) x, (int) y, (int) width, (int) height);
	}

	@Override
	public void update(Observable o, Object arg) {
		MovingEntity entity = (MovingEntity) o;
		setX(entity.getX());
		setY(entity.getY());
	}
}
