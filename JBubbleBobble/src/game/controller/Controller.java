package game.controller;

import game.view.*;
import game.view.frames.GameFrame;
import game.view.frames.GameFrame.Screen;
import game.controller.gamestates.Playing;
import game.model.Model;

import java.util.Timer;
import java.util.TimerTask;

import game.controller.gamestates.End;
import game.controller.gamestates.GameState;
import game.controller.gamestates.Menu;
import game.controller.gamestates.Pause;

/**
 * The Controller class {@code Controller} is responsible for managing the game
 * loop and state transitions. It handles the game's main logic, including
 * updating the game state, rendering, and managing different game states. This
 * class implements the Runnable interface to allow the game loop to run on a
 * separate thread.
 */
public class Controller implements Runnable {
	private Thread gameThread;
	private boolean running;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	private GameFrame gameFrame;

	private Playing playing;
	private Menu menu;
	private End end;
	private Pause pause;

	private Model model;
	private View view;

	/**
	 * Constructs a new Controller instance. Initializes game states, view, model,
	 * and game frame. Displays the menu screen by default.
	 */
	public Controller() {
		new Timer("Music Timer").schedule(new TimerTask() {

			@Override
			public void run() {
				AudioManager.getInstance().play(Paths.getAbsolutePath("audio/soundtrack.wav"));
			}
		}, 0, 107000);
		model = Model.getInstance();
		menu = new Menu(this);
		end = new End(this);
		pause = new Pause(this);
		gameFrame = new GameFrame(this, new InputManager(this));
		view = View.getInstance(gameFrame);
		model.addObserver(view);
		playing = new Playing(this);
		gameFrame.showState(Screen.MENU);
	}

	/**
	 * Gets the Playing game state.
	 *
	 * @return the Playing game state
	 */
	public Playing getPlaying() {
		return playing;
	}

	/**
	 * Gets the Menu game state.
	 *
	 * @return the Menu game state
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * Gets the Pause game state.
	 *
	 * @return the Pause game state
	 */
	public Pause getPause() {
		return pause;
	}

	/**
	 * Sets the Pause game state.
	 *
	 * @param pause the Pause game state to set
	 */
	public void setPause(Pause pause) {
		this.pause = pause;
	}

	/**
	 * Gets the End game state.
	 *
	 * @return the End game state
	 */
	public End getEnd() {
		return end;
	}

	/**
	 * Gets the GameFrame associated with this Controller.
	 *
	 * @return the GameFrame
	 */
	public GameFrame getGameFrame() {
		return gameFrame;
	}

	/**
	 * Starts the game loop. Initializes and starts the game thread, and transitions
	 * to the game screen.
	 */
	public void startGameLoop() {
		if (gameThread != null && running) {
			stopGameLoop();
		}
		Model.getInstance().loadLevels();
		gameFrame.getLevelPanel().renderTilesOnce(gameFrame);
		gameFrame.showState(Screen.GAME);
		running = true;
		gameThread = new Thread(this);
		gameThread.start();
		GameState.state = GameState.PLAYING;
	}

	/**
	 * Stops the game loop and waits for the game thread to finish execution.
	 */
	public void stopGameLoop() {
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Resets the game to its initial state. Reloads the model and transitions to
	 * the menu screen.
	 */
	public void resetGame() {
		Model.getInstance().resetModel();
		View.getInstance().getLevelPanel().renderTilesOnce(gameFrame);
		GameState.state = GameState.MENU;
		gameFrame.showState(Screen.MENU);
	}

	/**
	 * Updates the current game state based on the GameState.
	 */
	private void update() {
		switch (GameState.state) {
		case MENU -> menu.update();
		case PLAYING -> playing.update();
		case WIN, LOSS -> end.update();
		case PAUSE -> pause.update();
		}
	}

	/**
	 * The main game loop executed on a separate thread. Handles the timing of
	 * updates and rendering.
	 */
	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;
		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (running) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}

			if (deltaF >= 1) {
				gameFrame.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}
}