package game.view;

import java.awt.Image;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

public class CheatButton extends JToggleButton {

	public CheatButton(Image img, ActionListener actionListener) {
		setIcon(new ImageIcon(img));
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusPainted(false);
		addActionListener(actionListener);
	}
}
