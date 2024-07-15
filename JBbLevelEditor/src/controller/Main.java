package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import view.EditorFrame;
import view.EditorPanel;
import view.Sprite;

public class Main {
	public static void main(String[] args) {
		
		EditorFrame ef=new EditorFrame();
		for(Sprite s: ef.getEditorPanel().getSprites()) {
			s.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(s.getX()+"|"+s.getY());
					
				}
				
			});
		}
	}
}
