package game.controller.gamestates;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import game.controller.AudioManager;
import game.controller.Controller;

import game.model.Model;
import game.model.Model.ModelState;
import game.model.bubbles.BubbleManager;
import game.model.entities.MovingEntity.Direction;
import game.model.level.Level;
import game.view.AnimationAndImagesLoader;
import game.view.frames.GameFrame.Screen;

public class Playing extends State implements Statemethods {

	public Playing(Controller game) {
		super(game);

	}

	@Override
	public void update() {
		Model.getInstance().updateModel();
		if (Model.getInstance().getModelState() == ModelState.LOSS) {
			GameState.state = GameState.LOSS;
			game.getGameFrame().showState(Screen.LOSS);
		}

		if (Model.getInstance().getModelState() == ModelState.WIN) {
			GameState.state = GameState.WIN;
			game.getGameFrame().showState(Screen.WIN);
		}
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
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			GameState.state = GameState.PAUSE;
			game.getGameFrame().showState(Screen.PAUSE);
		}

		switch (e.getKeyCode()) {
		case KeyEvent.VK_W, KeyEvent.VK_UP:
			if (!Model.getInstance().getCurrentLevel().getPlayer().isStunned())
				Model.getInstance().getCurrentLevel().getPlayer().setJumping(true);
			break;
		case KeyEvent.VK_A, KeyEvent.VK_LEFT:
			if (!Model.getInstance().getCurrentLevel().getPlayer().isStunned()) {
				Model.getInstance().getCurrentLevel().getPlayer().setDirection(Direction.LEFT);
				Model.getInstance().getCurrentLevel().getPlayer().move(0.7f);
			}
			break;
		case KeyEvent.VK_D, KeyEvent.VK_RIGHT:
			if (!Model.getInstance().getCurrentLevel().getPlayer().isStunned()) {
				Model.getInstance().getCurrentLevel().getPlayer().setDirection(Direction.RIGHT);
				Model.getInstance().getCurrentLevel().getPlayer().move(0.7f);
			}
			break;
		case KeyEvent.VK_SPACE:
			if (!Model.getInstance().getCurrentLevel().getPlayer().isStunned())
				Model.getInstance().getCurrentLevel().getPlayer().shoot();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W, KeyEvent.VK_UP:
			Model.getInstance().getCurrentLevel().getPlayer().setJumping(false);
			break;
		case KeyEvent.VK_A, KeyEvent.VK_LEFT:
			if (Model.getInstance().getCurrentLevel().getPlayer().getxSpeed() <= 0)
				Model.getInstance().getCurrentLevel().getPlayer().stop();
			break;
		case KeyEvent.VK_D, KeyEvent.VK_RIGHT:
			if (Model.getInstance().getCurrentLevel().getPlayer().getxSpeed() >= 0)
				Model.getInstance().getCurrentLevel().getPlayer().stop();
			break;
		}
	}

}
