package editor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.Border;
import editor.controller.ActionListenersManager;
import game.view.AnimationAndImagesLoader;
import game.view.ImageLoader;
import static editor.view.EditorPanel.SCALE;
import static editor.view.EditorPanel.PANEL_HEIGHT;;

/**
 * A scroll pane that allows users to select various sprites, including blocks, enemies, players, and others.
 */
public class SpriteSelectionScrollPane extends JScrollPane {
	
	private static final long serialVersionUID = 1L;

    private List<SelectionButton> blocks;
    private List<SelectionButton> enemies;
    private List<SelectionButton> players;
    private List<SelectionButton> others;
    private SelectionButton selectedButton;

    /**
     * Constructs a new SpriteSelectionScrollPane, initializing the lists of selection buttons and setting up the UI.
     */
    public SpriteSelectionScrollPane() {
        blocks = new ArrayList<>();
        enemies = new ArrayList<>();
        players = new ArrayList<>();
        others = new ArrayList<>();

        addBlocks();
        addEnemies();
        addPlayers();
        SelectionButton eraser = new SelectionButton(ImageLoader.importImg("/editor/eraser.png"), ' ');
        others.add(eraser);
        setCurrentButton(blocks.get(0));

        setSize();
        setLayout(new ScrollPaneLayout());

        // Define grid size, considering buttons and labels
        int totalRows = blocks.size() + enemies.size() + players.size() + others.size() + 4; // Add 4 for labels

        JPanel buttonPanel = new JPanel(new GridLayout(totalRows, 1));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setSize(new Dimension((int) (100 * SCALE), (int) (totalRows * 40 * SCALE)));

        // Add category titles and buttons
        addCategory(buttonPanel, "Blocks", blocks);
        addCategory(buttonPanel, "Enemies", enemies);
        addCategory(buttonPanel, "Players", players);
        addCategory(buttonPanel, "Others", others);

        setViewportView(buttonPanel);
        System.out.println(getSize());
    }

    /**
     * Adds a category label and its buttons to the specified panel.
     *
     * @param panel The panel to which the category will be added.
     * @param title The title of the category.
     * @param buttons The list of buttons to be added.
     */
    private void addCategory(JPanel panel, String title, List<SelectionButton> buttons) {
        // Create a gray border
        Border grayBorder = BorderFactory.createLineBorder(Color.GRAY, 1);

        // Add title
        JLabel label = new JLabel(title);
        label.setForeground(Color.YELLOW); // Set text color to yellow
        label.setBorder(grayBorder); // Set gray border on the label
        label.setHorizontalAlignment(JLabel.CENTER); // Center-align text horizontally
        panel.add(label);

        // Add buttons
        for (SelectionButton b : buttons) {
            b.addActionListener(ActionListenersManager.addSelectionButton(this, b));
            panel.add(b);
        }
    }

    /**
     * Initializes the block buttons.
     */
    private void addBlocks() {
        for (int i = 1; i <= 9; i++) {
            char number = (char) (i + '0');
            blocks.add(new SelectionButton(AnimationAndImagesLoader.getImage(number), number));
        }
    }

    /**
     * Initializes the enemy buttons.
     */
    private void addEnemies() {
        char[] enemiesChars = {'Z', 'M', 'N', 'I', 'U', 'S', 'B'};
        for (char c : enemiesChars) {
            enemies.add(new SelectionButton(AnimationAndImagesLoader.getImage(c), c));
        }
    }

    /**
     * Initializes the player buttons.
     */
    private void addPlayers() {
        players.add(new SelectionButton(AnimationAndImagesLoader.getImage('P'), 'P'));
    }

    /**
     * Sets the preferred size of the scroll pane.
     */
    private void setSize() {
        Dimension size = new Dimension((int) (80 * SCALE), PANEL_HEIGHT);
        setPreferredSize(size);
        setSize(size);
    }

    /**
     * Deselects all buttons in all categories.
     */
    public void deselectAllButtons() {
        blocks.forEach(b -> b.setSelected(false));
        enemies.forEach(b -> b.setSelected(false));
        players.forEach(b -> b.setSelected(false));
        others.forEach(b -> b.setSelected(false));
    }

    /**
     * Returns the currently selected button.
     *
     * @return The currently selected button.
     */
    public SelectionButton getCurrentButton() {
        return selectedButton;
    }

    /**
     * Sets the currently selected button.
     *
     * @param selectedButton The button to be set as selected.
     */
    public void setCurrentButton(SelectionButton selectedButton) {
        this.selectedButton = selectedButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        blocks.forEach(b -> b.repaint());
        enemies.forEach(b -> b.repaint());
        players.forEach(b -> b.repaint());
        others.forEach(b -> b.repaint());
    }
}
