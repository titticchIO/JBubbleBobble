package editor.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import editor.controller.ActionListenersManager;
import editor.view.LevelsPopUpMenu.MenuType;
import game.view.ImageLoader;
import game.view.View;

/**
 * Singleton class that represents the main frame of the level editor.
 * It manages the layout and interaction with the editor components.
 */
public class EditorFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
    // Static fields
    public static EditorFrame instance;

    // Non-static fields (grouped by type)
    private EditorPanel editorPanel;
    private SpriteSelectionScrollPane selectionPane;
    private JButton saveLevelButton;
    private JLabel actualLevel;
    private String actualLevelNumber;
    private List<LevelsPopUpMenu> popUps;

    // Static methods
    /**
     * Returns the singleton instance of EditorFrame.
     * Creates a new instance if it doesn't exist.
     *
     * @return the instance of EditorFrame
     */
    public static EditorFrame getInstance() {
        if (instance == null)
            instance = new EditorFrame();
        return instance;
    }

    // Constructor
    /**
     * Private constructor to create and initialize the editor frame.
     * Sets up the layout, components, and window listeners.
     */
    private EditorFrame() {
        setLayout(new BorderLayout());
        selectionPane = new SpriteSelectionScrollPane();
        editorPanel = new EditorPanel(this, selectionPane);
        popUps = new ArrayList<>();

        // Create top panel with flow layout
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.BLACK);

        // New grid button
        JButton newGridButton = new JButton(
                new ImageIcon(ImageLoader.importImg("/editor/new.png").getScaledInstance(70, 35, Image.SCALE_SMOOTH))); 
        newGridButton.setContentAreaFilled(false);
        newGridButton.setPreferredSize(new Dimension(70, 35));
        newGridButton.setBorderPainted(false);
        newGridButton.setFocusPainted(false);

        // Open grid button
        JButton openGridButton = new JButton(
                new ImageIcon(ImageLoader.importImg("/editor/open.png").getScaledInstance(70, 35, Image.SCALE_SMOOTH)));
        openGridButton.setContentAreaFilled(false);
        openGridButton.setPreferredSize(new Dimension(70, 35));
        openGridButton.setBorderPainted(false);
        openGridButton.setFocusPainted(false);

        // Delete level button
        JButton deleteLevelButton = new JButton(
                new ImageIcon(ImageLoader.importImg("/editor/delete.png").getScaledInstance(70, 35, Image.SCALE_SMOOTH)));
        deleteLevelButton.setContentAreaFilled(false);
        deleteLevelButton.setPreferredSize(new Dimension(70, 35));
        deleteLevelButton.setBorderPainted(false);
        deleteLevelButton.setFocusPainted(false);

        // Initialize JLabel for actual level
        actualLevel = new JLabel(actualLevelNumber);
        actualLevel.setForeground(Color.YELLOW);

        // ActionListeners for buttons
        newGridButton.addActionListener(ActionListenersManager.newGridButton(editorPanel, selectionPane));
        openGridButton.addActionListener(ActionListenersManager.openGridButton(
                new LevelsPopUpMenu(MenuType.OPEN, this), openGridButton));
        
        saveLevelButton = new JButton(
                new ImageIcon(ImageLoader.importImg("/editor/save.png").getScaledInstance(70, 35, Image.SCALE_SMOOTH)));
        saveLevelButton.setContentAreaFilled(false);
        saveLevelButton.setPreferredSize(new Dimension(70, 35));
        saveLevelButton.setBorderPainted(false);
        saveLevelButton.setFocusPainted(false);
        saveLevelButton.addActionListener(ActionListenersManager.saveLevelButton(
                new LevelsPopUpMenu(MenuType.SAVE, this), saveLevelButton));
        deleteLevelButton.addActionListener(ActionListenersManager.deleteLevelButton(
                new LevelsPopUpMenu(MenuType.DELETE, this), deleteLevelButton));

        // Add buttons to top panel
        topPanel.add(newGridButton);
        topPanel.add(openGridButton);
        topPanel.add(saveLevelButton);
        topPanel.add(deleteLevelButton);
        topPanel.add(actualLevel);

        // Add components to frame
        add(topPanel, BorderLayout.NORTH);
        add(editorPanel, BorderLayout.CENTER);
        add(selectionPane, BorderLayout.EAST);

        // Window listener for frame
        addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Refocus the game frame and close the editor frame
                View.getInstance().getGameFrame().requestFocus();
                dispose();
            }

            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
        });

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    // Getters
    /**
     * Returns the editor panel.
     *
     * @return the editor panel
     */
    public EditorPanel getEditorPanel() {
        return editorPanel;
    }

    /**
     * Returns the actual level number as a String.
     *
     * @return the actual level number
     */
    public String getActualLevelNumber() {
        return actualLevelNumber;
    }

    /**
     * Returns the JLabel that displays the current level.
     *
     * @return the JLabel for the actual level
     */
    public JLabel getActualLevel() {
        return actualLevel;
    }

    /**
     * Returns the sprite selection pane.
     *
     * @return the sprite selection pane
     */
    public SpriteSelectionScrollPane getSelectionPane() {
        return selectionPane;
    }

    /**
     * Returns the list of LevelsPopUpMenu instances.
     *
     * @return the list of LevelsPopUpMenu
     */
    public List<LevelsPopUpMenu> getPopUps() {
        return popUps;
    }

    // Setters
    /**
     * Sets the editor panel.
     *
     * @param editorPanel the new editor panel
     */
    public void setEditorPanel(EditorPanel editorPanel) {
        this.editorPanel = editorPanel;
    }

    /**
     * Sets the actual level number.
     *
     * @param actualLevelNumber the new actual level number
     */
    public void setActualLevelNumber(String actualLevelNumber) {
        this.actualLevelNumber = actualLevelNumber;
    }

    /**
     * Sets the JLabel that displays the current level.
     *
     * @param actualLevel the new JLabel for the actual level
     */
    public void setActualLevel(JLabel actualLevel) {
        this.actualLevel = actualLevel;
    }
}
