package game.controller;

import game.view.*;
import game.view.GameFrame.Screen;
import game.controller.gamestates.Playing;
import game.model.Model;
import game.controller.gamestates.State;
import game.controller.gamestates.Win;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.controller.gamestates.GameState;
import game.controller.gamestates.Loss;
import game.controller.gamestates.Menu;

public class Game implements Runnable {
	private Thread gameThread;
	private boolean running;
	private final float GAME_SPEED = 1.0f;
	private final int FPS_SET = 120;
	private final int UPS_SET = (int) (200 * GAME_SPEED);
	private GameFrame gameFrame;

	private Playing playing;
	private Menu menu;
	private Win win;
	private Loss loss;

	private Model model;
	private View view;

	public Game() {
		model = Model.getInstance();
		menu = new Menu(this);
		win = new Win(this);
		loss = new Loss(this);
		gameFrame = new GameFrame(this, new InputManager(this), menu);
		view = View.getInstance(gameFrame);
		model.addObserver(view);
		playing = new Playing(this);
		gameFrame.showState(Screen.USER_SELECTION);
	}

	public Playing getPlaying() {
		return playing;
	}

	public Menu getMenu() {
		return menu;
	}

	public Win getWin() {
		return win;
	}

	public Loss getLoss() {
		return loss;
	}

	public GameFrame getGameFrame() {
		return gameFrame;
	}

	public void startGameLoop() {
		if (gameThread != null && running) {
			stopGameLoop(); // Ensure the previous thread is stopped
		}
		Model.getInstance().loadLevels();
		gameFrame.getLevelPanel().renderTilesOnce();
		gameFrame.showState(Screen.GAME);
		running = true;
		gameThread = new Thread(this);
		gameThread.start();
		GameState.state = GameState.PLAYING;
	}

	public void stopGameLoop() {
		running = false;
		try {
			gameThread.join(); // Wait for the current thread to finish
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void restartGame() {
		stopGameLoop(); // Stop the current game thread
		resetGame(); // Reset the game state, objects, and components
		startGameLoop(); // Start a new game thread
	}

	public void resetGame() {
        // Reset the model to its initial state
        Model.getInstance().resetModel();
        
        // Reset the view (if needed, additional UI elements like score can be reset here)
        View.getInstance().getLevelPanel().renderTilesOnce();

        // Set the game state back to the menu
        GameState.state = GameState.MENU;

        // Display the menu screen
        gameFrame.showState(Screen.MENU);
    }

	public void update() {
		switch (GameState.state) {
		case MENU -> menu.update();
		case PLAYING -> playing.update();
		case WIN -> win.update();
		case LOSS -> loss.update();
		}
	}

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
