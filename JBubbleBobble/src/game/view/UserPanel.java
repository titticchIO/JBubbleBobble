package game.view;

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
import game.model.User;

public class UserPanel extends JPanel {
	
	private JButton userButton;

	public UserPanel(User user) {
		setOpaque(false);
		setLayout(new BorderLayout());
		JLabel avatarLabel = new JLabel();
		try {
			BufferedImage avatar = ImageIO.read(Paths.get(user.getAvatarPath()).toFile());
			ImageIcon avatarIcon = new ImageIcon(avatar.getScaledInstance(100, 100, Image.SCALE_SMOOTH)); // Scala
																										// l'avatar
			avatarLabel.setIcon(avatarIcon);
		} catch (IOException e) {
			e.printStackTrace();
			avatarLabel.setText("No Avatar");
		}
		
		userButton = new JButton(user.getNickname());
		userButton.setBackground(Color.YELLOW);
		userButton.setForeground(Color.MAGENTA);
		Font font=new Font("Arial", Font.BOLD, 12);
		userButton.setFont(font);
		userButton.addActionListener(ActionListenersManager.setCurrentUser(user));

		userButton.setHorizontalTextPosition(JButton.CENTER);
		userButton.setVerticalTextPosition(JButton.BOTTOM);

		add(avatarLabel, BorderLayout.CENTER);
		add(userButton, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(100, 120));
	}
	
	public JButton getUserButton() {
		return userButton;
	}
	
	

}
