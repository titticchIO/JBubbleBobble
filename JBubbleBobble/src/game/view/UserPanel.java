package game.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import game.model.Model;
import game.model.user.User;

public class UserPanel extends JPanel {

	public UserPanel(User user) {
		setOpaque(false);
		setLayout(new BorderLayout());
		JLabel avatarLabel = new JLabel();
		try {
			BufferedImage avatar = ImageIO.read(Paths.get(user.getAvatarPath()).toFile());
			ImageIcon avatarIcon = new ImageIcon(avatar.getScaledInstance(80, 80, Image.SCALE_SMOOTH)); // Scala
																										// l'avatar
			avatarLabel.setIcon(avatarIcon);
		} catch (IOException e) {
			e.printStackTrace();
			avatarLabel.setText("No Avatar");
		}

		JButton userButton = new JButton(user.getNickname());
		userButton.setBackground(Color.YELLOW);
		userButton.addActionListener(e -> {
			// Imposta l'utente corrente nel Model
			Model.getInstance().setCurrentUser(user);
		});

		userButton.setHorizontalTextPosition(JButton.CENTER);
		userButton.setVerticalTextPosition(JButton.BOTTOM);

		add(avatarLabel, BorderLayout.CENTER);
		add(userButton, BorderLayout.SOUTH);
		setSize(50, 50);
		setPreferredSize(new Dimension(80, 90));
	}

}
