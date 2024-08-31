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

import editor.model.LevelManager;

import editor.view.LevelsPopUpMenu.MenuType;
import editor.controller.ActionListenersManager;
import game.view.ImageLoader;
import game.view.View;

public class EditorFrame extends JFrame {
	private static EditorFrame instance;
	private EditorPanel editorPanel;
	private SpriteSelectionScrollPane selectionPane;
	private JButton saveLevelButton;
	private String actualLevelNumber;
	private JLabel actualLevel;
	private List<LevelsPopUpMenu> popUps;

	public static EditorFrame getInstance() {
		if (instance == null)
			instance = new EditorFrame();
		return instance;
	}

	@SuppressWarnings("serial")
	private EditorFrame() {
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

		JButton openGridButton = new JButton(new ImageIcon(
				ImageLoader.importImg("/editor/open.png").getScaledInstance(70, 35, Image.SCALE_SMOOTH))) {
			{
				setContentAreaFilled(false);
				setPreferredSize(new Dimension(70, 35));
				setBorderPainted(false);
				setFocusPainted(false);
			}
		};

		JButton deleteLevelButton = new JButton(new ImageIcon(
				ImageLoader.importImg("/editor/delete.png").getScaledInstance(70, 35, Image.SCALE_SMOOTH))) {
			{
				setContentAreaFilled(false);
				setPreferredSize(new Dimension(70, 35));
				setBorderPainted(false);
				setFocusPainted(false);
			}
		};

		actualLevel = new JLabel(actualLevelNumber);
		actualLevel.setForeground(Color.YELLOW); // Inizializza JLabel

		// ActionListener per il pulsante "Nuova Griglia"
		newGridButton.addActionListener(ActionListenersManager.newGridButton(editorPanel, selectionPane));

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
		openGridButton.addActionListener(ActionListenersManager.openGridButton(levelSelectionPopup, openGridButton));
		saveLevelButton.addActionListener(ActionListenersManager.saveLevelButton(saveLevelPopup, saveLevelButton));
		deleteLevelButton
				.addActionListener(ActionListenersManager.deleteLevelButton(deleteLevelPopup, deleteLevelButton));

		// Aggiunta dei componenti al frame
		add(topPanel, BorderLayout.NORTH); // Pannello superiore
		add(editorPanel, BorderLayout.CENTER); // Pannello centrale
		add(selectionPane, BorderLayout.EAST); // Pannello di selezione

		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// Riporta il focus sul GameFrame
				View.getInstance().getGameFrame().requestFocus();
				// Chiude il frame
				dispose(); // Chiude il EditorFrame

			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
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
