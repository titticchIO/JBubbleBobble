package game.view;


import java.awt.event.ActionListener;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.controller.Game;

import game.controller.PlayerController;

public class GameFrame extends JFrame {
    private GamePanel gamePanel;
    private JLabel scoreLabel;
    private JLabel topScoreLabel;




	public GameFrame(Game game, PlayerController playerController, ActionListener actionListener) {
		this.gamePanel = new GamePanel(actionListener);
		// Attach the PlayerController as a KeyListener
		addKeyListener(playerController);
		setFocusable(true); // Ensure the frame is focusable
		setFocusTraversalKeysEnabled(false); // Disable focus traversal keys

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
