package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import model.entity.MovingEntity;

//extends EnityView
public class PlayerView implements Observer {
	private BufferedImage img;
	private float x, y;

	public PlayerView() {
		img = ImageLoader.importImg("/sprites/bubblun/image_5.png");
	}

	public void render(Graphics g) {
		g.drawImage(img, (int) x, (int) y, null);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (((String) arg).equals("moved")) {
			MovingEntity obj = (MovingEntity) o;
			x = obj.getX();
			y = obj.getY();
		}
		
	}

}
