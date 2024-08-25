package editor.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.ArrayList;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;

import editor.model.LevelManager;

import editor.view.LevelsPopUpMenu.MenuType;
import game.view.ImageLoader;

public class EditorFrame extends JFrame {
	private EditorPanel editorPanel;
	private SpriteSelectionScrollPane selectionPane;
	private JButton saveLevelButton;
	private String actualLevelNumber;
	private JLabel actualLevel;
	private List<LevelsPopUpMenu> popUps;

	@SuppressWarnings("serial")
	public EditorFrame() {
		setLayout(new BorderLayout());
		selectionPane = new SpriteSelectionScrollPane();
		// selectionPane.setBackground(Color.BLACK);
		editorPanel = new EditorPanel(this, selectionPane);
		// editorPanel.setBackground(Color.BLACK);
		popUps = new ArrayList<LevelsPopUpMenu>();

		// Creazione del pannello superiore
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topPanel.setBackground(Color.BLACK);

		// Pulsanti per creare nuova griglia e aprirne una esistente
		JButton newGridButton = new JButton(
				new ImageIcon(ImageLoader.importImg("/editor/new.png").getScaledInstance(70, 35, Image.SCALE_SMOOTH))) {
			{
				setContentAreaFilled(false);
				setPreferredSize(new Dimension(70, 35));
				setBorderPainted(false);
				setFocusPainted(false);

			}
		};

		// OLD CODE
//		ImageIcon newButtonImageIcon = new ImageIcon(
//				ImageLoader.importImg("/editor/new.png").getScaledInstance(70, 35, Image.SCALE_SMOOTH));
//		newGridButton.setIcon(newButtonImageIcon);
//		newGridButton.setContentAreaFilled(false);
//		newGridButton.setPreferredSize(new Dimension(70, 35));
//		newGridButton.setBorderPainted(false);
//		newGridButton.setFocusPainted(false);

		JButton openGridButton = new JButton(new ImageIcon(
				ImageLoader.importImg("/editor/open.png").getScaledInstance(70, 35, Image.SCALE_SMOOTH))) {
			{
				setContentAreaFilled(false);
				setPreferredSize(new Dimension(70, 35));
				setBorderPainted(false);
				setFocusPainted(false);
			}
		};

		// OLD CODE
//		ImageIcon openButtonImageIcon = new ImageIcon(
//				ImageLoader.importImg("/editor/open.png").getScaledInstance(70, 35, Image.SCALE_SMOOTH));
//		openGridButton.setIcon(openButtonImageIcon);
//		openGridButton.setContentAreaFilled(false);
//		openGridButton.setPreferredSize(new Dimension(70, 35));
//		openGridButton.setBorderPainted(false);
//		openGridButton.setFocusPainted(false);

		JButton deleteLevelButton = new JButton(new ImageIcon(
				ImageLoader.importImg("/editor/delete.png").getScaledInstance(70, 35, Image.SCALE_SMOOTH))) {
			{
				setContentAreaFilled(false);
				setPreferredSize(new Dimension(70, 35));
				setBorderPainted(false);
				setFocusPainted(false);
			}
		};

		// OLD CODE
//		ImageIcon deleteButtonImageIcon = new ImageIcon(
//				ImageLoader.importImg("/editor/delete.png").getScaledInstance(70, 35, Image.SCALE_SMOOTH));
//		deleteLevelButton.setIcon(deleteButtonImageIcon);
//		deleteLevelButton.setContentAreaFilled(false);
//		deleteLevelButton.setPreferredSize(new Dimension(70, 35));
//		deleteLevelButton.setBorderPainted(false);
//		deleteLevelButton.setFocusPainted(false);

		actualLevel = new JLabel(actualLevelNumber);
		actualLevel.setForeground(Color.YELLOW); // Inizializza JLabel

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
		saveLevelButton = new JButton(new ImageIcon(
				ImageLoader.importImg("/editor/save.png").getScaledInstance(70, 35, Image.SCALE_SMOOTH))) {
			{
				setContentAreaFilled(false);
				setPreferredSize(new Dimension(70, 35));
				setBorderPainted(false);
				setFocusPainted(false);
			}
		};

		// OLD CODE
//		ImageIcon saveButtonImageIcon = new ImageIcon(
//				ImageLoader.importImg("/editor/save.png").getScaledInstance(70, 35, Image.SCALE_SMOOTH));
//		saveLevelButton.setIcon(saveButtonImageIcon);
//		saveLevelButton.setContentAreaFilled(false);
//		saveLevelButton.setPreferredSize(new Dimension(70, 35));
//		saveLevelButton.setBorderPainted(false);
//		saveLevelButton.setFocusPainted(false);

		LevelsPopUpMenu levelSelectionPopup = new LevelsPopUpMenu(MenuType.OPEN, this);
		LevelsPopUpMenu saveLevelPopup = new LevelsPopUpMenu(MenuType.SAVE, this);
		LevelsPopUpMenu deleteLevelPopup = new LevelsPopUpMenu(MenuType.DELETE, this);

		popUps.add(levelSelectionPopup);
		popUps.add(saveLevelPopup);
		popUps.add(deleteLevelPopup);

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

	public List<LevelsPopUpMenu> getPopUps() {
		return popUps;
	}

}
