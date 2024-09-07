package game.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.controller.ActionListenersManager;
import game.model.user.User;

/**
 * The {@code UserPanel} class represents a panel that displays a user's avatar
 * and nickname. It extends {@link JPanel} and is used in the game's user
 * interface. The panel contains a button with the user's nickname and an image
 * representing the user's avatar.
 * 
 * <p>
 * This panel has the following layout:
 * <ul>
 * <li>An avatar image displayed in the center of the panel.</li>
 * <li>A button with the user's nickname displayed at the bottom.</li>
 * </ul>
 * 
 * <p>
 * The button is associated with an action listener that triggers actions for
 * the currently selected user. If the avatar image cannot be loaded, a default
 * "No Avatar" text will be displayed instead.
 * 
 * <p>
 * Usage Example:
 * 
 * <pre>
 * UserPanel userPanel = new UserPanel(user);
 * someContainer.add(userPanel);
 * </pre>
 * 
 * @see User
 * @see ActionListenersManager
 */
public class UserPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton userButton;

	/**
	 * Constructs a UserPanel object, displaying the user's avatar and nickname.
	 * 
	 * @param user The User object containing information such as the avatar path
	 *             and nickname.
	 */
	public UserPanel(User user) {
		setOpaque(false);
		setLayout(new BorderLayout());
		JLabel avatarLabel = new JLabel();
		try {
			BufferedImage avatar = ImageIO.read(Paths.get(user.getAvatarPath()).toFile());
			ImageIcon avatarIcon = new ImageIcon(avatar.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
			avatarLabel.setIcon(avatarIcon);
		} catch (IOException e) {
			e.printStackTrace();
			avatarLabel.setText("No Avatar");
		}

		userButton = new JButton(user.getNickname());
		userButton.setBackground(Color.YELLOW);
		userButton.setForeground(Color.MAGENTA);
		Font font = new Font("Arial", Font.BOLD, 12);
		userButton.setFont(font);
		userButton.addActionListener(ActionListenersManager.setCurrentUser(user));

		userButton.setHorizontalTextPosition(JButton.CENTER);
		userButton.setVerticalTextPosition(JButton.BOTTOM);

		add(avatarLabel, BorderLayout.CENTER);
		add(userButton, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(100, 120));
	}

	/**
	 * Returns the JButton representing the user button.
	 * 
	 * @return The JButton that displays the user's nickname.
	 */
	public JButton getUserButton() {
		return userButton;
	}

}
