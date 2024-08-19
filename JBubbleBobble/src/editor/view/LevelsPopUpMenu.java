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
            });
            panel.add(button);
        });

        if (menuType == MenuType.SAVE) {
            int newLevelNumber = levels.size() + 1;
            JButton newLevelButton = new JButton("new Level (" + newLevelNumber + ")");
            newLevelButton.addActionListener(e -> {
                handleSaveLevel(newLevelNumber, editorFrame);
            });
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
        rebuildMenu(); // Ricostruisci il menu dopo ogni azione
    }

    private void handleSaveLevel(int levelNumber, EditorFrame editorFrame) {
        LevelManager.saveLevelFile(levelNumber);
        editorFrame.setActualLevelNumber(String.valueOf(levelNumber));
        editorFrame.getActualLevel().setText("Level " + levelNumber);
        rebuildMenu(); // Ricostruisci il menu dopo ogni azione
    }

    private void handleDeleteLevel(int levelNumber, EditorFrame editorFrame) {
        LevelManager.deleteLevelFile(levelNumber);
        editorFrame.setActualLevelNumber("");
        editorFrame.getActualLevel().setText("");
        rebuildMenu(); // Ricostruisci il menu dopo ogni azione
    }
}
