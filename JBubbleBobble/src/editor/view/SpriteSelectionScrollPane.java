package editor.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

import utils.Images;

import static editor.view.EditorPanel.PANEL_HEIGHT;
import static editor.view.EditorPanel.SQUARE_SIZE;
import static editor.view.EditorPanel.SCALE;

public class SpriteSelectionScrollPane extends JScrollPane {
	// List<SelectionButton> buttons;
	private List<SelectionButton> buttons;
	private SelectionButton selectedButton;

	public SpriteSelectionScrollPane() {
		buttons = new ArrayList<SelectionButton>();
		// add the player button
		buttons.add(new SelectionButton(Images.PLAYER.getImg(), "P"));
		addBlocks();
		addEnemies();
		// add a blank button
		buttons.add(new SelectionButton(Images.EMPTY_BLOCK.getImg(), " "));
		setSize();
		setLayout(new ScrollPaneLayout());
		JPanel buttonPanel = new JPanel(new GridLayout(buttons.size(), 1));
		buttonPanel.setSize(new Dimension((int) (100 * SCALE), (int) (buttons.size() * 40 * SCALE)));
		for (SelectionButton b : buttons) {
			b.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					deselectAllButtons();
					b.setSelected(true);
					setCurrentButton(b);
				}
			});
			buttonPanel.add(b);
		}
		setViewportView(buttonPanel);

	}

	private void addBlocks() {
		SelectionButton b1 = new SelectionButton(Images.BLOCK1.getImg(), "#1");
		b1.setSelected(true);
		setCurrentButton(b1);
		buttons.add(b1);
		buttons.add(new SelectionButton(Images.BLOCK2.getImg(), "#2"));
		buttons.add(new SelectionButton(Images.BLOCK3.getImg(), "#3"));
		buttons.add(new SelectionButton(Images.BLOCK4.getImg(), "#4"));
		buttons.add(new SelectionButton(Images.BLOCK5.getImg(), "#5"));
		buttons.add(new SelectionButton(Images.BLOCK6.getImg(), "#6"));
	}

	private void addEnemies() {
		buttons.add(new SelectionButton(Images.ZEN_CHAN.getImg(), "Z"));
		buttons.add(new SelectionButton(Images.MONSTA.getImg(), "M"));
		buttons.add(new SelectionButton(Images.BANEBOU.getImg(), "B"));
		buttons.add(new SelectionButton(Images.MIGHTA.getImg(), "I"));
		buttons.add(new SelectionButton(Images.PULPUL.getImg(), "U"));
	}

	private void setSize() {
		Dimension size = new Dimension((int) (40 * SCALE), PANEL_HEIGHT);
		setPreferredSize(size);
	}

	private void deselectAllButtons() {
		for (SelectionButton b : buttons)
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
		for (SelectionButton b : buttons)
			b.repaint();
	}
}
