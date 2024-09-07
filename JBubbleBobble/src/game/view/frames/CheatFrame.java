package game.view.frames;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import game.controller.ActionListenersManager;
import game.view.AnimationAndImagesLoader;
import game.view.CheatButton;
import game.view.ImageLoader;

/**
 * The {@code CheatFrame} class represents a JFrame used in the game to provide
 * various cheat options. It extends {@code JFrame} and initializes a user
 * interface with buttons that enable different cheat functionalities.
 * 
 * <p>
 * This class sets up the window's size and layout, and adds buttons for
 * activating cheats such as invincibility, skipping levels, and spawning
 * special bubbles or power-ups.
 * </p>
 * 
 * <p>
 * Each button is configured with an icon and an associated action listener that
 * triggers specific cheat behaviors.
 * </p>
 * 
 * @see JFrame
 * @see JToggleButton
 * @see CheatButton
 * @see ActionListenersManager
 * @see ImageLoader
 * @see AnimationAndImagesLoader
 */
public class CheatFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a {@code CheatFrame} object and initializes the frame's
	 * properties. The frame is set to a fixed size and contains a set of buttons
	 * for cheat functionalities.
	 */
	public CheatFrame() {
		setBounds(200, 106, 64, 640);
		setResizable(false);
		addButtons();
	}

	/**
	 * Adds various buttons to the frame that allow the user to activate different
	 * cheats. The buttons include options for invincibility, skipping levels, and
	 * spawning special bubbles or power-ups. Each button is created with an icon
	 * and an action listener.
	 */
	private void addButtons() {
		JPanel buttonsPanel = new JPanel();

		buttonsPanel.setLayout(new GridLayout(0, 1));

		buttonsPanel.add(new JToggleButton() {

			private static final long serialVersionUID = 1L;

			{
				setIcon(new ImageIcon(
						ImageLoader.importImg("/player/invincible.png").getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
				setFocusPainted(false);
				setBackground(Color.BLACK);
				addItemListener(ActionListenersManager.enableInvincibility(this));
			}
		});
		buttonsPanel.add(
				new CheatButton(AnimationAndImagesLoader.getImage('@', "orange"), ActionListenersManager.skipLevel()));
		buttonsPanel.add(new CheatButton(AnimationAndImagesLoader.getImage('-'),
				ActionListenersManager.spawnSpecialBubble("fire")));
		buttonsPanel.add(new CheatButton(AnimationAndImagesLoader.getImage('+'),
				ActionListenersManager.spawnSpecialBubble("thunder")));
		buttonsPanel.add(new CheatButton(AnimationAndImagesLoader.getImage('/'),
				ActionListenersManager.spawnSpecialBubble("water")));
		buttonsPanel.add(new CheatButton(AnimationAndImagesLoader.getImage('%'),
				ActionListenersManager.spawnSpecialBubble("special")));
		buttonsPanel.add(new CheatButton(AnimationAndImagesLoader.getImage('('),
				ActionListenersManager.spawnSpecialBubble("extend")));
		buttonsPanel.add(
				new CheatButton(AnimationAndImagesLoader.getImage('!'), ActionListenersManager.spawnRandomPowerup()));

		buttonsPanel.add(buttonsPanel);
	}
}
