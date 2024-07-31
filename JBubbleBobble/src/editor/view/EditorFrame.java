package editor.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import utils.LevelLoader;
import utils.LevelMaker;

public class EditorFrame extends JFrame {
    EditorPanel editorPanel;
    SpriteSelectionScrollPane selectionPane;
    JButton myButton;

    public EditorFrame() {
        setLayout(new BorderLayout());
        selectionPane = new SpriteSelectionScrollPane();
        editorPanel = new EditorPanel(this, selectionPane);

        // Creazione del pannello superiore
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // Pulsanti per creare nuova griglia e aprirne una esistente
        JButton newGridButton = new JButton("Nuovo");
        JButton openGridButton = new JButton("Apri");
        
        // Aggiunta dei pulsanti al pannello
        topPanel.add(newGridButton);
        topPanel.add(openGridButton);

     // ActionListener per il pulsante "Nuova Griglia"
        newGridButton.addActionListener(e -> {
            // Azione per creare una nuova griglia
            LevelMaker.emptyLevel(); // Presumendo che questo svuoti il livello corrente
            getContentPane().remove(editorPanel); // Rimuovi il pannello esistente
            editorPanel = new EditorPanel(this, selectionPane); // Crea un nuovo EditorPanel
            add(editorPanel, BorderLayout.CENTER); // Aggiungi il nuovo EditorPanel al frame
            revalidate(); // Aggiorna il layout del frame
            repaint(); // Ridisegna il frame
        });


        // ActionListener per il pulsante "Apri Griglia"
        openGridButton.addActionListener(e -> {
            // Azione per aprire una griglia esistente
            String inputValue = JOptionPane.showInputDialog(this, "Inserisci il numero del livello da aprire:");
            
            if (inputValue != null && !inputValue.isEmpty()) {
                try {
                    int numero = Integer.parseInt(inputValue);
                    //LevelLoader.readLevelFile(numero);
                    
                    //editorPanel.openGrid(numero);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Per favore, inserisci un numero valido.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Creazione del bottone con la scritta "SALVA"
        myButton = new JButton("SALVA");

        // Aggiunta dell'ActionListener al bottone
        myButton.addActionListener(e -> {
            String inputValue = JOptionPane.showInputDialog(this, "Inserisci il numero del livello:");
            
            if (inputValue != null && !inputValue.isEmpty()) {
                try {
                    int numero = Integer.parseInt(inputValue);
                    LevelMaker.saveLevelToFile(numero);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Per favore, inserisci un numero valido.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Aggiunta dei componenti al frame
        add(topPanel, BorderLayout.NORTH);        // Pannello superiore
        add(editorPanel, BorderLayout.CENTER);    // Pannello centrale
        add(selectionPane, BorderLayout.EAST);    // Pannello di selezione
        add(myButton, BorderLayout.SOUTH);        // Bottone di salvataggio

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public EditorPanel getEditorPanel() {
        return editorPanel;
    }
}
