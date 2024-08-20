package game.view;


import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.controller.Game;
import game.controller.PlayerController;
import game.controller.gamestates.GameState;
import game.controller.gamestates.Menu;
import game.model.Model;
import game.model.level.Level;

public class GameFrame extends JFrame {

    public enum Screen {
        USER_SELECTION, MENU, GAME, WIN
    }

    private JPanel layoutPanel;
    private LevelPanel levelPanel;
    private MenuPanel menuPanel;
    private WinPanel winPanel;
    private UserSelectionPanel userSelectionPanel;
    
    // Aggiunti per mostrare e aggiornare punti e highscore
    private JLabel scoreLabel;
    private JLabel highScoreLabel;

    public GameFrame(Game game, PlayerController playerController, Menu menu) {
        layoutPanel = new JPanel(new CardLayout());
        layoutPanel.setSize(new Dimension((int) (Level.GAME_WIDTH * LevelPanel.SCALE),
                (int) (Level.GAME_HEIGHT * LevelPanel.SCALE)));

        JPanel gamePanel = new JPanel(new BorderLayout());
        JPanel scorePanel = new JPanel(new GridBagLayout());
        
        // Inizializzazione delle label con valori 0
        scoreLabel = new JLabel("Score: 0");
        highScoreLabel = new JLabel("Highscore: 0");
        
        scorePanel.add(scoreLabel);
        scorePanel.add(highScoreLabel);

        gamePanel.add(scorePanel, BorderLayout.NORTH);

        levelPanel = View.getInstance(this).getLevelPanel();
        gamePanel.add(levelPanel, BorderLayout.CENTER);

        menuPanel = new MenuPanel(menu);
        winPanel=new WinPanel();
        userSelectionPanel = new UserSelectionPanel(e -> {
        	
            // Logica per selezionare l'utente e passare al menu
            ((CardLayout) layoutPanel.getLayout()).show(layoutPanel, Screen.MENU.name());
        });

        // Attach the PlayerController as a KeyListener
        addKeyListener(playerController);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        layoutPanel.add(userSelectionPanel, Screen.USER_SELECTION.name());
        layoutPanel.add(menuPanel, Screen.MENU.name());
        layoutPanel.add(gamePanel, Screen.GAME.name());
        layoutPanel.add(winPanel, Screen.WIN.name());
        

        add(layoutPanel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public LevelPanel getLevelPanel() {
        return levelPanel;
    }

    public void showState(Screen screen) {
        ((CardLayout) layoutPanel.getLayout()).show(layoutPanel, screen.name());
        layoutPanel.revalidate();
        layoutPanel.repaint();
    }

    @Override
    public void repaint() {
        super.repaint();
        if (GameState.state == GameState.PLAYING) {
            levelPanel.repaint();
        }
    }

    // Metodo per aggiornare i punti e l'highscore
    public void updateScoreAndHighscore() {
        int currentPoints = Model.getInstance().getCurrentUser().getPoints();
        int highScore = Model.getInstance().getCurrentUser().getHighScore();

        scoreLabel.setText("Score: " + currentPoints);
        highScoreLabel.setText("Highscore: " + highScore);
    }
}
