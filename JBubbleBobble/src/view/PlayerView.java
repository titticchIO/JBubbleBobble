package view;

import java.awt.Graphics;

//extends EnityView
public class PlayerView extends EntityView {

	public PlayerView() {
		img = ImageLoader.importImg("/sprites/bubblun/image_5.png");
	}

	public void render(Graphics g) {
		g.drawImage(img, (int) x, (int) y, (int) width, (int) height, null);
	}

}
