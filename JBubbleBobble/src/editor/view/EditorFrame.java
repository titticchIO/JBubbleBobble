package editor.view;

import java.awt.BorderLayout;

import java.awt.FlowLayout;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;


import editor.model.LevelManager;

import editor.view.LevelsPopUpMenu.MenuType;


public class EditorFrame extends JFrame {
	private EditorPanel editorPanel;
	private SpriteSelectionScrollPane selectionPane;
	private JButton saveLevelButton;
	private String actualLevelNumber;
	private JLabel actualLevel;

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
		JButton deleteLevelButton = new JButton("Delete");
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

		// Creazione del bottone con la scritta "Save"
		saveLevelButton = new JButton("Save");

		LevelsPopUpMenu levelSelectionPopup= new LevelsPopUpMenu(MenuType.OPEN, this);
		LevelsPopUpMenu saveLevelPopup= new LevelsPopUpMenu(MenuType.SAVE, this);
		LevelsPopUpMenu deleteLevelPopup= new LevelsPopUpMenu(MenuType.DELETE, this);

		// Aggiunta dei pulsanti al pannello
		topPanel.add(newGridButton);
		topPanel.add(openGridButton);
		topPanel.add(saveLevelButton);
		topPanel.add(deleteLevelButton);
		topPanel.add(actualLevel);

		// ActionListener per il pulsante "Apri Griglia"
		openGridButton.addActionListener(e -> {
			levelSelectionPopup.show(openGridButton, 0, openGridButton.getHeight());
		});
		saveLevelButton.addActionListener(e -> {
			saveLevelPopup.show(saveLevelButton, 0, saveLevelButton.getHeight());
		});
		deleteLevelButton.addActionListener(e -> {
			deleteLevelPopup.show(deleteLevelButton, 0, deleteLevelButton.getHeight());
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

	public void setEditorPanel(EditorPanel editorPanel) {
		this.editorPanel = editorPanel;
	}

	public String getActualLevelNumber() {
		return actualLevelNumber;
	}

	public void setActualLevelNumber(String actualLevelNumber) {
		this.actualLevelNumber = actualLevelNumber;
	}

	public JLabel getActualLevel() {
		return actualLevel;
	}

	public void setActualLevel(JLabel actualLevel) {
		this.actualLevel = actualLevel;
	}

	public SpriteSelectionScrollPane getSelectionPane() {
		return selectionPane;
	}

}
