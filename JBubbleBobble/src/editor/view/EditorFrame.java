package editor.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import editor.model.LevelManager;
import editor.model.LevelReader;
import game.model.level.LevelLoader;

public class EditorFrame extends JFrame {
	private EditorPanel editorPanel;
	private SpriteSelectionScrollPane selectionPane;
	private JButton saveLevelButton;
	private String actualLevelNumber = "";
	private JLabel actualLevel; // Dichiara JLabel come variabile d'istanza

	@SuppressWarnings("serial")
	public EditorFrame() {
		setLayout(new BorderLayout());
		selectionPane = new SpriteSelectionScrollPane();
		editorPanel = new EditorPanel(this, selectionPane);

		// Creazione del pannello superiore
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		// Pulsanti per creare nuova griglia e aprirne una esistente
		JButton newGridButton = new JButton("New");
		JButton openGridButton = new JButton("Open");
		actualLevel = new JLabel(actualLevelNumber); // Inizializza JLabel

		// ActionListener per il pulsante "Nuova Griglia"
		newGridButton.addActionListener(e -> {
			// Azione per creare una nuova griglia
			LevelManager.emptyLevel(); // Presumendo che questo svuoti il livello corrente
			getContentPane().remove(editorPanel); // Rimuovi il pannello esistente
			editorPanel = new EditorPanel(this, selectionPane); // Crea un nuovo EditorPanel
			add(editorPanel, BorderLayout.CENTER); // Aggiungi il nuovo EditorPanel al frame
			actualLevelNumber = ""; // Resetta il numero del livello
			actualLevel.setText("Level " + actualLevelNumber); // Aggiorna l'etichetta
			revalidate(); // Aggiorna il layout del frame
			repaint(); // Ridisegna il frame
		});

		JPopupMenu levelSelection = new JPopupMenu();

		// Aggiungere lo JScrollPane al JPopupMenu
		levelSelection.add(new JScrollPane(new JPanel() {
			{
				setLayout(new GridLayout(0, 1)); // Layout verticale
				LevelReader.getLevels().forEach(level -> add(new JButton("Level " + level) {
					{
						addActionListener(e -> {
							try {
								int numero = Integer.parseInt(level);
								String[][] levelData = LevelLoader.readLevelFile(numero);

								if (levelData != null) {
									getContentPane().remove(editorPanel); // Rimuovi il pannello esistente
									editorPanel = new EditorPanel(EditorFrame.this, selectionPane); // Crea un nuovo
																									// EditorPanel
									LevelManager.setLevel(levelData);
									editorPanel.loadLevel(levelData); // Carica i dati del livello
									actualLevelNumber = String.valueOf(numero);
									actualLevel.setText("Livello " + actualLevelNumber); // Aggiorna l'etichetta
									EditorFrame.this.add(editorPanel, BorderLayout.CENTER); // Aggiungi il nuovo
																							// EditorPanel al frame
									EditorFrame.this.revalidate(); // Aggiorna il layout del frame
									EditorFrame.this.repaint(); // Ridisegna il frame
								} else {
									JOptionPane.showMessageDialog(this, "Error: level not found.", "Error",
											JOptionPane.ERROR_MESSAGE);
								}
							} catch (NumberFormatException ex) {
								JOptionPane.showMessageDialog(this, "Please, enter a valid number.", "Error",
										JOptionPane.ERROR_MESSAGE);
							}

							levelSelection.setVisible(false);
						});
					}
				}));
			}
		}) {
			{
				setPreferredSize(new Dimension(500, 200)); // Imposta la dimensione preferita
			}
		});

		// Creazione del bottone con la scritta "Save"
		saveLevelButton = new JButton("Save");

		JPopupMenu saveLevelPopup = new JPopupMenu();

		saveLevelPopup.add(new JScrollPane(new JPanel() {
			{
				setLayout(new GridLayout(0, 1)); // Layout verticale
				LevelReader.getLevels().forEach(level -> add(new JButton("Level " + level) {
					{
						addActionListener(e -> {
							int numero = Integer.parseInt(level);
							LevelManager.saveLevelFile(numero);
							actualLevelNumber = String.valueOf(numero);
							actualLevel.setText("Level " + actualLevelNumber); // Aggiorna l'etichetta
						});
					}
				}));
			}
		}));

		// Aggiunta dei pulsanti al pannello
		topPanel.add(newGridButton);
		topPanel.add(openGridButton);
		topPanel.add(saveLevelButton);
		topPanel.add(actualLevel);

		// ActionListener per il pulsante "Apri Griglia"
		openGridButton.addActionListener(e -> {
			levelSelection.show(openGridButton, 0, openGridButton.getHeight());
		});
		saveLevelButton.addActionListener(e -> {
			saveLevelPopup.show(saveLevelButton, 0, saveLevelButton.getHeight());
		});

		// Aggiunta dei componenti al frame
		add(topPanel, BorderLayout.NORTH); // Pannello superiore
		add(editorPanel, BorderLayout.CENTER); // Pannello centrale
		add(selectionPane, BorderLayout.EAST); // Pannello di selezione

		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	public EditorPanel getEditorPanel() {
		return editorPanel;
	}
}
