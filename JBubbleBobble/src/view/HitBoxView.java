package view;

import java.awt.Color;
import java.awt.Graphics;

public class HitBoxView extends MovingEntityView {

	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.drawRect((int) x, (int) y, (int) width, (int) height);
	}
}
