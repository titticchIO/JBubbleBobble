package game.controller.gamestates;

import java.awt.event.KeyEvent;

import game.controller.Controller;
import game.view.frames.GameFrame.Screen;

/**
 * Represents {@code Pause} the pause state in the game. This state is active
 * when the game is paused and handles user inputs to transition back to the
 * playing state.
 * <p>
 * The class extends {@link State} and implements {@link Statemethods}. It
 * provides specific implementations for updating the state and handling key
 * events, such as resuming the game when the ESCAPE key is pressed.
 * </p>
 */
public class Pause extends State implements Statemethods {

	/**
	 * Constructs a {@code Pause} state with the given controller.
	 * 
	 * @param controller the controller used to manage game state transitions
	 */
	public Pause(Controller controller) {
		super(controller);
	}

	@Override
	public void update() {
	}

	/**
	 * Handles key press events. If the ESCAPE key is pressed, it resumes the game
	 * by setting the game state to {@link GameState#PLAYING} and displaying the
	 * game screen.
	 * 
	 * @param e the key event that occurred
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			GameState.state = GameState.PLAYING;
			controller.getGameFrame().showState(Screen.GAME);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
