package controller;

import model.*;
import model.level.Level;
import static view.GameFrame.TILE_SIZE;
import view.*;

public class Game implements Runnable {
	private GameFrame gameFrame;
	private Player player;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;

	public Game() {
		this.player = Player.getInstance(200, 200, TILE_SIZE, TILE_SIZE);
		PlayerView playerView = new PlayerView();
		player.addObserver(playerView);
		PlayerController pController = new PlayerController(player);
		player.notifyPosition();
		Level livello1 = new Level(1);
		livello1.loadLevelTiles();  //prova
		gameFrame = new GameFrame(playerView, pController,livello1);
		startGameLoop();
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void update() {
		player.updateEntity();
		gameFrame.repaint();
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
				gameFrame.paintComponents(gameFrame.getGraphics());;
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
