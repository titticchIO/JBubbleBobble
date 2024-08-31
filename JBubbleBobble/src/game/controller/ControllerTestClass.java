package game.controller;

import javax.swing.ImageIcon;

import game.view.AnimationAndImagesLoader;

public class ControllerTestClass {
	public static void main(String[] args) {
		 // Esempio di utilizzo
        String relativePath = "player/left_walk.gif";
        String absolutePath = AnimationAndImagesLoader.getAbsolutePath(relativePath);
        
        // Usa il percorso assoluto per creare l'ImageIcon
        ImageIcon icon = new ImageIcon(absolutePath);
        System.out.println("Percorso assoluto: " + absolutePath);
	}
}
