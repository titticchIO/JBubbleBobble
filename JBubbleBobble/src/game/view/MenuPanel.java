package game.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;

import game.controller.Game;
import game.controller.gamestates.Menu;
import game.model.level.Level;

import editor.controller.Main; // Import necessario per avviare l'editor

public class MenuPanel extends JPanel {

    private Menu menu;

    public MenuPanel(Menu menu) {
        this.menu = menu;
        setSize(new Dimension((int) (Level.GAME_WIDTH * LevelPanel.SCALE),
                (int) (Level.GAME_HEIGHT * LevelPanel.SCALE)));
        initMenu();
    }

    private void initMenu() {
        // Imposta il layout per centrare i bottoni
        setLayout(new GridBagLayout());

        // Crea il bottone Play
        JButton playButton = new JButton("Play");
        playButton.setPreferredSize(new Dimension(200, 50));

        // Aggiungi un listener al bottone Play
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.startGame(); // Avvia il gioco quando il bottone viene cliccato
            }
        });

        // Crea il bottone Editor
        JButton editorButton = new JButton("Editor");
        editorButton.setPreferredSize(new Dimension(200, 50));

        // Aggiungi un listener al bottone Editor
        editorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.controller.Main.main(null); // Avvia l'editor quando il bottone viene cliccato
            }
        });

        // Crea il bottone Leaderboard
        JButton leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.setPreferredSize(new Dimension(200, 50));

        // Aggiungi un listener al bottone Leaderboard
        leaderboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLeaderboard(); // Mostra la leaderboard quando il bottone viene cliccato
            }
        });

        // Aggiungi i bottoni al pannello
        add(playButton);
        add(editorButton);
        add(leaderboardButton);
    }

    // Metodo per mostrare la leaderboard
    private void showLeaderboard() {
        JFrame leaderboardFrame = new JFrame("Leaderboard");
        LeaderboardPanel leaderboardPanel = new LeaderboardPanel();
        leaderboardFrame.add(leaderboardPanel);
        leaderboardFrame.setSize(450, 300);
        leaderboardFrame.setLocationRelativeTo(null); // Centra la finestra sullo schermo
        leaderboardFrame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage menuImage = ImageLoader.importImg("/MenuScreen.png");
        if (menuImage != null) {
            g.drawImage(menuImage, 0, 0, getWidth(), getHeight(), null);
        }
    }
}
