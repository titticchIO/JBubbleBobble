package game.view;

import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import game.model.Settings;
import utils.ImageLoader;

public class MenuPanel extends JPanel {
	private JButton playButton;

	public MenuPanel(ActionListener actionListener) {
		playButton = new JButton("PLAY");
		playButton.addActionListener(actionListener);
		setLayout(new GridBagLayout());
		add(playButton);

	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(ImageLoader.importImg("/MenuScreen.png"), 0, 0, Settings.GAME_WIDTH, Settings.GAME_HEIGHT, null);
	}

}
