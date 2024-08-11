package game.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import editor.view.ImagesTest;
import game.model.enemies.Monsta;
import game.model.enemies.Zen_chan;
import game.model.entities.Entity;
import game.model.tiles.Tile;

public class EntityView implements Observer {

	protected BufferedImage img;
	protected float x, y;
	protected float width, height;
	protected boolean toDelete;

	private Observable observedEntity;

	public EntityView(String code) {
		img = Images.getImage(code);
		width = Tile.TILE_SIZE;
		height = Tile.TILE_SIZE;
	}

	public EntityView(String code, String type) {
		img = Images.getImage(code, type);
		width = Tile.TILE_SIZE;
		height = Tile.TILE_SIZE;
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

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setObservedEntity(Observable observedEntity) {
		this.observedEntity = observedEntity;
	}

	public boolean isObserving(Observable observable) {
		return this.observedEntity == observable;
	}

	public boolean isToDelete() {
		return toDelete;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void render(Graphics g) {
		g.drawImage(img, (int) x, (int) y, (int) width, (int) height, null);
//		g.setColor(Color.BLACK);
//		g.drawRect((int) x, (int) y, (int) width, (int) height);
	}

	public void delete(Graphics g) {
		g.clearRect((int) x, (int) y, (int) width, (int) height);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println(width+"|"+height);
		if (arg instanceof String && ((String) arg).equals("pop")) {
			toDelete = true;
		} else {
			Entity entity = (Entity) o;

			/*
			 * if (o instanceof Monsta) { // Aggiorna l'immagine in base alla direzione
			 * String direction = entity.getPositionCode(); // Ottieni la direzione (left o
			 * right) img = Images.getImage("M", direction); // Aggiorna l'immagine in base
			 * alla direzione }
			 */

			setX(entity.getX());
			setY(entity.getY());
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(height, img, width, x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityView other = (EntityView) obj;
		return Float.floatToIntBits(height) == Float.floatToIntBits(other.height) && Objects.equals(img, other.img)
				&& Float.floatToIntBits(width) == Float.floatToIntBits(other.width)
				&& Float.floatToIntBits(x) == Float.floatToIntBits(other.x)
				&& Float.floatToIntBits(y) == Float.floatToIntBits(other.y);
	}

}
