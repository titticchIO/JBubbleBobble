package view;

import java.awt.Color;
import java.awt.Graphics;

//extends EnityView
public class PlayerView extends EntityView {

	public PlayerView() {
		img = ImageLoader.importImg("/sprites/bubblun/image_5.png");
	}
	
	
//	cancella()
	
	
	public void render(Graphics g) {
		g.drawImage(img, (int) x, (int) y, (int) width, (int) height, null);
		g.setColor(Color.BLACK);
		g.drawRect((int) x, (int) y, (int) width, (int) height);
	}

}
