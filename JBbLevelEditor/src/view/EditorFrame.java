package view;

import javax.swing.JFrame;

public class EditorFrame extends JFrame {
	public EditorFrame() {
		
		add(new EditorPanel());
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
}
