package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JFrame;

public class EditorFrame extends JFrame {
	EditorPanel editorPanel;
	public EditorFrame() {
		setLayout(new BorderLayout());
		editorPanel=new EditorPanel();
		add(editorPanel,BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	public EditorPanel getEditorPanel() {
		return editorPanel;
	}
}
