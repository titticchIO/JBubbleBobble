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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;

import editor.controller.ActionListenersManager;
import editor.model.LevelManager;
import editor.model.LevelReader;
import game.model.level.LevelLoader;

/**
 * A popup menu for managing levels in the editor.
 */
public class LevelsPopUpMenu extends JPopupMenu {

    public enum MenuType {
        OPEN, SAVE, DELETE
    }

    public static final long serialVersionUID = 1L;

    private MenuType menuType;
    private EditorFrame editorFrame;

    /**
     * Constructs a LevelsPopUpMenu with the specified menu type and editor frame.
     *
     * @param menuType the type of menu
     * @param editorFrame the editor frame
     */
    public LevelsPopUpMenu(MenuType menuType, EditorFrame editorFrame) {
        this.menuType = menuType;
        this.editorFrame = editorFrame;
        rebuildMenu();
    }

    /**
     * Rebuilds the menu by adding buttons for each level.
     */
    private void rebuildMenu() {
        // Remove all existing components
        removeAll();

        // Update the list of levels
        List<String> levels = LevelReader.getLevels();
        System.out.println(levels.size());

        // JPanel to hold the buttons
        JPanel panel = new JPanel(new GridLayout(0, 1));

        // Load levels and create buttons based on the menu type
        levels.forEach(level -> {
            JButton button = new JButton("Level " + level);
            button.setBackground(Color.BLACK);
            button.setForeground(Color.YELLOW);
            button.addActionListener(ActionListenersManager.levelHandlers(this, level, menuType, editorFrame));
            panel.add(button);
        });

        if (menuType == MenuType.SAVE) {
            int newLevelNumber = levels.size() + 1;
            JButton newLevelButton = new JButton("New Level (" + newLevelNumber + ")");
            newLevelButton.setBackground(Color.BLACK);
            newLevelButton.setForeground(Color.YELLOW);
            newLevelButton.addActionListener(ActionListenersManager.handleSaveLevel(this, newLevelNumber, editorFrame));
            panel.add(newLevelButton);
        }

        // Set dimensions and add the panel with JScrollPane to the menu
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(120, 200));
        add(scrollPane);

        // Update the popup
        revalidate();
        repaint();
    }

    /**
     * Handles opening a level by loading its data and updating the editor frame.
     *
     * @param levelNumber the level number
     * @param editorFrame the editor frame
     */
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

    /**
     * Handles saving a level and updates the editor frame accordingly.
     *
     * @param levelNumber the level number
     * @param editorFrame the editor frame
     */
    public void handleSaveLevel(int levelNumber, EditorFrame editorFrame) {
        // Save the level
        LevelManager.saveLevelFile(levelNumber);
        editorFrame.setActualLevelNumber(String.valueOf(levelNumber));
        editorFrame.getActualLevel().setText("Level " + levelNumber);
        saveLevelImage(levelNumber, editorFrame);
    }

    /**
     * Handles deleting a level and updates the editor frame accordingly.
     *
     * @param levelNumber the level number
     * @param editorFrame the editor frame
     */
    public void handleDeleteLevel(int levelNumber, EditorFrame editorFrame) {
        LevelManager.deleteLevelFile(levelNumber);
        editorFrame.setActualLevelNumber("");
        editorFrame.getActualLevel().setText("");
        editorFrame.getPopUps().forEach(p -> p.rebuildMenu());
    }

    /**
     * Saves the current level as an image file.
     *
     * @param levelNumber the level number
     * @param editorFrame the editor frame
     */
    private void saveLevelImage(int levelNumber, EditorFrame editorFrame) {
        try {
            BufferedImage image = new BufferedImage(editorFrame.getEditorPanel().getWidth(),
                    editorFrame.getEditorPanel().getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();

            // Remove the gray border during rendering
            for (Sprite[] row : editorFrame.getEditorPanel().getSprites()) {
                for (Sprite s : row) {
                    s.renderWithoutBorder(g); // Use borderless rendering method
                }
            }

            g.dispose();

            // Specify the path and filename for the image
            File outputFile = new File("resources/levelsimg/Level" + levelNumber + ".png");
            ImageIO.write(image, "png", outputFile);

            System.out.println("Level image saved as: " + outputFile.getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(editorFrame, "Error saving level image.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Update popup menus
        editorFrame.getPopUps().forEach(p -> p.rebuildMenu());
    }
}
