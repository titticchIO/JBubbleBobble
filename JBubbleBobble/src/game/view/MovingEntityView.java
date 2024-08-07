package game.view;


import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Observable;

import game.model.Images;
import game.model.enemies.Monsta;
import game.model.entities.Entity;
import game.model.entities.MovingEntity;
import game.model.entities.Player;

public class MovingEntityView extends EntityView {

	private Animation animation;
	private Iterator<BufferedImage> animationIterator;

	public MovingEntityView(String type, String positionCode) {

		super(type, positionCode);

		/*
		if (type.equals("P")) {
			animation = new Animation();
			animationIterator = animation.iterator();
		}
		*/
	}

	/*
	public void updateAnimationImg() {
		img = animationIterator.next();
	}
	*/
	
	@Override
	public void update(Observable o, Object arg) {
		
		if (arg instanceof String && ((String) arg).equals("pop")) {
			toDelete = true;
		} else {
			MovingEntity entity = (MovingEntity) o;
			
			if (entity.isToChange()) {
				String direction = entity.getPositionCode(); // Ottieni la direzione (left o right)
	            img = Images.getImage(entity.getType(), direction); // Aggiorna l'immagine in base alla direzione
			}
			

			setX(entity.getX());
			setY(entity.getY());
			setWidth(entity.getWidth());
			setHeight(entity.getHeight());
		}
		
	}
	
	
}
