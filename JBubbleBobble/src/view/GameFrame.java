package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame{
	private GamePanel gamePanel;
	
	public GameFrame() {
		this.gamePanel=new GamePanel();
		add(gamePanel);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
}
