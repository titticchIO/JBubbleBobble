package editor.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

import utils.ImageLoader;
import utils.ImagesTest;

import static editor.view.EditorPanel.PANEL_HEIGHT;
import static editor.view.EditorPanel.SCALE;

public class SpriteSelectionScrollPane extends JScrollPane {
    private List<SelectionButton> blocks;
    private List<SelectionButton> enemies;
    private List<SelectionButton> players;
    private List<SelectionButton> others;

    private SelectionButton selectedButton;

    public SpriteSelectionScrollPane() {
        blocks = new ArrayList<>();
        enemies = new ArrayList<>();
        players = new ArrayList<>();
        others = new ArrayList<>();

        addBlocks();
        addEnemies();
        addPlayers();
        // Aggiunta di un bottone vuoto
        others.add(new SelectionButton(ImageLoader.importImg("/EmptyTile.png"), " "));

        setSize();
        setLayout(new ScrollPaneLayout());

        // Definisci la dimensione della griglia, contando i bottoni e le etichette
        int totalRows = blocks.size() + enemies.size() + players.size() + others.size() + 4; // Aggiungi 4 per le etichette

        JPanel buttonPanel = new JPanel(new GridLayout(totalRows, 1));
        buttonPanel.setSize(new Dimension((int) (100 * SCALE), (int) (totalRows * 40 * SCALE)));

        // Aggiungi i titoli e i bottoni per ogni categoria
        addCategory(buttonPanel, "Blocchi", blocks);
        addCategory(buttonPanel, "Nemici", enemies);
        addCategory(buttonPanel, "Player", players);
        addCategory(buttonPanel, "Altro", others);

        setViewportView(buttonPanel);
    }

    private void addCategory(JPanel panel, String title, List<SelectionButton> buttons) {
        // Aggiungi il titolo
        JLabel label = new JLabel(title);
        panel.add(label);

        // Aggiungi i bottoni corrispondenti
        for (SelectionButton b : buttons) {
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deselectAllButtons();
                    b.setSelected(true);
                    setCurrentButton(b);
                }
            });
            panel.add(b);
        }
    }

    private void addBlocks() {
        String type = "#";
        for (int i = 1; i <= 4; i++) {
            String number = String.valueOf(i);
            blocks.add(new SelectionButton(ImagesTest.getImage(type, number), type + number));
        }
    }
    private void addEnemies() {
        enemies.add(new SelectionButton(ImagesTest.getImage("Z", "1"), "Z1"));
        enemies.add(new SelectionButton(ImagesTest.getImage("M", "1"), "M1"));
        enemies.add(new SelectionButton(ImagesTest.getImage("N", "1"), "N1"));
        enemies.add(new SelectionButton(ImagesTest.getImage("I", "1"), "I1"));
        enemies.add(new SelectionButton(ImagesTest.getImage("U", "1"), "U1"));
    }

    private void addPlayers() {
        String type = "P";
        for (int i = 1; i <= 1; i++) {
            String number = String.valueOf(i);
            players.add(new SelectionButton(ImagesTest.getImage(type, number), type + number));
        }
    }

    private void setSize() {
        Dimension size = new Dimension((int) (60 * SCALE), PANEL_HEIGHT);
        setPreferredSize(size);
    }

    private void deselectAllButtons() {
        for (SelectionButton b : blocks)
            b.setSelected(false);
        for (SelectionButton b : enemies)
            b.setSelected(false);
        for (SelectionButton b : players)
            b.setSelected(false);
        for (SelectionButton b : others)
            b.setSelected(false);
    }

    public SelectionButton getCurrentButton() {
        return selectedButton;
    }

    public void setCurrentButton(SelectionButton selectedButton) {
        this.selectedButton = selectedButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (SelectionButton b : blocks)
            b.repaint();
        for (SelectionButton b : enemies)
            b.repaint();
        for (SelectionButton b : players)
            b.repaint();
        for (SelectionButton b : others)
            b.repaint();
    }
}
