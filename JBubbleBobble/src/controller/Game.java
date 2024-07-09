package controller;

import model.*;
import view.*;

public class Game implements Runnable {
	private GameFrame gameFrame;
	private Player player;
	private Thread gameThread;
	private final int FPS_SET = 120;
	
	
	
	public Game() {
		this.player = Player.getInstance();
		PlayerView playerView = new PlayerView();
		player.addObserver(playerView);
		PlayerController pController = new PlayerController(player);
		gameFrame = new GameFrame(playerView,pController);
		startGameLoop();
	}
	
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS_SET;
		long lastFrame = System.nanoTime();
		long now = System.nanoTime();

		int frames = 0;
		long lastCheck = System.currentTimeMillis();

		while (true) {

			now = System.nanoTime();
			if (now - lastFrame >= timePerFrame) {
				lastFrame = now;
				frames++;
				player.updateEntity();
				gameFrame.repaint();
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
	}
	
}	
