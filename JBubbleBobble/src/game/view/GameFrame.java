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
import game.model.level.Level;

public class GameFrame extends JFrame {

    public enum Screen {
        USER_SELECTION, MENU, GAME
    }

    private JPanel layoutPanel;
    private LevelPanel levelPanel;
    private MenuPanel menuPanel;
    private UserSelectionPanel userSelectionPanel;

    public GameFrame(Game game, PlayerController playerController, ActionListener actionListener, Menu menu) {
        layoutPanel = new JPanel(new CardLayout());
        layoutPanel.setSize(new Dimension((int) (Level.GAME_WIDTH * LevelPanel.SCALE),
                (int) (Level.GAME_HEIGHT * LevelPanel.SCALE)));

        JPanel gamePanel = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout()) {
            {
                add(new JLabel("Score: 0"));
                add(new JLabel("Highscore: 100"));
            }
        };
        gamePanel.add(panel, BorderLayout.NORTH);

        levelPanel = View.getInstance().getLevelPanel();
        gamePanel.add(levelPanel, BorderLayout.CENTER);

        menuPanel = new MenuPanel(menu);
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
}
