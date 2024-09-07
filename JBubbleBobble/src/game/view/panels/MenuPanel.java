package game.view.panels;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.*;

import game.controller.ActionListenersManager;
import game.model.Model;
import game.model.level.Level;
import game.model.user.User;
import game.model.user.UserMethods;
import game.view.ImageLoader;
import game.view.View;

/**
 * The {@code MenuPanel} class is responsible for rendering the main menu of the
 * game and handling user interactions such as selecting a user, starting the
 * editor, and viewing the leaderboard.
 */
public class MenuPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage menuImage;
	private UserPanel currentUserPanel;
	private JPopupMenu userSelectionPopUp;

	/**
	 * Constructor for the MenuPanel.
	 * 
	 * @param menu the Menu object that represents the game menu state
	 */
	public MenuPanel() {
		setSize(new Dimension((int) (Level.GAME_WIDTH * LevelPanel.SCALE),
				(int) (Level.GAME_HEIGHT * LevelPanel.SCALE)));
		loadImage(); // Load the image once
		initMenu();
	}

	/**
	 * Loads the background image for the menu.
	 */
	private void loadImage() {
		menuImage = ImageLoader.importImg("/menu/MenuScreen.png");
	}

	/**
	 * Initializes the menu components including buttons and pop-up menus.
	 */
	private void initMenu() {
		setLayout(null);

		JButton editorButton = new JButton();
		editorButton.setBounds(610, 20, 100, 50);
		editorButton.addActionListener(ActionListenersManager.startEditor());
		ImageIcon editorButtonImageIcon = new ImageIcon(
				ImageLoader.importImg("/menu/editor.png").getScaledInstance(100, 50, Image.SCALE_SMOOTH));
		editorButton.setIcon(editorButtonImageIcon);
		editorButton.setContentAreaFilled(false);
		editorButton.setBorderPainted(false);
		editorButton.setFocusPainted(false);

		JButton leaderboardButton = new JButton();
		leaderboardButton.setBounds(610, 70, 100, 50);
		leaderboardButton.addActionListener(ActionListenersManager.showLeaderboard());
		ImageIcon leaderboardButtonImageIcon = new ImageIcon(
				ImageLoader.importImg("/menu/leaderboard.png").getScaledInstance(100, 50, Image.SCALE_SMOOTH));
		leaderboardButton.setIcon(leaderboardButtonImageIcon);
		leaderboardButton.setContentAreaFilled(false);
		leaderboardButton.setBorderPainted(false);
		leaderboardButton.setFocusPainted(false);

		String lastUserNickname = UserMethods.getLastUser("resources/last_user.txt");
		User lastUser = Model.getInstance().getUserByNickname(lastUserNickname);
		Model.getInstance().setCurrentUser(lastUser);

		updateCurrentUserPanel();

		userSelectionPopUp = new JPopupMenu();
		updateUserSelectionPopUp();

		JButton userSelectionButton = new JButton();
		userSelectionButton.setBounds(10, 140, 100, 50);
		userSelectionButton
				.addActionListener(ActionListenersManager.showUserSelection(userSelectionPopUp, userSelectionButton));
		ImageIcon userSelectionButtonImageIcon = new ImageIcon(
				ImageLoader.importImg("/menu/userSelection.png").getScaledInstance(100, 50, Image.SCALE_SMOOTH));
		userSelectionButton.setIcon(userSelectionButtonImageIcon);
		userSelectionButton.setContentAreaFilled(false);
		userSelectionButton.setBorderPainted(false);
		userSelectionButton.setFocusPainted(false);

		JButton cheatButton = new JButton();
		cheatButton.setBounds(337, 450, 40, 37);
		cheatButton.setContentAreaFilled(false);
		cheatButton.setBorderPainted(false);
		cheatButton.setFocusPainted(false);
		cheatButton.addActionListener(ActionListenersManager.enableCheats());

		add(editorButton);
		add(leaderboardButton);
		add(userSelectionButton);
		add(cheatButton);
	}

	/**
	 * Updates the user selection pop-up menu with available users.
	 */
	private void updateUserSelectionPopUp() {
		userSelectionPopUp.removeAll();
		JPanel userPanelContainer = new JPanel();
		userPanelContainer.setLayout(new GridLayout(0, 1));
		Model.getInstance().getUsers().forEach(user -> {
			UserPanel userPanel = new UserPanel(user);
			userPanelContainer.add(userPanel);
			userPanel.getUserButton()
					.addActionListener(ActionListenersManager.updateUserSelection(user, userSelectionPopUp));
		});

		JButton newUserButton = new JButton();
		newUserButton.setPreferredSize(new Dimension(100, 20));
		newUserButton.setSize(new Dimension(100, 20));
		newUserButton.setIcon(new ImageIcon(
				ImageLoader.importImg("/menu/new_user.png").getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		setForeground(Color.MAGENTA);
		newUserButton.setFocusPainted(false);
		newUserButton.setContentAreaFilled(true);
		newUserButton.addActionListener(ActionListenersManager.showNewUserDialog());

		userPanelContainer.add(newUserButton);
		userSelectionPopUp.add(new JScrollPane(userPanelContainer));
		userSelectionPopUp.setPreferredSize(new Dimension(120, 300));
	}

	/**
	 * Updates the current user panel to reflect the selected user.
	 */
	public void updateCurrentUserPanel() {
		User user = Model.getInstance().getCurrentUser();
		if (currentUserPanel != null) {
			remove(currentUserPanel);
		}
		currentUserPanel = new UserPanel(user);
		currentUserPanel.setBounds(10, 20, 100, 120);
		currentUserPanel.getUserButton().setBorderPainted(false);
		currentUserPanel.getUserButton().setFocusPainted(false);

		add(currentUserPanel);
		revalidate();
		repaint();
	}

	/**
	 * Displays the leaderboard in a new window.
	 */
	public void showLeaderboard() {
		LeaderboardPanel leaderboardPanel = new LeaderboardPanel();
		JFrame leaderboardFrame = new JFrame("Leaderboard");
		leaderboardFrame.add(leaderboardPanel);
		Dimension preferredSize = leaderboardPanel.getPreferredSize();
		leaderboardFrame.setSize(preferredSize.width, preferredSize.height);
		leaderboardFrame.setLocationRelativeTo(null);

		leaderboardFrame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				View.getInstance().getGameFrame().requestFocus();
				leaderboardFrame.dispose();
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});

		leaderboardFrame.setVisible(true);
		leaderboardFrame.setResizable(false);
	}

	/**
	 * renders the menu image on the JPanel
	 * 
	 * @param g the {@code Graphics} object used for rendering.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (menuImage != null) {
			g.drawImage(menuImage, 0, 0, getWidth(), getHeight(), null);
		}
	}

	/**
	 * Displays a dialog for creating a new user.
	 */
	public void showNewUserDialog() {
		JFrame newUserFrame = new JFrame("Crea Nuovo Utente");
		newUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		newUserFrame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
				View.getInstance().getGameFrame().requestFocus();

			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
		newUserFrame.setSize(400, 400);
		newUserFrame.setResizable(false);
		newUserFrame.setLayout(null);
		newUserFrame.getContentPane().setBackground(Color.BLACK);

		JLabel nicknameLabel = new JLabel("Nickname:");
		nicknameLabel.setForeground(Color.YELLOW);
		nicknameLabel.setBounds(50, 30, 100, 30);
		newUserFrame.add(nicknameLabel);

		JTextField nicknameField = new JTextField(10);
		nicknameField.setBounds(150, 30, 200, 30);
		nicknameField.setBackground(Color.YELLOW);
		nicknameField.setForeground(Color.BLACK);
		newUserFrame.add(nicknameField);

		JLabel avatarPreviewLabel = new JLabel();
		avatarPreviewLabel.setBounds(145, 150, 100, 100);

		// Imposta avatar di default all'inizio
		String defaultAvatarPath = "resources/usersicons/default.png";
		BufferedImage defaultAvatar = ImageLoader.importImg("/usersicons/default.png");
		ImageIcon defaultAvatarIcon = new ImageIcon(defaultAvatar.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		avatarPreviewLabel.setIcon(defaultAvatarIcon);
		avatarPreviewLabel.putClientProperty("avatarPath", defaultAvatarPath);
		newUserFrame.add(avatarPreviewLabel);

		JButton chooseAvatarButton = new JButton(new ImageIcon(
				ImageLoader.importImg("/menu/avatar.png").getScaledInstance(150, 30, Image.SCALE_SMOOTH)));
		chooseAvatarButton
				.addActionListener(ActionListenersManager.chooseAvatar(avatarPreviewLabel, chooseAvatarButton));
		newUserFrame.add(chooseAvatarButton);
		chooseAvatarButton.setContentAreaFilled(false);
		chooseAvatarButton.setBounds(120, 90, 150, 30);
		chooseAvatarButton.setBorderPainted(false);
		chooseAvatarButton.setFocusPainted(false);

		JButton okButton = new JButton(
				new ImageIcon(ImageLoader.importImg("/menu/ok.png").getScaledInstance(80, 30, Image.SCALE_SMOOTH)));
		okButton.setContentAreaFilled(false);
		okButton.setBounds(100, 300, 80, 30);
		okButton.setBorderPainted(false);
		okButton.setFocusPainted(false);
		
		newUserFrame.add(okButton);

		JButton cancelButton = new JButton(new ImageIcon(
				ImageLoader.importImg("/menu/cancel.png").getScaledInstance(80, 30, Image.SCALE_SMOOTH)));
		cancelButton.setContentAreaFilled(false);
		cancelButton.setBounds(220, 300, 80, 30);
		cancelButton.addActionListener(e -> newUserFrame.dispose());
		cancelButton.setBorderPainted(false);
		cancelButton.setFocusPainted(false);
		
		newUserFrame.add(cancelButton);

		okButton.addActionListener(e -> {
			String nickname = nicknameField.getText();
			String selectedAvatarPath = (String) avatarPreviewLabel.getClientProperty("avatarPath");

			if (!nickname.isEmpty()) {
				if (selectedAvatarPath == null)
					selectedAvatarPath = defaultAvatarPath;
				String avatarPath = "resources/users/" + nickname + ".png";
				try {
					Files.copy(Paths.get(selectedAvatarPath), Paths.get(avatarPath));
				} catch (IOException ex) {
					System.err.println("Error copying files: " + ex.getMessage());
				}
				User newUser = new User(nickname, 0, avatarPath, 0, 0, 0);
				Model.getInstance().addUser(newUser);
				Model.getInstance().setCurrentUser(newUser);
				updateCurrentUserPanel();
				UserMethods.saveUsersData(nickname, 0, 0, 0, 0);

				updateUserSelectionPopUp();
				newUserFrame.dispose();
			} else {
				JOptionPane.showMessageDialog(newUserFrame, "The nickname is required!", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		newUserFrame.setLocationRelativeTo(null);
		newUserFrame.setVisible(true);
	}

}
