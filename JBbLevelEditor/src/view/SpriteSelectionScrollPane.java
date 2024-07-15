package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;

public class SpriteSelectionScrollPane extends JScrollPane {
	List<JButton> buttons;

	public SpriteSelectionScrollPane() {
		buttons = new ArrayList<JButton>();
	}
}
