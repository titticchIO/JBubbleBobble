package game.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import game.controller.gamestates.Menu;
import game.model.Model;
import game.model.level.Level;

public class MenuPanel extends JPanel {

	private Menu menu;
	private BufferedImage menuImage;

	public MenuPanel(Menu menu) {
		this.menu = menu;
		setSize(new Dimension((int) (Level.GAME_WIDTH * LevelPanel.SCALE),
				(int) (Level.GAME_HEIGHT * LevelPanel.SCALE)));

		loadImage(); // Load the image once
		initMenu();

	}

	// Load the image once to avoid reloading on every repaint
	private void loadImage() {
		menuImage = ImageLoader.importImg("/MenuScreen.png");
	}

	private void initMenu() {
		setLayout(null);

		// Create and configure Play button
		JButton playButton = new JButton("Play") {
			{
				setBounds(300, 400, 100, 50);
				addActionListener(e -> menu.startGame());
			}
		};

		// Create and configure Editor button
		JButton editorButton = new JButton() {
			{
				setBounds(19, 20, 100, 50);
				addActionListener(e -> editor.controller.Main.main(null));
				ImageIcon editorButtonImageIcon = new ImageIcon(
						ImageLoader.importImg("/editor.png").getScaledInstance(100, 50, Image.SCALE_SMOOTH));
				setIcon(editorButtonImageIcon);
				setContentAreaFilled(false); // Make the background transparent
				setBorderPainted(false); // Remove the button border
				setFocusPainted(false); // Remove the focus border

			}
		};
		// Create and configure Leaderboard button
		JButton leaderboardButton = new JButton() {
			{
				setBounds(19, 70, 100, 50);
				addActionListener(e -> showLeaderboard());
				ImageIcon editorButtonImageIcon = new ImageIcon(
						ImageLoader.importImg("/leaderboard.png").getScaledInstance(100, 50, Image.SCALE_SMOOTH));
				setIcon(editorButtonImageIcon);
				setContentAreaFilled(false); // Make the background transparent
				setBorderPainted(false); // Remove the button border
				setFocusPainted(false); // Remove the focus border
			}
		};

		JPopupMenu userSelectionPopUp = new JPopupMenu() {
			{
				add(new JScrollPane(new JPanel() {
					{
						setLayout(new GridLayout(0, 1));
						Model.getInstance().getUsers().forEach(user -> add(new UserPanel(user)));
						// Crea e configura il pulsante "New User"
						JButton newUserButton = new JButton() {
							{
								setPreferredSize(new Dimension(100, 20));
								setIcon(new ImageIcon(ImageLoader.importImg("/newUser.png")
										.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
								setBackground(Color.YELLOW); // Colore di sfondo giallo
								setForeground(Color.MAGENTA); // Colore del testo magenta
								setFocusPainted(false); // Rimuovi il bordo di focus
								setContentAreaFilled(true); // Assicurati che il background sia visibile
							}
						};

						newUserButton.addActionListener(e -> {
							// Logica per aggiungere un nuovo utente o aprire una finestra per farlo
							// Puoi aggiungere qui la logica per gestire la creazione di un nuovo utente.
							System.out.println("New User Button Clicked!");
						});

						// Aggiungi il pulsante al pannello
						add(newUserButton);
					}
				}));

				setPreferredSize(new Dimension(120, 300));
			}
		};
		JButton userSelectionButton = new JButton() {
			{
				setBounds(19, 120, 100, 50);
				addActionListener(e -> userSelectionPopUp.show(this, 0, this.getHeight()));
				ImageIcon editorButtonImageIcon = new ImageIcon(
						ImageLoader.importImg("/userSelection.png").getScaledInstance(100, 50, Image.SCALE_SMOOTH));
				setIcon(editorButtonImageIcon);
				setContentAreaFilled(false); // Make the background transparent
				setBorderPainted(false); // Remove the button border
				setFocusPainted(false); // Remove the focus border
			}
		};

		// Add buttons to panel
		add(playButton);
		add(editorButton);
		add(leaderboardButton);
		add(userSelectionButton);
	}

	// Display leaderboard
	private void showLeaderboard() {
		JFrame leaderboardFrame = new JFrame("Leaderboard");
		LeaderboardPanel leaderboardPanel = new LeaderboardPanel();
		leaderboardFrame.add(leaderboardPanel);
		leaderboardFrame.setSize(450, 300);
		leaderboardFrame.setLocationRelativeTo(null); // Center the window
		leaderboardFrame.setVisible(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Draw the preloaded image, preventing repetitive loading
		if (menuImage != null) {
			g.drawImage(menuImage, 0, 0, getWidth(), getHeight(), null);
		}
	}
}
