package game.controller;

import game.model.level.Level;
import game.view.*;
import game.controller.gamestates.Playing;

import java.awt.Graphics;

import game.controller.gamestates.GameState;
import game.controller.gamestates.Menu;

public class Game implements Runnable {
	private Thread gameThread;
	private final float GAME_SPEED = 1.0f;
	private final int FPS_SET = 120;
	private final int UPS_SET = (int) (200 * GAME_SPEED);
	private GameFrame gameFrame;

	private Playing playing;
	private Menu menu;

	public Game() {
		playing = new Playing(this);
		
		startGameLoop();
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void update() {
		switch (GameState.state) {
		case MENU:
			//forse superfluo
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
		double timePerAnimationUpdate = 1000000000.0 / 4; // 4 aggiornamenti al secondo
		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;
		double deltaA = 0; // Delta per l'animazion

		while (true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			deltaA += (currentTime - previousTime) / timePerAnimationUpdate;
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
			if (deltaA >= 1) {
				gameFrame.getGamePanel().getPlayerView().updateAnimationImg();
				deltaA--;
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
