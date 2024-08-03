package game.view;


import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Observable;

public class MovingEntityView extends EntityView {

	private Animation animation;
	private Iterator<BufferedImage> animationIterator;

	public MovingEntityView(String imageCode) {

		super(imageCode);

		if (imageCode.substring(0,1).equals("P")) {
			animation = new Animation();
			animationIterator = animation.iterator();
		}
	}

	public void updateAnimationImg() {
		img = animationIterator.next();
	}
}
