package view;

import controller.PlayerController;
import model.Player;

public class ViewTestClass {
	public static void main(String[] args) {
		Player player = Player.getInstance();
		PlayerView playerView = new PlayerView();
		player.addObserver(playerView);
		PlayerController pController = new PlayerController(player);
		GameFrame gameFrame = new GameFrame(playerView,pController);
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			player.updateEntity();
			gameFrame.repaint();
		}
	}
}
