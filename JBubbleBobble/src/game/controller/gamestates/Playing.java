package game.controller.gamestates;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import game.controller.Game;

import game.model.Model;
import game.model.bubbles.BubbleManager;
import game.model.entities.MovingEntity.Direction;
import game.model.level.Level;

public class Playing extends State implements Statemethods {

	private Level currentLevel;

	public Playing(Game game) {
		super(game);
		initClasses();
	}

	private void initClasses() {
		currentLevel = Model.getInstance().getCurrentLevel();

	}

	public Level getCurrentLevel() {
		return currentLevel;
	}

	@Override
	public void update() {
		Model.getInstance().updateModel();
	}

	@Override
	public void repaint() {

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
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			currentLevel.getPlayer().setJumping(true);
			currentLevel.getPlayer().jump();
			break;
		case KeyEvent.VK_A:
			currentLevel.getPlayer().setDirection(Direction.LEFT);
			currentLevel.getPlayer().move(1);
			break;
		case KeyEvent.VK_D:
			currentLevel.getPlayer().setDirection(Direction.RIGHT);
			currentLevel.getPlayer().move(1);
			break;
		case KeyEvent.VK_SPACE:
			currentLevel.getPlayer().shootBubble();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			currentLevel.getPlayer().setJumping(false);
			break;
		case KeyEvent.VK_A:
			if (currentLevel.getPlayer().getxSpeed() <= 0)
				currentLevel.getPlayer().stop();
			break;
		case KeyEvent.VK_D:
			if (currentLevel.getPlayer().getxSpeed() >= 0)
				currentLevel.getPlayer().stop();
			break;
		}
	}

}
