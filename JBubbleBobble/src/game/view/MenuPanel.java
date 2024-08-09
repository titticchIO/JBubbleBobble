package game.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import game.model.Settings;
import game.model.level.Level;

public class MenuPanel extends JPanel {
	private JButton playButton;

	public MenuPanel(ActionListener actionListener) {
		playButton = new JButton("PLAY");
		playButton.addActionListener(actionListener);
		setLayout(new GridBagLayout());
		add(playButton);
		setSize(new Dimension((int) (Level.GAME_WIDTH * GamePanel.SCALE),
				(int) (Level.GAME_HEIGHT * GamePanel.SCALE)));

	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(ImageLoader.importImg("/MenuScreen.png"), 0, 0, (int) (Level.GAME_WIDTH * GamePanel.SCALE),
				(int) (Level.GAME_HEIGHT * GamePanel.SCALE), null);
	}

}
