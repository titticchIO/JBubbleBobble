package game.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

import game.model.level.Level;

public class TransitionPanel extends JPanel {
    private Image currentLevelImage;
    private Image nextLevelImage;
    private float progress;  // Progresso della transizione da 0.0 a 1.0
    private Timer transitionTimer;
    private static final int TRANSITION_DURATION = 2000;  // Durata della transizione in millisecondi
    private GameFrame gameFrame;

    public TransitionPanel(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        this.progress = 0f;

        // Imposta le dimensioni del pannello in base al livello
        Dimension size = new Dimension((int) (Level.GAME_WIDTH * LevelPanel.SCALE), 
                                        (int) (Level.GAME_HEIGHT * LevelPanel.SCALE));
        setPreferredSize(size);

        // Inizializza il timer senza avviarlo
        transitionTimer = new Timer(10, null);
    }

    // Metodo per avviare la transizione
    public void startTransition(Image currentImage, Image nextImage) {
        this.currentLevelImage = currentImage;
        this.nextLevelImage = nextImage;
        this.progress = 0f;  // Resetta il progresso per ogni nuova transizione

        // Reimposta il timer per la nuova transizione
        if (transitionTimer.isRunning()) {
            transitionTimer.stop(); // Assicurati che il timer sia fermo
        }

        transitionTimer = new Timer(10, new ActionListener() {
            private long startTime = -1;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (startTime == -1) {
                    startTime = System.currentTimeMillis();
                }

                long elapsed = System.currentTimeMillis() - startTime;
                progress = Math.min(1.0f, elapsed / (float) TRANSITION_DURATION);

                // Se la transizione è completata
                if (progress >= 1.0f) {
                    transitionTimer.stop();
                    // Passa allo stato di gioco successivo
                    gameFrame.showState(GameFrame.Screen.GAME);
                }

                repaint();
            }
        });

        transitionTimer.start();  // Avvia il timer per la transizione
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int height = getHeight();

        // Calcola le posizioni verticali delle immagini in base al progresso
        int currentY = (int) (-progress * height);  // Il livello corrente si muove verso l'alto
        int nextY = (int) ((1 - progress) * height); // Il prossimo livello entra dal basso

        // Disegna l'immagine del livello corrente che scorre verso l'alto
        if (currentLevelImage != null) {
            g2d.drawImage(currentLevelImage, 0, currentY, getWidth(), getHeight(), null);
        }

        // Disegna l'immagine del prossimo livello che entra dal basso
        if (nextLevelImage != null) {
            g2d.drawImage(nextLevelImage, 0, nextY, getWidth(), getHeight(), null);
        }
    }
}
