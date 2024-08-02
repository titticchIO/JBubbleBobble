package game.view;

import java.awt.image.BufferedImage;

public class ViewTestClass {

	public static void main(String[] args) {
		MovingEntityView b1 = new MovingEntityView("Bubble");
		MovingEntityView b2 = new MovingEntityView("Bubble");
		
		b1.setX(10);
		b1.setX(0);
		System.out.println(b1.equals(b2));
	}
}
