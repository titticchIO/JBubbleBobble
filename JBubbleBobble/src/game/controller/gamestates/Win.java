package game.controller.gamestates;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import game.controller.Game;
import game.model.Model;
import game.view.GameFrame.Screen;
import game.view.View;

public class Win extends State implements Statemethods {

	public Win(Game game) {
		super(game);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void repaint() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_R) {
			Model.getInstance().resetLevels();
			GameState.state = GameState.PLAYING;
			View.getInstance().getLevelPanel().renderTilesOnce();
			game.getGameFrame().showState(Screen.GAME);
		}	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
