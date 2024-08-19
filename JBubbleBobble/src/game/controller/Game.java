package game.controller;

import game.view.*;
import game.view.GameFrame.Screen;
import game.controller.gamestates.Playing;
import game.model.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.controller.gamestates.GameState;
import game.controller.gamestates.Menu;

public class Game implements Runnable {
	private Thread gameThread;
	private final float GAME_SPEED = 0.1f;
	private final int FPS_SET = 120;
	private final int UPS_SET = (int) (200 * GAME_SPEED);
	private GameFrame gameFrame;

	private Playing playing;
	private Menu menu;

	private Model model;
	private View view;

	public Game() {
		model = Model.getInstance();
		view = View.getInstance();
		model.addObserver(view);
		model.loadLevels();
		playing = new Playing(this);
		menu = new Menu(this);
		ActionListener actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GameState.state = GameState.PLAYING;
				startGameLoop();
			}
		};
		gameFrame = new GameFrame(this, new PlayerController(this), actionListener);
		gameFrame.showState(Screen.MENU);

	}
	
	public Playing getPlaying() {
		return playing;
	}

	public Menu getMenu() {
		return menu;
	}

	public void startGameLoop() {
		GameState.state = GameState.PLAYING;
		gameFrame.getLevelPanel().renderTilesOnce();
		gameFrame.showState(Screen.GAME);
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void update() {
		switch (GameState.state) {
		case MENU:
			menu.update();
			break;
		case PLAYING:
			playing.update();
			break;
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

		while (true) {
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
//				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}

}
