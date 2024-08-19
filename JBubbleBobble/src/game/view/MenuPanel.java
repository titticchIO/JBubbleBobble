package game.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;

import game.controller.Game;
import game.controller.gamestates.Menu;
import game.model.level.Level;

public class MenuPanel extends JPanel {

    private Menu menu;

    public MenuPanel(Menu menu) {
    	this.menu = menu;
        setSize(new Dimension((int) (Level.GAME_WIDTH * LevelPanel.SCALE),
                (int) (Level.GAME_HEIGHT * LevelPanel.SCALE)));
        initMenu();
    }

    private void initMenu() {
        // Imposta il layout per centrare il bottone
        setLayout(new GridBagLayout());

        // Crea il bottone Play
        JButton playButton = new JButton("Play");
        playButton.setPreferredSize(new Dimension(200, 50));

        // Aggiungi un listener al bottone
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               menu.startGame();;  // Avvia il gioco quando il bottone viene cliccato
            }
        });

        // Aggiungi il bottone al pannello
        add(playButton);
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
