package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import static view.EditorPanel.PANEL_WIDTH;
import static view.EditorPanel.PANEL_HEIGHT;

public class SpriteSelectionScrollPane extends JScrollPane {
	List<SelectionButton> buttons;

	public SpriteSelectionScrollPane() {
		buttons = new ArrayList<SelectionButton>();
		buttons.add(new SelectionButton(Sprites.PLAYER));
		buttons.add(new SelectionButton(Sprites.BLOCK));
		buttons.add(new SelectionButton(Sprites.ENEMY));
		setSize();
		setLayout(new ScrollPaneLayout());
		JPanel buttonPanel=new JPanel(new GridLayout(1, 3));
		for(SelectionButton b:buttons) 
			buttonPanel.add(b);
		setViewportView(buttonPanel);
		
	}
	private void setSize() {
		Dimension size = new Dimension(PANEL_WIDTH, 60);
		setPreferredSize(size);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(SelectionButton b:buttons) 
			b.repaint();
	}
}
