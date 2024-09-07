package game.controller.gamestates;

import java.awt.event.KeyEvent;

/**
 * The {@code Statemethods} class contains the methods used by the classes
 * extending {@code State}
 */
public interface Statemethods {
	
	public void update();

	public void keyPressed(KeyEvent e);

	public void keyReleased(KeyEvent e);

}
