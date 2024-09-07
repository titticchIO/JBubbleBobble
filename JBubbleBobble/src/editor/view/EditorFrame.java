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
import game.model.Model;
import game.view.ImageLoader;
import game.view.View;

/**
 * Singleton class representing the main editor frame in the level editor.
 * It contains panels and buttons to create and modify levels.
 */
public class EditorFrame extends JFrame {
	private static final long serialVersionUID = 1L;

    /** Singleton instance of the EditorFrame */
    public static EditorFrame instance;

    /** Panel that shows and allows editing of the level */
    private EditorPanel editorPanel;

    /** Button to save the current level */
    private JButton saveLevelButton;

    /** Label showing the current level number */
    private JLabel actualLevel;

    /** String storing the current level number */
    private String actualLevelNumber;

    /** List of popup menus for different level actions */
    private List<LevelsPopUpMenu> popUps;

    /**
     * Returns the singleton instance of EditorFrame.
     * 
     * @return the instance of EditorFrame
     */
    public static EditorFrame getInstance() {
        if (instance == null)
            instance = new EditorFrame();
        return instance;
    }

    /**
     * Private constructor for the singleton pattern.
     * Initializes the editor frame with necessary panels and buttons.
     */
    private EditorFrame() {
        setLayout(new BorderLayout());
        
        // Initialize fields inside the constructor
        SpriteSelectionScrollPane selectionPane = new SpriteSelectionScrollPane();
        editorPanel = new EditorPanel(this, selectionPane);
        popUps = new ArrayList<>();

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.BLACK);

        JButton newGridButton = new JButton(
            new ImageIcon(ImageLoader.importImg("/editor/new.png").getScaledInstance(70, 35, Image.SCALE_SMOOTH)));
        {
            newGridButton.setContentAreaFilled(false);
            newGridButton.setPreferredSize(new Dimension(70, 35));
            newGridButton.setBorderPainted(false);
            newGridButton.setFocusPainted(false);
            newGridButton.addActionListener(ActionListenersManager.newGridButton(editorPanel, selectionPane));
        }

        JButton openGridButton = new JButton(
            new ImageIcon(ImageLoader.importImg("/editor/open.png").getScaledInstance(70, 35, Image.SCALE_SMOOTH)));
        {
            openGridButton.setContentAreaFilled(false);
            openGridButton.setPreferredSize(new Dimension(70, 35));
            openGridButton.setBorderPainted(false);
            openGridButton.setFocusPainted(false);
        }

        JButton deleteLevelButton = new JButton(
            new ImageIcon(ImageLoader.importImg("/editor/delete.png").getScaledInstance(70, 35, Image.SCALE_SMOOTH)));
        {
            deleteLevelButton.setContentAreaFilled(false);
            deleteLevelButton.setPreferredSize(new Dimension(70, 35));
            deleteLevelButton.setBorderPainted(false);
            deleteLevelButton.setFocusPainted(false);
        }

        actualLevel = new JLabel(actualLevelNumber);
        actualLevel.setForeground(Color.YELLOW);


        saveLevelButton = new JButton(
            new ImageIcon(ImageLoader.importImg("/editor/save.png").getScaledInstance(70, 35, Image.SCALE_SMOOTH)));
        {
            saveLevelButton.setContentAreaFilled(false);
            saveLevelButton.setPreferredSize(new Dimension(70, 35));
            saveLevelButton.setBorderPainted(false);
            saveLevelButton.setFocusPainted(false);
        }

        LevelsPopUpMenu levelSelectionPopup = new LevelsPopUpMenu(MenuType.OPEN, this);
        LevelsPopUpMenu saveLevelPopup = new LevelsPopUpMenu(MenuType.SAVE, this);
        LevelsPopUpMenu deleteLevelPopup = new LevelsPopUpMenu(MenuType.DELETE, this);

        popUps.add(levelSelectionPopup);
        popUps.add(saveLevelPopup);
        popUps.add(deleteLevelPopup);

        topPanel.add(newGridButton);
        topPanel.add(openGridButton);
        topPanel.add(saveLevelButton);
        topPanel.add(deleteLevelButton);
        topPanel.add(actualLevel);

        openGridButton.addActionListener(ActionListenersManager.openGridButton(levelSelectionPopup, openGridButton));
        saveLevelButton.addActionListener(ActionListenersManager.saveLevelButton(saveLevelPopup, saveLevelButton));
        deleteLevelButton
            .addActionListener(ActionListenersManager.deleteLevelButton(deleteLevelPopup, deleteLevelButton));

        add(topPanel, BorderLayout.NORTH);
        add(editorPanel, BorderLayout.CENTER);
        add(selectionPane, BorderLayout.EAST);

        addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
                // Do nothing
            }

            @Override
            public void windowIconified(WindowEvent e) {
                // Do nothing
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                // Do nothing
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                // Do nothing
            }

            @Override
            public void windowClosing(WindowEvent e) {
            	resetEditor();
            	Model.getInstance().resetModel();
            	// Request focus back to the GameFrame
                View.getInstance().getGameFrame().requestFocus();
                // Dispose the EditorFrame
                dispose();
                
            }

            @Override
            public void windowClosed(WindowEvent e) {
                // Do nothing
            }

            @Override
            public void windowActivated(WindowEvent e) {
                // Do nothing
            }
        });
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Returns the editor panel.
     * 
     * @return the editor panel
     */
    public EditorPanel getEditorPanel() {
        return editorPanel;
    }

    /**
     * Sets the editor panel.
     * 
     * @param editorPanel the editor panel to set
     */
    public void setEditorPanel(EditorPanel editorPanel) {
        this.editorPanel = editorPanel;
    }

    /**
     * Returns the current level number as a string.
     * 
     * @return the current level number
     */
    public String getActualLevelNumber() {
        return actualLevelNumber;
    }

    /**
     * Sets the current level number.
     * 
     * @param actualLevelNumber the level number to set
     */
    public void setActualLevelNumber(String actualLevelNumber) {
        this.actualLevelNumber = actualLevelNumber;
    }

    /**
     * Returns the label showing the current level number.
     * 
     * @return the label showing the current level number
     */
    public JLabel getActualLevel() {
        return actualLevel;
    }

    /**
     * Sets the label showing the current level number.
     * 
     * @param actualLevel the label to set
     */
    public void setActualLevel(JLabel actualLevel) {
        this.actualLevel = actualLevel;
    }

    /**
     * Returns the list of popup menus.
     * 
     * @return the list of popup menus
     */
    public List<LevelsPopUpMenu> getPopUps() {
        return popUps;
    }
    
    public void resetEditor() {
    	editorPanel.reset();
    	actualLevelNumber="";
    	actualLevel.setText(actualLevelNumber);
    }
}
