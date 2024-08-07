package game.controller.gamestates;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import game.controller.Game;
import game.controller.PlayerController;
import game.model.bubbles.BubbleManager;
import game.model.entities.MovingEntity.Directions;
import game.model.level.Level;
import game.view.GameFrame;
import game.view.LevelView;

public class Playing extends State implements Statemethods {

	private GameFrame gameFrame;
	private Level currentLevel;

	public Playing(Game game, GameFrame gameFrame) {
		super(game);
		initClasses(gameFrame);
	}

	private void initClasses(GameFrame gameFrame) {
		Level livello1 = new Level(111);
		currentLevel = livello1;
		this.gameFrame = gameFrame;
	}

	public Level getCurrentLevel() {
		return currentLevel;
	}

	@Override
	public void update() {
		currentLevel.updateLevel();
	}

	@Override
	public void repaint() {
		gameFrame.repaint();
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
			currentLevel.getPlayer().jump();
			break;
		case KeyEvent.VK_A:
			currentLevel.getPlayer().move(Directions.LEFT);
			currentLevel.getPlayer().setDirections(Directions.LEFT);
			break;
		case KeyEvent.VK_D:
			currentLevel.getPlayer().move(Directions.RIGHT);
			currentLevel.getPlayer().setDirections(Directions.RIGHT);
			break;
		case KeyEvent.VK_SPACE:
			currentLevel.getPlayer().shootBubble(BubbleManager.getInstance());
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			if (currentLevel.getPlayer().getxSpeed() < 0)
				currentLevel.getPlayer().stop();
			break;
		case KeyEvent.VK_D:
			if (currentLevel.getPlayer().getxSpeed() > 0)
				currentLevel.getPlayer().stop();
			break;
		}
	}

}
