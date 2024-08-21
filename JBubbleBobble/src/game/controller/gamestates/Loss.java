package game.controller.gamestates;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import game.controller.Game;

public class Loss extends State implements Statemethods {

    public Loss(Game game) {
        super(game);
    }

    @Override
    public void update() {
        // Loss-specific update logic (if any)
    }

    @Override
    public void repaint() {
        // Loss-specific repaint logic (if any)
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Handle mouse click if necessary
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Handle mouse press if necessary
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Handle mouse release if necessary
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Handle mouse move if necessary
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            game.resetGame();  // Use the resetGame method from the Game class
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Handle key release if necessary
    }
}
