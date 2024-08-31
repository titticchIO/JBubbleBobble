package game.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CheatFrame extends JFrame {

	public CheatFrame() {
		System.out.println(View.getInstance().getGameFrame().getY());
		setBounds(200, 106, 64, 640);
		setResizable(false);
		addButtons();
	}

	private void addButtons() {
		JPanel buttonsPanel = new JPanel() {
			{
				setLayout(new GridLayout(0, 1));

				add(new CheatButton(
						AnimationAndImagesLoader.getImage('P').getScaledInstance(64, 64, Image.SCALE_SMOOTH), null));
				add(new CheatButton(
						AnimationAndImagesLoader.getImage('P').getScaledInstance(64, 64, Image.SCALE_SMOOTH), null));
				add(new CheatButton(
						AnimationAndImagesLoader.getImage('P').getScaledInstance(64, 64, Image.SCALE_SMOOTH), null));
				add(new CheatButton(
						AnimationAndImagesLoader.getImage('P').getScaledInstance(64, 64, Image.SCALE_SMOOTH), null));
				add(new CheatButton(
						AnimationAndImagesLoader.getImage('P').getScaledInstance(64, 64, Image.SCALE_SMOOTH), null));
				add(new CheatButton(
						AnimationAndImagesLoader.getImage('P').getScaledInstance(64, 64, Image.SCALE_SMOOTH), null));
				add(new CheatButton(
						AnimationAndImagesLoader.getImage('P').getScaledInstance(64, 64, Image.SCALE_SMOOTH), null));
				add(new CheatButton(
						AnimationAndImagesLoader.getImage('P').getScaledInstance(64, 64, Image.SCALE_SMOOTH), null));
				add(new CheatButton(
						AnimationAndImagesLoader.getImage('P').getScaledInstance(64, 64, Image.SCALE_SMOOTH), null));
				add(new CheatButton(
						AnimationAndImagesLoader.getImage('P').getScaledInstance(64, 64, Image.SCALE_SMOOTH), null));

			}
		};
		add(buttonsPanel);
	}
}
