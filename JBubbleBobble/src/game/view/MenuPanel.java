package game.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
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
        initMenu();
        loadMenuImage();  // Load the image once
    }

    // Load the image once to avoid reloading on every repaint
    private void loadMenuImage() {
        menuImage = ImageLoader.importImg("/MenuScreen.png");
    }

    private void initMenu() {
        setLayout(new GridBagLayout());

        // Create and configure Play button
        JButton playButton = new JButton("Play");
        playButton.setPreferredSize(new Dimension(200, 50));
        playButton.addActionListener(e -> menu.startGame());

        // Create and configure Editor button
        JButton editorButton = new JButton("Editor");
        editorButton.setPreferredSize(new Dimension(200, 50));
        editorButton.addActionListener(e -> editor.controller.Main.main(null));

        // Create and configure Leaderboard button
        JButton leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.setPreferredSize(new Dimension(200, 50));
        leaderboardButton.addActionListener(e -> showLeaderboard());

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
        leaderboardFrame.setLocationRelativeTo(null);  // Center the window
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
