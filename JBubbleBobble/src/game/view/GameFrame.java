package game.view;

import javax.swing.*;
import java.awt.BorderLayout;
import game.controller.PlayerController;
import game.model.entities.Player;
import game.model.level.Level;

public class GameFrame extends JFrame {
    private GamePanel gamePanel;
    private JLabel scoreLabel;
    private JLabel topScoreLabel;

    public GameFrame(LevelView levelView) {
        this.gamePanel = new GamePanel(levelView);

        // Creare un pannello superiore per mostrare il punteggio
        JPanel topPanel = new JPanel();
        scoreLabel = new JLabel("Score: 0");
        topScoreLabel = new JLabel("Top Score: 100");
        topPanel.add(scoreLabel);
        topPanel.add(topScoreLabel);

        // Imposta il layout e aggiungi il pannello superiore
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        // Attach the PlayerController as a KeyListener
        addKeyListener(new PlayerController());
        setFocusable(true); // Ensure the frame is focusable
        setFocusTraversalKeysEnabled(false); // Disable focus traversal keys

        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
    
    @Override
    public void repaint() {
        super.repaint();
        gamePanel.repaint();
    }

}
