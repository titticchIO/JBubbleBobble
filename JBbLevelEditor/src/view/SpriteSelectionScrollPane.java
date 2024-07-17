package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import static view.EditorPanel.PANEL_WIDTH;
import static view.EditorPanel.PANEL_HEIGHT;

public class SpriteSelectionScrollPane extends JScrollPane {
	//List<SelectionButton> buttons;
	private Map<SelectionButton, String> buttons;
	private static boolean selected = false;
	private static String actualSelection;

	public SpriteSelectionScrollPane() {
		buttons = new HashMap<>();
		//buttons = new ArrayList<SelectionButton>();
		//buttons.add(new SelectionButton("/sprites/bubblun/image_5.png"));
		//buttons.add(new SelectionButton("/sprites/invader/image_1.png"));
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_3.png"), "/blocks/normal_blocks/block_3.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_4.png"), "/blocks/normal_blocks/block_4.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_5.png"), "/blocks/normal_blocks/block_5.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_6.png"), "/blocks/normal_blocks/block_6.png");
		buttons.put(new SelectionButton("/blocks/normal_blocks/block_7.png"), "/blocks/normal_blocks/block_7.png");
		
		setSize();
		setLayout(new ScrollPaneLayout());
		JPanel buttonPanel=new JPanel(new GridLayout(3, 3));
		for(SelectionButton b: buttons.keySet()) {
			b.addActionListener(new ActionListener() {
				 @Override
		            public void actionPerformed(ActionEvent e) {
		                if (b.isSelected() && selected == false) {
		                	selected = true;
		                	actualSelection = buttons.get(b);
		                	
		                    //System.out.println("Button is selected!");
		                    //System.out.println(buttons.get(b));
		                } else {
		                    System.out.println("Button is already selected!");
		                	selected = false;
		                }
		            }
	        });
			buttonPanel.add(b);
		}	
		setViewportView(buttonPanel);
		
	}
	private void setSize() {
		Dimension size = new Dimension(200, PANEL_HEIGHT);
		setPreferredSize(size);
	}
	
	public static String getActualSelection() {
		return actualSelection;
	}
	
	public static boolean isSelected() {
		return selected;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(SelectionButton b:buttons.keySet()) 
			b.repaint();
	}
}
