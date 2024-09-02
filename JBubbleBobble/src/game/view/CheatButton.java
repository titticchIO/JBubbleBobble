package game.view;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CheatButton extends JButton {

	public CheatButton(Image img, ActionListener actionListener) {
		setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
		setFocusPainted(false);
		setBackground(Color.BLACK);
		addActionListener(actionListener);
	}
}
