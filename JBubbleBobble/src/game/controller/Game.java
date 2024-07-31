package game.controller;

import game.model.*;
import game.model.bubbles.BubbleManager;
import game.model.enemies.Enemy;
import game.model.entities.Player;
import game.model.level.Level;
import game.view.*;

public class Game implements Runnable {
	private GameFrame gameFrame;
	private Thread gameThread;
	private final float GAME_SPEED = 1.0f;
	private final int FPS_SET = 120;
	private final int UPS_SET = (int) (200 * GAME_SPEED);
	private Level currentLevel;

	public Game() {
		Level livello1 = new Level(2);
		this.currentLevel = livello1;
		LevelView livello1View = new LevelView(livello1);
		BubbleManager.getInstance().addObserver(livello1View);
		gameFrame = new GameFrame(livello1View);
		startGameLoop();
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void update() {
		currentLevel.updateLevel();
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
