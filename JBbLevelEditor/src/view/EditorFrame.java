package view;

import javax.swing.JFrame;

public class EditorFrame extends JFrame {
	EditorPanel editorPanel;
	public EditorFrame() {
		editorPanel=new EditorPanel();
		add(editorPanel);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	public EditorPanel getEditorPanel() {
		return editorPanel;
	}
}
