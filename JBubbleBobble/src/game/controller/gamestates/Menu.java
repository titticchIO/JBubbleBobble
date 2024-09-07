package game.controller.gamestates;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import game.controller.Controller;
import game.model.Model;

public class Menu extends State implements Statemethods {

	public Menu(Controller game) {
		super(game);

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_P) {
			controller.startGameLoop();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public void startGame() {
		controller.startGameLoop();
	}

}
