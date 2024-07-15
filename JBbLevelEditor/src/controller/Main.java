package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import view.EditorFrame;
import view.ImageLoader;
import view.Sprite;

public class Main {
	public static void main(String[] args) {
		BufferedImage img = ImageLoader.importImg("/sprites/bubblun/image_5.png");
		EditorFrame ef = new EditorFrame();
		for (Sprite s : ef.getEditorPanel().getSprites()) {
			s.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					s.updateSprite(img);
					ef.repaint();
				}
			});
		}
	}
}
