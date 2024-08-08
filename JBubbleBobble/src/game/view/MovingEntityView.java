package game.view;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Observable;

import game.model.enemies.Monsta;
import game.model.entities.Entity;
import game.model.entities.MovingEntity;
import game.model.entities.Player;

public class MovingEntityView extends EntityView {

	private Iterator<BufferedImage> animationIterator;

	public MovingEntityView(String type) {
		super(type);
	}

	public MovingEntityView(String type, String code) {
		super(type, code);
	}

	@Override
	public void update(Observable o, Object arg) {
		MovingEntity entity = (MovingEntity) o;

		if (arg instanceof String message) {
			switch (message) {
			case "pop" -> toDelete = true;
			case "LEFT" -> img = Images.getImage(entity.getType(), message);
			case "RIGHT" -> img = Images.getImage(entity.getType(), message);
			case "STATIC" -> img = Images.getImage(entity.getType(), message);
			}
		}

		setX(entity.getX());
		setY(entity.getY());
		setWidth(entity.getWidth());
		setHeight(entity.getHeight());
	}

}
