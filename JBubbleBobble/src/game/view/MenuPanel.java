package game.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;

import game.model.Settings;
import game.model.level.Level;

public class MenuPanel extends JPanel {

	public MenuPanel(ActionListener actionListener) {
		setSize(new Dimension((int) (Level.GAME_WIDTH * LevelPanel.SCALE),
				(int) (Level.GAME_HEIGHT * LevelPanel.SCALE)));

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // Assicurati di chiamare il metodo della superclasse
		BufferedImage menuImage = ImageLoader.importImg("/MenuScreen.png");
		if (menuImage != null) {
			g.drawImage(menuImage, 0, 0, getWidth(), getHeight(), null);
		}
	}

}
