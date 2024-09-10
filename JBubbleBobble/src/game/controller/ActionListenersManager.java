package game.controller;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;

import game.controller.gamestates.GameState;
import game.model.Model;
import game.model.bubbles.FireBubble;
import game.model.bubbles.SpecialBubble;
import game.model.bubbles.ThunderBubble;
import game.model.bubbles.WaterBubble;
import game.model.entities.Player;
import game.model.level.Level;
import game.model.user.User;
import game.view.View;
import game.view.frames.CheatFrame;
import game.view.frames.GameFrame;
import game.view.frames.GameFrame.Screen;
import game.view.panels.TransitionPanel;
import editor.model.LevelManager;
import editor.view.EditorFrame;

/**
 * {@code ActionListenersManager} contains all the game's ActionListeners,
 * obtainable through static methods
 */
public class ActionListenersManager {
	/**
	 * Sets the current user in the {@code Model}
	 * 
	 * @param user the user to be set
	 * @return the ActionListener to perform the action
	 */
	public static ActionListener setCurrentUser(User user) {
		return e -> {
			Model.getInstance().setCurrentUser(user);
			View.getInstance().getGameFrame().requestFocus();
		};

	}

	/**
	 * Opens the EditorFrame
	 * 
	 * @return the ActionListener to perform the action
	 */
	public static ActionListener startEditor() {
		return e -> {
			EditorFrame ef = EditorFrame.getInstance();
			new LevelManager();
			if (!ef.isVisible()) {
				ef.setVisible(true);
			} else {
				ef.toFront(); // Porta l'editor in primo piano
			}
		};
	}

	/**
	 * Opens the Leaderboard
	 * 
	 * @return the ActionListener to perform the action
	 */
	public static ActionListener showLeaderboard() {
		return e -> View.getInstance().getMenuPanel().showLeaderboard();
	}

	/**
	 * Opens the JPopupMenu to select the user
	 * 
	 * @param userSelectionPopUp  the JPopupMenu to select the user
	 * @param userSelectionButton
	 * @return the ActionListener to perform the action
	 */
	public static ActionListener showUserSelection(JPopupMenu userSelectionPopUp, JButton userSelectionButton) {
		return e -> userSelectionPopUp.show(userSelectionButton, 0, userSelectionButton.getHeight());
	}

	/**
	 * Updates the JPopupMenu to select the user
	 * 
	 * @param user               user to be set
	 * @param userSelectionPopUp the JPopupMenu to select the user
	 * @return the ActionListener to perform the action
	 */
	public static ActionListener updateUserSelection(User user, JPopupMenu userSelectionPopUp) {
		return e -> {
			Model.getInstance().setCurrentUser(user); // Aggiorna il Model con l'utente selezionato
			View.getInstance().getMenuPanel().updateCurrentUserPanel(); // Aggiorna il pannello utente corrente dopo
																		// aver cambiato l'utente nel Model
			userSelectionPopUp.setVisible(false);
		};
	}

	/**
	 * Shows the UI to create a new User
	 * 
	 * @return the ActionListener to perform the action
	 */
	public static ActionListener showNewUserDialog() {
		return e -> View.getInstance().getMenuPanel().showNewUserDialog();
	}

	/**
	 * Used to select the Avatar of a User
	 * 
	 * @param avatarPreviewLabel
	 * @param chooseAvatarButton
	 * @return the ActionListener to perform the action
	 */
	public static ActionListener chooseAvatar(JLabel avatarPreviewLabel, JButton chooseAvatarButton) {
		return e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Seleziona un Avatar");
			fileChooser.setFileFilter(
					new javax.swing.filechooser.FileNameExtensionFilter("Immagini", "png", "jpg", "jpeg"));
			int returnValue = fileChooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				String selectedAvatarPath = fileChooser.getSelectedFile().getPath();
				try {
					BufferedImage avatar = ImageIO.read(Paths.get(selectedAvatarPath).toFile());
					ImageIcon avatarIcon = new ImageIcon(avatar.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
					avatarPreviewLabel.setIcon(avatarIcon);
					avatarPreviewLabel.putClientProperty("avatarPath", selectedAvatarPath);
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(chooseAvatarButton, "Errore durante il caricamento dell'avatar!",
							"Errore", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
	}

	/**
	 * Sets the GameState to PLAYING
	 * 
	 * @param controller
	 * @return the ActionListener to perform the action
	 */
	public static ActionListener resumeGame(Controller controller) {
		return e -> {
			GameState.state = GameState.PLAYING;
			controller.getGameFrame().showState(Screen.GAME);
		};
	}

	/**
	 * Resets the game to go back to the menu
	 * 
	 * @param controller
	 * @return the ActionListener to perform the action
	 */
	public static ActionListener backToMenu(Controller controller) {
		return e -> {
			controller.stopGameLoop();
			controller.resetGame();
		};

	}

	public static ActionListener advanceTransition(TransitionPanel transitionPanel) {
		return new ActionListener() {

			private long startTime = -1;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (startTime == -1) {
					startTime = System.currentTimeMillis();
				}

				long elapsed = System.currentTimeMillis() - startTime;
				transitionPanel.setProgress(Math.min(1.0f, elapsed / (float) transitionPanel.TRANSITION_DURATION));

				if (transitionPanel.getProgress() >= 1.0f) {
					transitionPanel.getTransitionTimer().stop();
					if (GameState.state == GameState.PLAYING)
						View.getInstance().getGameFrame().showState(GameFrame.Screen.GAME);
					Model.getInstance().setToUpdate(true);
				}
				transitionPanel.repaint();
			}
		};
	}

	/**
	 * Opens the JFrame to activate the cheats
	 * 
	 * @return the ActionListener to perform the action
	 */
	public static ActionListener enableCheats() {
		return e -> {
			if (View.getInstance().getCheatFrame() == null) {
				View.getInstance().setCheatFrame(new CheatFrame());
			}
			View.getInstance().getCheatFrame().setVisible(true);
			View.getInstance().getGameFrame().requestFocus();
		};
	}

	/**
	 * Makes the {@code Player} invulnerable
	 * 
	 * @param button
	 * @return the ActionListener to perform the action
	 */
	public static ItemListener enableInvincibility(JToggleButton button) {
		return new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (GameState.state == GameState.PLAYING) {
					if (button.isSelected()) {
						Player.getInstance().setInvulnerable(true);
						if (Player.getInstance().getInvulnerabilityTimer() != null) {
							Player.getInstance().getInvulnerabilityTimer().cancel();
							Player.getInstance().setInvulnerabilityTimer(null);
						}
					} else {
						Player.getInstance().setInvulnerable(false);
					}
				}
				View.getInstance().getGameFrame().requestFocus();
			}
		};
	}

	/**
	 * Skips a level
	 * 
	 * @return the ActionListener to perform the action
	 */
	public static ActionListener skipLevel() {
		return e -> {
			if (GameState.state == GameState.PLAYING)
				try {
					Model.getInstance().nextLevel();
				} catch (Exception ex) {
					Model.getInstance().setWin();
				}
			View.getInstance().getGameFrame().requestFocus();
		};
	}

	/**
	 * Spawns a specified special {@code Bubble}
	 * 
	 * @param bubbleType indicates the type of bubble to be spawned
	 * @return the ActionListener to perform the action
	 */
	public static ActionListener spawnSpecialBubble(String bubbleType) {
		return e -> {
			if (GameState.state == GameState.PLAYING) {
				switch (bubbleType) {
				case "extend" -> Level.setSimultaneousKills(1);
				default ->
					Model.getInstance().getCurrentLevel().getBubbleManager().createSpecialBubble(switch (bubbleType) {
					case "water" -> new WaterBubble();
					case "fire" -> new FireBubble();
					case "thunder" -> new ThunderBubble();
					case "special" -> new SpecialBubble();
					default -> throw new IllegalArgumentException("Unexpected value: " + bubbleType);
					});
				}
			}
			View.getInstance().getGameFrame().requestFocus();
		};
	}

	/**
	 * Spawns a random {@code Powerup}
	 * 
	 * @return the ActionListener to perform the action
	 */
	public static ActionListener spawnRandomPowerup() {
		return e -> {
			if (GameState.state == GameState.PLAYING)
				Model.getInstance().getCurrentLevel().getPowerupManager().createRandomPowerup();
			View.getInstance().getGameFrame().requestFocus();
		};
	}
}
