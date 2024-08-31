package game.view;

import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class CheatFrame extends JFrame{

	public CheatFrame() {
		System.out.println(View.getInstance().getGameFrame().getY());
		setBounds(200, 106, 150, 641);
		setResizable(false);
		setLayout(new GridLayout(0,1));
	}
}
