package game.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import javax.swing.JButton;
import javax.swing.JPanel;

import game.controller.ActionListenersManager;
import game.controller.Game;
import game.model.level.Level;

public class PausePanel extends JPanel {
    
    private BufferedImage levelImage;
    private LevelPanel levelPanel;
    private Game game;

    public PausePanel(LevelPanel levelPanel, Game game) {
        this.levelPanel = levelPanel;
        this.game = game;

        setSize(new Dimension((int) (Level.GAME_WIDTH * LevelPanel.SCALE),
                (int) (Level.GAME_HEIGHT * LevelPanel.SCALE)));

        setLayout(null);

        // Crea i bottoni
        JButton resumeButton = new JButton("Resume");
        resumeButton.addActionListener(ActionListenersManager.resumeGame(game));
        resumeButton.setBounds(220,220, 100, 30);
        
        
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(ActionListenersManager.backToMenu(game));
        exitButton.setBounds(220,270, 100, 30);
        
        // Aggiungi i bottoni al pannello
        add(resumeButton);
        add(exitButton);
    }

    public void drawBackground() {
        // Cattura l'immagine attuale del livello
        this.levelImage = new BufferedImage(levelPanel.getWidth(), levelPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = levelImage.getGraphics();
        levelPanel.paint(g);  // Disegna il livello sul BufferedImage
        g.dispose();

        // Applica un filtro di sfocatura all'immagine catturata
        blurImage();
    }

    private void blurImage() {
        if (levelImage != null) {
            // Kernel di sfocatura più grande 5x5 per una sfocatura più intensa
            float[] blurKernel = {
                1f/25f, 1f/25f, 1f/25f, 1f/25f, 1f/25f,
                1f/25f, 1f/25f, 1f/25f, 1f/25f, 1f/25f,
                1f/25f, 1f/25f, 1f/25f, 1f/25f, 1f/25f,
                1f/25f, 1f/25f, 1f/25f, 1f/25f, 1f/25f,
                1f/25f, 1f/25f, 1f/25f, 1f/25f, 1f/25f
            };

            Kernel kernel = new Kernel(5, 5, blurKernel);
            ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            levelImage = op.filter(levelImage, null);
        }
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Disegna l'immagine del livello come sfondo
        if (levelImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(levelImage, 0, 0, getWidth(), getHeight(), null);
        }
    }
}
