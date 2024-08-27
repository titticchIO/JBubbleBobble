package editor.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import editor.model.LevelManager;
import editor.view.EditorFrame;
import editor.view.EditorPanel;
import editor.view.LevelsPopUpMenu;
import editor.view.SelectionButton;
import editor.view.LevelsPopUpMenu.MenuType;
import editor.view.SpriteSelectionScrollPane;

public class ActionListenersManager {

	public static ActionListener newGridButton(EditorPanel editorPanel, SpriteSelectionScrollPane selectionPane) {
		return e -> {
			EditorFrame ef = EditorFrame.getInstance();
			// Azione per creare una nuova griglia
			LevelManager.emptyLevel(); // Presumendo che questo svuoti il livello corrente
			EditorFrame.getInstance().getEditorPanel().reset();
//			EditorFrame.getInstance().setEditorPanel(new EditorPanel(EditorFrame.getInstance(), selectionPane)); // Crea
			EditorFrame.getInstance().setActualLevel(new JLabel("")); // Aggiorna l'etichetta
			EditorFrame.getInstance().getEditorPanel().repaint(); // Ridisegna il frame
			EditorFrame.getInstance().revalidate(); // Aggiorna il layout del frame
			EditorFrame.getInstance().repaint(); // Ridisegna il frame
		};
	}

	public static ActionListener openGridButton(LevelsPopUpMenu levelSelectionPopup, JButton openGridButton) {
		return e -> levelSelectionPopup.show(openGridButton, 0, openGridButton.getHeight());
	}

	public static ActionListener saveLevelButton(LevelsPopUpMenu saveLevelPopup, JButton saveLevelButton) {
		return e -> saveLevelPopup.show(saveLevelButton, 0, saveLevelButton.getHeight());
	}

	public static ActionListener deleteLevelButton(LevelsPopUpMenu deleteLevelPopup, JButton deleteLevelButton) {
		return e -> deleteLevelPopup.show(deleteLevelButton, 0, deleteLevelButton.getHeight());
	}

	public static ActionListener levelHandlers(LevelsPopUpMenu levelsPopUpMenu, String level, MenuType menuType, EditorFrame editorFrame) {
		return e -> {
			int numero = Integer.parseInt(level);
			switch (menuType) {
			case OPEN:
				levelsPopUpMenu.handleOpenLevel(numero, editorFrame);
				break;
			case SAVE:
				levelsPopUpMenu.handleSaveLevel(numero, editorFrame);
				break;
			case DELETE:
				levelsPopUpMenu.handleDeleteLevel(numero, editorFrame);
				break;
			}
		};
	}
	
	public static ActionListener handleSaveLevel(int newLevelNumber, EditorFrame editorFrame) {
		return e -> handleSaveLevel(newLevelNumber, editorFrame);
	}
	
	public static ActionListener addSelectionButton(SpriteSelectionScrollPane spriteSelectionScrollPane, SelectionButton b) {
		return e -> {
			spriteSelectionScrollPane.deselectAllButtons();
			b.setSelected(true);
			spriteSelectionScrollPane.setCurrentButton(b);
		};
	}
}
