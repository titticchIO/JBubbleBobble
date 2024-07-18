package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import model.LevelMaker;
import view.EditorFrame;
import view.ImageLoader;
import view.Sprite;
import view.SpriteSelectionScrollPane;

public class Main {
	public static void main(String[] args) {
		//BufferedImage img = ImageLoader.importImg("/sprites/bubblun/image_5.png");
		EditorFrame ef = new EditorFrame();
		LevelMaker lm = new LevelMaker();
		/*
		for (Sprite s : ef.getEditorPanel().getSprites()) {
			s.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (SpriteSelectionScrollPane.isSelected()) {
						String imgPath = SpriteSelectionScrollPane.getActualSelection();
						BufferedImage img = ImageLoader.importImg(imgPath);
						s.updateSprite(img);
						ef.repaint();
					}
					else {System.out.println("niente è selezionato");}
					
				}
			});
		}
		*/
	}
}
