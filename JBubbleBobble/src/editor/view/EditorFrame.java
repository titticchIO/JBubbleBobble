package editor.view;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import utils.LevelMaker;

public class EditorFrame extends JFrame {
    EditorPanel editorPanel;
    SpriteSelectionScrollPane selectionPane;
    JButton myButton;

    public EditorFrame() {
        setLayout(new BorderLayout());
        selectionPane = new SpriteSelectionScrollPane();
        editorPanel = new EditorPanel(this,selectionPane);
        
        // Creazione del bottone con la scritta "Click Me"
        myButton = new JButton("SALVA");
        
        // Aggiunta dell'ActionListener al bottone
        myButton.addActionListener(e -> {
            LevelMaker.saveLevelToFile(2);
            // Puoi aggiungere qui altre azioni da eseguire quando il bottone viene cliccato
        });
        
        // Aggiunta dei componenti al frame
        add(editorPanel, BorderLayout.CENTER);
        add(selectionPane, BorderLayout.EAST);
        add(myButton, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public EditorPanel getEditorPanel() {
        return editorPanel;
    }
}
