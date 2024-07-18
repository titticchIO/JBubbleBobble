package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

import static view.EditorPanel.PANEL_HEIGHT;
import static view.EditorPanel.SQUARE_SIZE;
import static view.EditorPanel.SCALE;

public class SpriteSelectionScrollPane extends JScrollPane {
	// List<SelectionButton> buttons;
	private Map<SelectionButton, String> buttons;
	private String currentPath;

	public SpriteSelectionScrollPane() {
		buttons = new HashMap<>();
		SelectionButton b1 = new SelectionButton("/blocks/normal_blocks/block_3.png");
		//------------------------------------------------------------------------------------------------------------
		buttons.put(b1, "/blocks/normal_blocks/block_3.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_4.png"), "/blocks/normal_blocks/block_4.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_5.png"), "/blocks/normal_blocks/block_5.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_6.png"), "/blocks/normal_blocks/block_6.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		//------------------------------------------------------------------------------------------------------------
		
		b1.setSelected(true);
		setCurrentPath("/blocks/normal_blocks/block_3.png");
		setSize();
		setLayout(new ScrollPaneLayout());
		JPanel buttonPanel = new JPanel(new GridLayout(buttons.size(), 1));
		buttonPanel.setSize(new Dimension((int)(100*SCALE), (int)(buttons.size()*40*SCALE)));
		for (SelectionButton b : buttons.keySet()) {
			b.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					deselectAllButtons();
					b.setSelected(true);
					setCurrentPath(buttons.get(b));
				}
			});
			buttonPanel.add(b);
		}
		setViewportView(buttonPanel);

	}

	private void setSize() {
		Dimension size = new Dimension((int)(40*SCALE), PANEL_HEIGHT);
		setPreferredSize(size);
	}

	private void deselectAllButtons() {
		for (SelectionButton b : buttons.keySet())
			b.setSelected(false);
	}

	public String getCurrentPath() {
		return currentPath;
	}

	public void setCurrentPath(String currentPath) {
		this.currentPath = currentPath;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (SelectionButton b : buttons.keySet())
			b.repaint();
	}
}
