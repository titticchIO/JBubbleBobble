package game.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import game.controller.gamestates.Menu;
import game.model.level.Level;

public class MenuPanel extends JPanel {

	private Menu menu;
	private BufferedImage menuImage;

	public MenuPanel(Menu menu) {
		this.menu = menu;
		setSize(new Dimension((int) (Level.GAME_WIDTH * LevelPanel.SCALE),
				(int) (Level.GAME_HEIGHT * LevelPanel.SCALE)));
		loadImage(); // Load the image once
		initMenu();

	}

	// Load the image once to avoid reloading on every repaint
	private void loadImage() {
		menuImage = ImageLoader.importImg("/MenuScreen.png");
	}

	private void initMenu() {
		setLayout(null);

		// Create and configure Play button
		JButton playButton = new JButton("Play") {
			{
				setBounds(300, 400, 100, 50);
				addActionListener(e -> menu.startGame());
			}
		};

		// Create and configure Editor button
		JButton editorButton = new JButton() {
			{
				setBounds(20, 20, 100, 50);
				addActionListener(e -> editor.controller.Main.main(null));
				ImageIcon editorButtonImageIcon = new ImageIcon(
						ImageLoader.importImg("/editor.png").getScaledInstance(100, 50, Image.SCALE_SMOOTH));
				setIcon(editorButtonImageIcon);
				setContentAreaFilled(false); // Make the background transparent
				setBorderPainted(false); // Remove the button border
				setFocusPainted(false); // Remove the focus border

			}
		};
		// Create and configure Leaderboard button
		JButton leaderboardButton = new JButton("Leaderboard") {
			{
				setBounds(20, 70, 100, 50);
				addActionListener(e -> showLeaderboard());
				ImageIcon editorButtonImageIcon = new ImageIcon(
						ImageLoader.importImg("/leaderboard.png").getScaledInstance(100, 50, Image.SCALE_SMOOTH));
				setIcon(editorButtonImageIcon);
				setContentAreaFilled(false); // Make the background transparent
				setBorderPainted(false); // Remove the button border
				setFocusPainted(false); // Remove the focus border
			}
		};

		// Add buttons to panel
		add(playButton);
		add(editorButton);
		add(leaderboardButton);
	}

	// Display leaderboard
	private void showLeaderboard() {
		JFrame leaderboardFrame = new JFrame("Leaderboard");
		LeaderboardPanel leaderboardPanel = new LeaderboardPanel();
		leaderboardFrame.add(leaderboardPanel);
		leaderboardFrame.setSize(450, 300);
		leaderboardFrame.setLocationRelativeTo(null); // Center the window
		leaderboardFrame.setVisible(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Draw the preloaded image, preventing repetitive loading
		if (menuImage != null) {
			g.drawImage(menuImage, 0, 0, getWidth(), getHeight(), null);
		}
	}
}
