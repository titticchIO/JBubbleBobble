package editor.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;
import javax.swing.JOptionPane;

import editor.controller.ActionListenersManager;
import editor.model.LevelManager;
import editor.model.LevelReader;
import game.model.level.LevelLoader;

public class LevelsPopUpMenu extends JPopupMenu {

	public enum MenuType {
		OPEN, SAVE, DELETE
	}

	private MenuType menuType;
	private EditorFrame editorFrame;

	public LevelsPopUpMenu(MenuType menuType, EditorFrame editorFrame) {
		this.menuType = menuType;
		this.editorFrame = editorFrame;
		rebuildMenu();
	}

	private void rebuildMenu() {
		// Rimuovi tutti i componenti esistenti
		this.removeAll();

		// Aggiorna la lista dei livelli
		List<String> levels = LevelReader.getLevels();
		System.out.println(levels.size());

		// JPanel che conterrà i bottoni
		JPanel panel = new JPanel(new GridLayout(0, 1));

		// Carica i livelli e crea i bottoni in base al tipo di menu
		levels.forEach(level -> {
			JButton button = new JButton("Level " + level);
			button.setBackground(Color.BLACK);
			button.setForeground(Color.YELLOW);
			button.addActionListener(ActionListenersManager.levelHandlers(this, level, menuType, editorFrame));
			panel.add(button);
		});

		if (menuType == MenuType.SAVE) {
			int newLevelNumber = levels.size() + 1;
			JButton newLevelButton = new JButton("new Level (" + newLevelNumber + ")");
			newLevelButton.setBackground(Color.BLACK);
			newLevelButton.setForeground(Color.YELLOW);
			newLevelButton.addActionListener(ActionListenersManager.handleSaveLevel(newLevelNumber, editorFrame));
			panel.add(newLevelButton);
		}

		// Imposta dimensioni e aggiunge il pannello con lo JScrollPane al menu
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setPreferredSize(new Dimension(120, 200));
		add(scrollPane);

		// Aggiorna il popup
		this.revalidate();
		this.repaint();
	}

	public void handleOpenLevel(int levelNumber, EditorFrame editorFrame) {
		try {
			char[][] levelData = LevelLoader.readLevelFile(levelNumber);
			if (levelData != null) {
				editorFrame.getContentPane().remove(editorFrame.getEditorPanel());
				EditorPanel newEditorPanel = new EditorPanel(editorFrame, editorFrame.getSelectionPane());
				LevelManager.setLevel(levelData);
				newEditorPanel.loadLevel(levelData);
				editorFrame.setEditorPanel(newEditorPanel);
				editorFrame.add(newEditorPanel, BorderLayout.CENTER);
				editorFrame.setActualLevelNumber(String.valueOf(levelNumber));
				editorFrame.getActualLevel().setText("Level " + levelNumber);
				editorFrame.revalidate();
				editorFrame.repaint();
			} else {
				JOptionPane.showMessageDialog(this, "Error: level not found.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Please, enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		editorFrame.getPopUps().forEach(p -> p.rebuildMenu());

	}

	public void handleSaveLevel(int levelNumber, EditorFrame editorFrame) {
		// Salva il livello
		LevelManager.saveLevelFile(levelNumber);
		editorFrame.setActualLevelNumber(String.valueOf(levelNumber));
		editorFrame.getActualLevel().setText("Level " + levelNumber);
		saveLevelImage(levelNumber, editorFrame);

	}

	public void handleDeleteLevel(int levelNumber, EditorFrame editorFrame) {
		LevelManager.deleteLevelFile(levelNumber);
		editorFrame.setActualLevelNumber("");
		editorFrame.getActualLevel().setText("");
		editorFrame.getPopUps().forEach(p -> p.rebuildMenu());

	}

	private void saveLevelImage(int levelNumber, EditorFrame editorFrame) {
		try {
			BufferedImage image = new BufferedImage(editorFrame.getEditorPanel().getWidth(),
					editorFrame.getEditorPanel().getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics g = image.getGraphics();

			// Rimuovi il bordo grigio durante il rendering
			for (Sprite[] row : editorFrame.getEditorPanel().getSprites()) {
				for (Sprite s : row) {
					s.renderWithoutBorder(g); // Usa il metodo senza bordo
				}
			}

			g.dispose();

			// Specifica il percorso e il nome del file dell'immagine
			File outputFile = new File("resources/levelsimg/Livello" + levelNumber + ".png");
			ImageIO.write(image, "png", outputFile);

			System.out.println("Level image saved as: " + outputFile.getAbsolutePath());
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(editorFrame, "Error saving level image.", "Error", JOptionPane.ERROR_MESSAGE);
		}

		// Aggiorna i popup menu
		editorFrame.getPopUps().forEach(p -> p.rebuildMenu());
	}

}
