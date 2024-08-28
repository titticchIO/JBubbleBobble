package editor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.Border;

import editor.controller.ActionListenersManager;
import game.view.ImageLoader;
import game.view.Images;

import static editor.view.EditorPanel.PANEL_HEIGHT;
import static editor.view.EditorPanel.SCALE;

public class SpriteSelectionScrollPane extends JScrollPane {
	private static final long serialVersionUID = 1L;
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
		SelectionButton eraser = new SelectionButton(ImageLoader.importImg("/editor/eraser.png"), ' ');
		others.add(eraser);
		setCurrentButton(eraser);

		// others.add(new SelectionButton(ImageLoader.importImg("/editor/eraser.png"), "
		// "));

		setSize();
		setLayout(new ScrollPaneLayout());

		// Definisci la dimensione della griglia, contando i bottoni e le etichette
		int totalRows = blocks.size() + enemies.size() + players.size() + others.size() + 4; // Aggiungi 4 per le
																								// etichette

		JPanel buttonPanel = new JPanel(new GridLayout(totalRows, 1));
		buttonPanel.setBackground(Color.BLACK);
		// buttonPanel.setForeground(Color.YELLOW);
		buttonPanel.setSize(new Dimension((int) (100 * SCALE), (int) (totalRows * 40 * SCALE)));

		// Aggiungi i titoli e i bottoni per ogni categoria
		addCategory(buttonPanel, "Blocchi", blocks);
		addCategory(buttonPanel, "Nemici", enemies);
		addCategory(buttonPanel, "Player", players);
		addCategory(buttonPanel, "Altro", others);

		setViewportView(buttonPanel);

		// setBackground(Color.BLACK);
	}

	private void addCategory(JPanel panel, String title, List<SelectionButton> buttons) {
		// Crea un bordo di colore arancione
		Border grayBorder = BorderFactory.createLineBorder(Color.GRAY, 1);

		// Aggiungi il titolo
		JLabel label = new JLabel(title);
		label.setForeground(Color.YELLOW); // Imposta il colore del testo a giallo
		label.setBorder(grayBorder); // Imposta il bordo arancione sull'etichetta
		label.setHorizontalAlignment(JLabel.CENTER); // Allinea il testo orizzontalmente al centro
		panel.add(label);

		// Aggiungi i bottoni corrispondenti
		for (SelectionButton b : buttons) {
			b.addActionListener(ActionListenersManager.addSelectionButton(this, b));
			panel.add(b);
		}
	}

	private void addBlocks() {
		for (int i = 1; i <= 9; i++) {
			char number = (char) (i+'0');
			blocks.add(new SelectionButton(Images.getImage(number), number));
		}
	}

	private void addEnemies() {
		enemies.add(new SelectionButton(Images.getImage('Z'), 'Z'));
		enemies.add(new SelectionButton(Images.getImage('M'), 'M'));
		enemies.add(new SelectionButton(Images.getImage('N'), 'N'));
		enemies.add(new SelectionButton(Images.getImage('I'), 'I'));
		enemies.add(new SelectionButton(Images.getImage('U'), 'U'));
		enemies.add(new SelectionButton(Images.getImage('S'), 'S'));
	}

	private void addPlayers() {
		players.add(new SelectionButton(Images.getImage('P', "static"), 'P'));
	}

	private void setSize() {
		Dimension size = new Dimension((int) (80 * SCALE), PANEL_HEIGHT);
		setPreferredSize(size);
	}

	public void deselectAllButtons() {
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
