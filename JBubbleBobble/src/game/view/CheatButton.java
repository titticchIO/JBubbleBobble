package game.view;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * A {@code CheatButton} is used to trigger or enable a cheat.
 */
public class CheatButton extends JButton {

	/**
	 * Constructor for the CheatButton
	 * 
	 * @param img            the image to be displayed from the button
	 * @param actionListener the action to be executed when the button is pressed
	 */
	public CheatButton(Image img, ActionListener actionListener) {
		// Sets the looks of the button
		setIcon(new ImageIcon(img.getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
		setBackground(Color.BLACK);
		setFocusPainted(false);

		addActionListener(actionListener);
	}
}
