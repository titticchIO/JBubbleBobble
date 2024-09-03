package game.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import game.controller.ActionListenersManager;
import game.model.Paths;
import game.model.bubbles.FireBubble;
import game.model.bubbles.SpecialBubble;
import game.model.bubbles.ThunderBubble;
import game.model.bubbles.WaterBubble;

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

				add(new JToggleButton() {
					{
						setIcon(new ImageIcon(ImageLoader.importImg("/player/invincible.png").getScaledInstance(64, 64,
								Image.SCALE_SMOOTH)));
						setFocusPainted(false);
						setBackground(Color.BLACK);
						
						// Add action listener to handle button toggle state
				        addItemListener(ActionListenersManager.enableInvincibility(this));
					}
				});
				add(new CheatButton(AnimationAndImagesLoader.getImage('@', "orange"),
						ActionListenersManager.skipLevel()));
				add(new CheatButton(AnimationAndImagesLoader.getImage('-'),
						ActionListenersManager.spawnSpecialBubble("fire")));
				add(new CheatButton(AnimationAndImagesLoader.getImage('+'),
						ActionListenersManager.spawnSpecialBubble("thunder")));
				add(new CheatButton(AnimationAndImagesLoader.getImage('/'),
						ActionListenersManager.spawnSpecialBubble("water")));
				add(new CheatButton(AnimationAndImagesLoader.getImage('%'),
						ActionListenersManager.spawnSpecialBubble("special")));
				add(new CheatButton(AnimationAndImagesLoader.getImage('('),
						ActionListenersManager.spawnSpecialBubble("extend")));
				add(new CheatButton(AnimationAndImagesLoader.getImage('!'),
						ActionListenersManager.spawnRandomPowerup()));

			}
		};
		add(buttonsPanel);
	}
}
