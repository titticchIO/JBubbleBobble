package game.view;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
		menuImage = ImageLoader.importImg("/menu/MenuScreen.png");
	}

	private void initMenu() {
		setLayout(null);

		JButton editorButton = new JButton() {
			{
				setBounds(610, 20, 100, 50);
				addActionListener(ActionListenersManager.startEditor());
				ImageIcon editorButtonImageIcon = new ImageIcon(
						ImageLoader.importImg("/menu/editor.png").getScaledInstance(100, 50, Image.SCALE_SMOOTH));
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
						ImageLoader.importImg("/menu/leaderboard.png").getScaledInstance(100, 50, Image.SCALE_SMOOTH));
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
						ImageLoader.importImg("/menu/userSelection.png").getScaledInstance(100, 50, Image.SCALE_SMOOTH));
				setIcon(editorButtonImageIcon);
				setContentAreaFilled(false);
				setBorderPainted(false);
				setFocusPainted(false);
			}
		};

		JButton cheatButton=new JButton(".") {
			{
				setBounds(337, 450, 40, 37);
				setContentAreaFilled(false);
				setBorderPainted(false);
				setFocusPainted(false);
				addActionListener(ActionListenersManager.enableCheats());
			}
		};
		
		add(editorButton);
		add(leaderboardButton);
		add(userSelectionButton);
		add(cheatButton);
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
						ImageLoader.importImg("/menu/newUser.png").getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
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

		leaderboardFrame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// Riporta il focus sul GameFrame
				View.getInstance().getGameFrame().requestFocus();
				// Chiude il frame
				leaderboardFrame.dispose(); // Chiude il EditorFrame

			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

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
	    JFrame newUserFrame = new JFrame("Crea Nuovo Utente");
	    newUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
	    String defaultAvatarPath = "/usersicons/default.png";
	    BufferedImage defaultAvatar = ImageLoader.importImg(defaultAvatarPath);
	    ImageIcon defaultAvatarIcon = new ImageIcon(defaultAvatar.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
	    avatarPreviewLabel.setIcon(defaultAvatarIcon);
	    avatarPreviewLabel.putClientProperty("avatarPath", defaultAvatarPath);
	    newUserFrame.add(avatarPreviewLabel);

	    JButton chooseAvatarButton = new JButton(new ImageIcon(
	            ImageLoader.importImg("/menu/avatar.png").getScaledInstance(150, 30, Image.SCALE_SMOOTH))) {
	        {
	            setContentAreaFilled(false);
	            setBounds(120, 90, 150, 30);
	            setBorderPainted(false);
	            setFocusPainted(false);
	        }
	    };
	    chooseAvatarButton.addActionListener(
	        ActionListenersManager.chooseAvatar(avatarPreviewLabel, chooseAvatarButton)
	    );
	    newUserFrame.add(chooseAvatarButton);

	    JButton okButton = new JButton(new ImageIcon(
	            ImageLoader.importImg("/menu/ok.png").getScaledInstance(80, 30, Image.SCALE_SMOOTH))) {
	        {
	            setContentAreaFilled(false);
	            setBounds(100, 300, 80, 30);
	            setBorderPainted(false);
	            setFocusPainted(false);
	        }
	    };
	    newUserFrame.add(okButton);

	    JButton cancelButton = new JButton(new ImageIcon(
	            ImageLoader.importImg("/menu/cancel.png").getScaledInstance(80, 30, Image.SCALE_SMOOTH))) {
	        {
	            setContentAreaFilled(false);
	            setBounds(220, 300, 80, 30);
	            addActionListener(e -> newUserFrame.dispose());
	            setBorderPainted(false);
	            setFocusPainted(false);
	        }
	    };
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
	                System.err.println("Errore durante la copia del file: " + ex.getMessage());
	            }
	            User newUser = new User(nickname, 0, avatarPath, 0, 0, 0);
	            Model.getInstance().addUser(newUser);
	            Model.getInstance().setCurrentUser(newUser);
	            updateCurrentUserPanel();
	            UserMethods.saveUsersData(nickname, 0, 0, 0, 0);

	            updateUserSelectionPopUp();
	            newUserFrame.dispose();
	        } else {
	            JOptionPane.showMessageDialog(newUserFrame, "The nickname is required!", "Errore", JOptionPane.ERROR_MESSAGE);
	        }
	    });

	    newUserFrame.setLocationRelativeTo(null);
	    newUserFrame.setVisible(true);
	}


}
