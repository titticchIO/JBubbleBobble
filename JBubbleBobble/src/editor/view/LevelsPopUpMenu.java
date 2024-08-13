package editor.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;
import javax.swing.JOptionPane;

import editor.model.LevelManager;
import editor.model.LevelReader;
import game.model.level.LevelLoader;

public class LevelsPopUpMenu extends JPopupMenu {
    public enum MenuType {
        OPEN, SAVE, DELETE
    }

    public LevelsPopUpMenu(MenuType menuType, EditorFrame editorFrame) {
        // JPanel che conterrà i bottoni
        JPanel panel = new JPanel(new GridLayout(0, 1));

        // Carica i livelli e crea i bottoni in base al tipo di menu
        List<String> levels = LevelReader.getLevels();
        levels.forEach(level -> {
            JButton button = new JButton("Level " + level);
            button.addActionListener(e -> {
                int numero = Integer.parseInt(level);
                switch (menuType) {
                    case OPEN:
                        handleOpenLevel(numero, editorFrame);
                        break;
                    case SAVE:
                        handleSaveLevel(numero, editorFrame);
                        break;
                    case DELETE:
                        handleDeleteLevel(numero, editorFrame);
                        break;
                }
                setVisible(false); // Nasconde il popup dopo l'azione
            });
            panel.add(button);
        });

        // Imposta dimensioni e aggiunge il pannello con lo JScrollPane al menu
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(100, 200));
        add(scrollPane);
    }

    private void handleOpenLevel(int levelNumber, EditorFrame editorFrame) {
        try {
            String[][] levelData = LevelLoader.readLevelFile(levelNumber);
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
    }

    private void handleSaveLevel(int levelNumber, EditorFrame editorFrame) {
        LevelManager.saveLevelFile(levelNumber);
        editorFrame.setActualLevelNumber(String.valueOf(levelNumber));
        editorFrame.getActualLevel().setText("Level " + levelNumber);
    }

    private void handleDeleteLevel(int levelNumber, EditorFrame editorFrame) {
        LevelManager.deleteLevelFile(levelNumber);
        editorFrame.setActualLevelNumber("");
        editorFrame.getActualLevel().setText("");
    }
}
