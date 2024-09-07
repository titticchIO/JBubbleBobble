package game.controller.gamestates;

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import game.controller.Controller;

import game.model.Model;
import game.model.Model.ModelState;
import game.model.entities.MovingEntity.Direction;
import game.view.frames.GameFrame.Screen;

/**
 * Represents the state of the game when it is being played.
 * <p>
 * The {@code Playing} class is responsible for handling the game logic during
 * the gameplay state, including updating the game model, processing user input,
 * and managing the transition between different game states such as WIN, LOSS,
 * and PAUSE.
 * </p>
 */
public class Playing extends State implements Statemethods {

	/**
	 * Constructs a new {@code Playing} state.
	 * 
	 * @param controller the controller that manages the game
	 */
	public Playing(Controller controller) {
		super(controller);

	}

	/**
	 * Updates the game model and checks for game state changes.
	 * <p>
	 * This method updates the game model and checks if the game state has changed
	 * to WIN or LOSS. It handles the transition to the appropriate screen and stops
	 * the game loop if necessary.
	 * </p>
	 */
	@Override
	public void update() {
		Model.getInstance().updateModel();
		if (Model.getInstance().getModelState() == ModelState.LOSS) {
			GameState.state = GameState.LOSS;
			controller.getGameFrame().showState(Screen.LOSS);
			new Timer("stop game loop").schedule(new TimerTask() {

				@Override
				public void run() {
					controller.stopGameLoop();
					cancel();

				}
			}, 100);
		}

		if (Model.getInstance().getModelState() == ModelState.WIN) {
			GameState.state = GameState.WIN;
			controller.getGameFrame().showState(Screen.WIN);
			controller.stopGameLoop();
		}
	}

	/**
	 * Handles key press events for gameplay actions.
	 * <p>
	 * This method processes user input when a key is pressed, including moving the
	 * player, making the player jump, and shooting. It also handles the pause
	 * functionality when the ESC key is pressed.
	 * </p>
	 * 
	 * @param e the key event that occurred
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			GameState.state = GameState.PAUSE;
			controller.getGameFrame().showState(Screen.PAUSE);
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

	/**
	 * Handles key release events for gameplay actions.
	 * <p>
	 * This method processes user input when a key is released, including stopping
	 * the player's movement when the direction keys are released.
	 * </p>
	 * 
	 * @param e the key event that occurred
	 */
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
