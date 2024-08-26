package game.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.*;

import game.controller.ActionListenersManager;
import game.controller.gamestates.Menu;
import game.model.Model;
import game.model.level.Level;
import game.model.user.User;
import game.model.user.UserMethods;

public class MenuPanel extends JPanel {

	private Menu menu;
	private BufferedImage menuImage;
	private UserPanel currentUserPanel;
	private JPopupMenu userSelectionPopUp;

	public MenuPanel(Menu menu) {
		this.menu = menu;
		setSize(new Dimension((int) (Level.GAME_WIDTH * LevelPanel.SCALE),
				(int) (Level.GAME_HEIGHT * LevelPanel.SCALE)));
		loadImage(); // Load the image once
		initMenu();
	}

	private void loadImage() {
		menuImage = ImageLoader.importImg("/MenuScreen.png");
	}

	private void initMenu() {
		setLayout(null);

		JButton playButton = new JButton("Play") {
			{
				setBounds(300, 400, 100, 50);
				addActionListener(ActionListenersManager.startGame(menu));
			}
		};

		JButton editorButton = new JButton() {
			{
				setBounds(610, 20, 100, 50);
				addActionListener(ActionListenersManager.startEditor());
				ImageIcon editorButtonImageIcon = new ImageIcon(
						ImageLoader.importImg("/editor.png").getScaledInstance(100, 50, Image.SCALE_SMOOTH));
				setIcon(editorButtonImageIcon);
				setContentAreaFilled(false);
				setBorderPainted(false);
				setFocusPainted(false);
			}
		};

		JButton leaderboardButton = new JButton() {
			{
				setBounds(610, 70, 100, 50);
				addActionListener(ActionListenersManager.showLeaderboard());
				ImageIcon editorButtonImageIcon = new ImageIcon(
						ImageLoader.importImg("/leaderboard.png").getScaledInstance(100, 50, Image.SCALE_SMOOTH));
				setIcon(editorButtonImageIcon);
				setContentAreaFilled(false);
				setBorderPainted(false);
				setFocusPainted(false);
			}
		};

		String lastUserNickname = UserMethods.getLastUser("resources/last_user.txt");
		User lastUser = Model.getInstance().getUserByNickname(lastUserNickname);
		Model.getInstance().setCurrentUser(lastUser);

		updateCurrentUserPanel();

		userSelectionPopUp = new JPopupMenu();
		updateUserSelectionPopUp();

		JButton userSelectionButton = new JButton() {
			{
				setBounds(10, 140, 100, 50);
				addActionListener(ActionListenersManager.showUserSelection(userSelectionPopUp, this));
				ImageIcon editorButtonImageIcon = new ImageIcon(
						ImageLoader.importImg("/userSelection.png").getScaledInstance(100, 50, Image.SCALE_SMOOTH));
				setIcon(editorButtonImageIcon);
				setContentAreaFilled(false);
				setBorderPainted(false);
				setFocusPainted(false);
			}
		};

		add(playButton);
		add(editorButton);
		add(leaderboardButton);
		add(userSelectionButton);
	}

	private void updateUserSelectionPopUp() {
		userSelectionPopUp.removeAll(); // Rimuovi gli elementi esistenti
		JPanel userPanelContainer = new JPanel();
		userPanelContainer.setLayout(new GridLayout(0, 1));
		Model.getInstance().getUsers().forEach(user -> {
			UserPanel userPanel = new UserPanel(user);
			userPanelContainer.add(userPanel);
			userPanel.getUserButton()
					.addActionListener(ActionListenersManager.updateUserSelection(user, userSelectionPopUp));
		});

		JButton newUserButton = new JButton() {
			{
				setPreferredSize(new Dimension(100, 20));
				setIcon(new ImageIcon(
						ImageLoader.importImg("/newUser.png").getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
				setBackground(Color.YELLOW);
				setForeground(Color.MAGENTA);
				setFocusPainted(false);
				setContentAreaFilled(true);
				addActionListener(ActionListenersManager.showNewUserDialog());
			}
		};

		userPanelContainer.add(newUserButton);
		userSelectionPopUp.add(new JScrollPane(userPanelContainer));
		userSelectionPopUp.setPreferredSize(new Dimension(120, 300));
	}

	public void updateCurrentUserPanel() {
		User user = Model.getInstance().getCurrentUser();
		if (currentUserPanel != null) {
			remove(currentUserPanel);
		}
		currentUserPanel = new UserPanel(user) {
			{
				setBounds(10, 20, 100, 120);
				getUserButton().setBorderPainted(false); // Remove the button border
				getUserButton().setFocusPainted(false); // Remove the focus border
			}
		};
		add(currentUserPanel);
		revalidate();
		repaint();
	}

	public void showLeaderboard() {
		LeaderboardPanel leaderboardPanel = new LeaderboardPanel();
		// leaderboardPanel.setBackground(Color.BLACK);

		// Creazione del frame
		JFrame leaderboardFrame = new JFrame("Leaderboard");
		// leaderboardFrame.setBackground(Color.BLACK);

		// Aggiungi il pannello al frame
		leaderboardFrame.add(leaderboardPanel);

		// Ottieni le dimensioni preferite del pannello leaderboard (che include il
		// JScrollPane)
		Dimension preferredSize = leaderboardPanel.getPreferredSize();

		// Imposta la dimensione del frame in base alla dimensione preferita del
		// pannello leaderboard
		leaderboardFrame.setSize(preferredSize.width, preferredSize.height); // Aggiungi un margine

		// Centra il frame rispetto alla finestra principale
		leaderboardFrame.setLocationRelativeTo(null);

		// Imposta l'operazione di chiusura del frame
		leaderboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Mostra il frame
		leaderboardFrame.setVisible(true);

		// Impedisce il ridimensionamento del frame dopo averlo mostrato
		leaderboardFrame.setResizable(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (menuImage != null) {
			g.drawImage(menuImage, 0, 0, getWidth(), getHeight(), null);
		}
	}

	public void showNewUserDialog() {
		JTextField nicknameField = new JTextField(10);
		JButton chooseAvatarButton = new JButton("Scegli Avatar");
		JLabel avatarPreviewLabel = new JLabel();
		String defaultAvatarPath = "resources/usersicons/default.png";

		// Selezione dell'avatar
		chooseAvatarButton.addActionListener(ActionListenersManager.chooseAvatar(avatarPreviewLabel, chooseAvatarButton));

		JPanel dialogPanel = new JPanel(new GridLayout(0, 5));
		dialogPanel.add(new JLabel("Nickname:"));
		dialogPanel.add(nicknameField);
		dialogPanel.add(chooseAvatarButton);
		dialogPanel.add(avatarPreviewLabel);

		int result = JOptionPane.showConfirmDialog(null, dialogPanel, "Crea Nuovo Utente",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			String nickname = nicknameField.getText();
			String selectedAvatarPath = (String) avatarPreviewLabel.getClientProperty("avatarPath");

			if (!nickname.isEmpty()) {
				if (selectedAvatarPath == null)
					selectedAvatarPath = defaultAvatarPath;
				String avatarPath = "resources/users/" + nickname + ".png";
				try {
					Files.copy(Paths.get(selectedAvatarPath), Paths.get(avatarPath));
				} catch (IOException e) {
					System.err.println("Errore durante la copia del file: " + e.getMessage());
				}
				User newUser = new User(nickname, 0, avatarPath, 0, 0, 0);
				Model.getInstance().addUser(newUser);
				Model.getInstance().setCurrentUser(newUser);
				updateCurrentUserPanel();
				UserMethods.saveUsersData(nickname, 0, 0, 0, 0);

				// Aggiorna il pop-up dopo aver aggiunto un nuovo utente
				updateUserSelectionPopUp();
			} else {
				JOptionPane.showMessageDialog(this, "Il nickname è obbligatorio!", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
