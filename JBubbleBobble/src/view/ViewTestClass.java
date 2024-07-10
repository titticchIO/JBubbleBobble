package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import controller.PlayerController;
import model.Player;

public class ViewTestClass implements Runnable, MouseListener {

	private Thread gameThread;
	private final int FPS_SET = 120;

	public ViewTestClass() {
		startGameLoop();
		// creare hitbox e jframe e jpanel e metterceli dentro
		// hitbox view osserva hitbox
		// creare un hitbox controller test per muoverla
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
				// update

			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
