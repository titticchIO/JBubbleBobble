package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import controller.PlayerController;
import model.Player;
import model.entity.Entity;

public class ViewTestClass implements Runnable, MouseListener {
    private GameFrame gameFrame;
    private Player player;
    private Entity e1;
    private Thread gameThread;
    private final int FPS_SET = 120;


    public ViewTestClass() {
    	e1 = new Entity(50, 50, 16, 16);
        this.player = Player.getInstance(200, 200, 40, 40);
//        player.setInitialPosition(200, 200);
        player.updateEntity();
        PlayerView playerView = new PlayerView();
        player.addObserver(playerView);
        PlayerController pController = new PlayerController(player);
        gameFrame = new GameFrame(playerView, pController);
//        player.setInitialPosition(200, 200);
        // Aggiungi il MouseListener al gameFrame
        gameFrame.addMouseListener(this);
        
        // Configura il GameFrame
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);

        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        long lastFrame = System.nanoTime();
        long now = System.nanoTime();

        int frames = 0;
        long lastCheck = System.currentTimeMillis();

        while (true) {
            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {
                lastFrame = now;
                frames++;
                //System.out.println(e1.getX() + ":" +  e1.getY());
                //System.out.println(e1.hit(player));
                
                // update
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
    	if (e1 != null) {
    		gameFrame.repaint();
            e1.setX(e.getX());
            e1.setY(e.getY());
            System.out.println("gg");
            System.out.println("X: " + e1.getX());
            System.out.println("Y: " + e1.getY());
            System.out.println(player.getX());
            System.out.println(player.getY());
            if (player.hit(e1)) System.out.println("true");
            else System.out.println("false");
        } else {
            System.out.println("Entity e1 is not initialized.");
        }
    	
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    public static void main(String[] args) {
        new ViewTestClass();
    }
}
