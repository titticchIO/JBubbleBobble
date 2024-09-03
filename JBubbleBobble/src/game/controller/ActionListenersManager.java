package game.controller;

import java.awt.Image;
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
import game.controller.gamestates.Menu;
import game.model.Model;
import game.model.bubbles.Bubble;
import game.model.bubbles.ExtendBubble;
import game.model.bubbles.FireBubble;
import game.model.bubbles.SpecialBubble;
import game.model.bubbles.ThunderBubble;
import game.model.bubbles.WaterBubble;
import game.model.entities.Player;
import game.model.user.User;
import game.view.View;
import game.view.CheatFrame;
import game.view.GameFrame.Screen;
import editor.model.LevelManager;
import editor.view.EditorFrame;

public class ActionListenersManager {

	public static ActionListener setCurrentUser(User user) {
		return e -> {
			Model.getInstance().setCurrentUser(user);
			View.getInstance().getGameFrame().requestFocus();
		}; // Imposta l'utente corrente nel Model

	}

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

	public static ActionListener showLeaderboard() {
		return e -> View.getInstance().getMenuPanel().showLeaderboard();
	}

	public static ActionListener showUserSelection(JPopupMenu userSelectionPopUp, JButton userSelectionButton) {
		return e -> userSelectionPopUp.show(userSelectionButton, 0, userSelectionButton.getHeight());
	}

	public static ActionListener updateUserSelection(User user, JPopupMenu userSelectionPopUp) {
		return e -> {
			Model.getInstance().setCurrentUser(user); // Aggiorna il Model con l'utente selezionato
			View.getInstance().getMenuPanel().updateCurrentUserPanel(); // Aggiorna il pannello utente corrente dopo
																		// aver cambiato l'utente nel Model
			userSelectionPopUp.setVisible(false);
		};
	}

	public static ActionListener showNewUserDialog() {
		return e -> View.getInstance().getMenuPanel().showNewUserDialog();
	}

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

	public static ActionListener resumeGame(Game game) {
		return e -> {
			GameState.state = GameState.PLAYING;
			game.getGameFrame().showState(Screen.GAME);
		};
	}

	public static ActionListener backToMenu(Game game) {
		return e -> {
			game.resetGame();
		};

	}

	public static ActionListener enableCheats() {
		return e -> {
			if (View.getInstance().getCheatFrame() == null) {
				View.getInstance().setCheatFrame(new CheatFrame());
			}
			View.getInstance().getCheatFrame().setVisible(true);
			View.getInstance().getGameFrame().requestFocus();
		};
	}

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

	public static ActionListener spawnSpecialBubble(String bubbleType) {
		return e -> {
			if (GameState.state == GameState.PLAYING) {
				switch (bubbleType) {
				case "extend" -> Model.getInstance().getCurrentLevel().spawnBubble(new ExtendBubble());
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

	public static ActionListener spawnRandomPowerup() {
		return e -> {
			if (GameState.state == GameState.PLAYING)
				Model.getInstance().getCurrentLevel().getPowerupManager().createRandomPowerup();
			View.getInstance().getGameFrame().requestFocus();
		};
	}
}
