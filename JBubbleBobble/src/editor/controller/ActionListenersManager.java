package editor.controller;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import editor.model.LevelManager;
import editor.view.EditorFrame;
import editor.view.EditorPanel;
import editor.view.LevelsPopUpMenu;
import editor.view.SelectionButton;
import editor.view.Sprite;
import editor.view.LevelsPopUpMenu.MenuType;
import editor.view.SpriteSelectionScrollPane;
import static editor.view.EditorPanel.SQUARE_SIZE;

/**
 * Manages the creation of action listeners for various UI components.
 */
public class ActionListenersManager {

	/**
	 * Creates an ActionListener to create a new grid.
	 * 
	 * @param editorPanel   the panel where the grid is displayed
	 * @param selectionPane the pane with sprite selection
	 * @return the ActionListener for creating a new grid
	 */
	public static ActionListener newGridButton(EditorPanel editorPanel, SpriteSelectionScrollPane selectionPane) {
		return e -> {
			EditorFrame ef = EditorFrame.getInstance();
			// Action to create a new grid
			LevelManager.emptyLevel(); // Assuming this empties the current level
			ef.getEditorPanel().reset();
			ef.setActualLevelNumber("");
			ef.getActualLevel().setText("Level " + ef.getActualLevelNumber());
			ef.getEditorPanel().repaint(); // Repaint the frame
			ef.revalidate(); // Refresh the layout of the frame
			ef.repaint(); // Repaint the frame
		};
	}

	/**
	 * Creates an ActionListener to open a grid.
	 * 
	 * @param levelSelectionPopup the popup menu for level selection
	 * @param openGridButton      the button to trigger the popup
	 * @return the ActionListener for opening a grid
	 */
	public static ActionListener openGridButton(LevelsPopUpMenu levelSelectionPopup, JButton openGridButton) {
		return e -> levelSelectionPopup.show(openGridButton, 0, openGridButton.getHeight());
	}

	/**
	 * Creates an ActionListener to save the current level.
	 * 
	 * @param saveLevelPopup  the popup menu for saving the level
	 * @param saveLevelButton the button to trigger the popup
	 * @return the ActionListener for saving the level
	 */
	public static ActionListener saveLevelButton(LevelsPopUpMenu saveLevelPopup, JButton saveLevelButton) {
		return e -> saveLevelPopup.show(saveLevelButton, 0, saveLevelButton.getHeight());
	}

	/**
	 * Creates an ActionListener to delete a level.
	 * 
	 * @param deleteLevelPopup  the popup menu for deleting the level
	 * @param deleteLevelButton the button to trigger the popup
	 * @return the ActionListener for deleting the level
	 */
	public static ActionListener deleteLevelButton(LevelsPopUpMenu deleteLevelPopup, JButton deleteLevelButton) {
		return e -> deleteLevelPopup.show(deleteLevelButton, 0, deleteLevelButton.getHeight());
	}

	/**
	 * Creates an ActionListener to handle different level operations like open,
	 * save, or delete.
	 * 
	 * @param levelsPopUpMenu the popup menu for level operations
	 * @param level           the level to operate on
	 * @param menuType        the type of operation (OPEN, SAVE, DELETE)
	 * @param editorFrame     the frame that displays the editor
	 * @return the ActionListener for handling the level operation
	 */
	public static ActionListener levelHandlers(LevelsPopUpMenu levelsPopUpMenu, String level, MenuType menuType,
			EditorFrame editorFrame) {
		return e -> {
			int numero = Integer.parseInt(level);
			switch (menuType) {
			case OPEN -> levelsPopUpMenu.handleOpenLevel(numero, editorFrame);
			case SAVE -> levelsPopUpMenu.handleSaveLevel(numero, editorFrame);
			case DELETE -> levelsPopUpMenu.handleDeleteLevel(numero, editorFrame);
			}
		};
	}

	/**
	 * Creates an ActionListener to handle saving a level with a specific number.
	 * 
	 * @param lvlPopMen      the popup menu for level operations
	 * @param newLevelNumber the new level number to save
	 * @param editorFrame    the frame that displays the editor
	 * @return the ActionListener for handling level saving
	 */
	public static ActionListener handleSaveLevel(LevelsPopUpMenu lvlPopMen, int newLevelNumber,
			EditorFrame editorFrame) {
		return e -> lvlPopMen.handleSaveLevel(newLevelNumber, editorFrame);
	}

	/**
	 * Creates an ActionListener to handle selection of a button in the sprite
	 * selection pane.
	 * 
	 * @param spriteSelectionScrollPane the pane that contains sprite selection
	 *                                  buttons
	 * @param b                         the button to select
	 * @return the ActionListener for handling button selection
	 */
	public static ActionListener addSelectionButton(SpriteSelectionScrollPane spriteSelectionScrollPane,
			SelectionButton b) {
		return e -> {
			spriteSelectionScrollPane.deselectAllButtons();
			b.setSelected(true);
			spriteSelectionScrollPane.setCurrentButton(b);
		};
	}

	// Editor Panel Action Listeners

	/**
	 * Updates the Sprite at the specified coordinates to the current Sprite
	 * selected
	 * 
	 * @param editorPanel the JPanel containing the Sprite
	 * @param sprite      the Sprite to be updated
	 * @param x           the x coordinate of the Sprite
	 * @param y           the y coordinate of the Sprite
	 * @return the ActionListener to perform the Action
	 */
	public static MouseAdapter updateSpriteClick(EditorPanel editorPanel, int x, int y) {
		return new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				editorPanel.setMousePressed(true);
				editorPanel.updateSprite(x, y);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				editorPanel.setMousePressed(false);
			}
		};
	}

	/**
	 * Updates the Sprite at the specified coordinates to the current Sprite
	 * selected
	 * 
	 * @param editorPanel the JPanel containing the Sprite
	 * @param sprite      the Sprite to be updated
	 * @param x           the x coordinate of the Sprite
	 * @param y           the y coordinate of the Sprite
	 * @return the ActionListener to perform the Action
	 */
	public static MouseAdapter updateSpriteDrag(EditorPanel editorPanel, int x, int y) {
		return new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (editorPanel.isMousePressed()) {
					int x = (e.getXOnScreen() - editorPanel.getLocationOnScreen().x) / SQUARE_SIZE;
					int y = (e.getYOnScreen() - editorPanel.getLocationOnScreen().y) / SQUARE_SIZE;
					try {
						editorPanel.updateSprite(x, y);
					} catch (Exception ignored) {
					}
				}
			}
		};
	}

}
