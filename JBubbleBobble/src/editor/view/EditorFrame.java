package editor.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import editor.model.LevelManager;
import editor.model.LevelReader;
import game.model.level.LevelLoader;

public class EditorFrame extends JFrame {
	private EditorPanel editorPanel;
	private SpriteSelectionScrollPane selectionPane;
	private JButton myButton;
	private String actualLevelNumber = "";
	private JLabel actualLevel; // Dichiara JLabel come variabile d'istanza

	@SuppressWarnings("serial")
	public EditorFrame() {
		setLayout(new BorderLayout());
		selectionPane = new SpriteSelectionScrollPane();
		editorPanel = new EditorPanel(this, selectionPane);

		// Creazione del pannello superiore
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));


		// Pulsanti per creare nuova griglia e aprirne una esistente
		JButton newGridButton = new JButton("Nuovo");
		JButton openGridButton = new JButton("Apri");
		actualLevel = new JLabel(actualLevelNumber); // Inizializza JLabel

        // ActionListener per il pulsante "Nuova Griglia"
        newGridButton.addActionListener(e -> {
            // Azione per creare una nuova griglia
            LevelManager.emptyLevel(); // Presumendo che questo svuoti il livello corrente
            getContentPane().remove(editorPanel); // Rimuovi il pannello esistente
            editorPanel = new EditorPanel(this, selectionPane); // Crea un nuovo EditorPanel
            add(editorPanel, BorderLayout.CENTER); // Aggiungi il nuovo EditorPanel al frame
            actualLevelNumber = ""; // Resetta il numero del livello
            actualLevel.setText("Livello " + actualLevelNumber); // Aggiorna l'etichetta
            revalidate(); // Aggiorna il layout del frame
            repaint(); // Ridisegna il frame
        });

		List<String> levels = LevelReader.getLevels();

		JPopupMenu levelSelection = new JPopupMenu();


		// Aggiungere lo JScrollPane al JPopupMenu
		levelSelection.add(new JScrollPane(new JPanel() {
			{
				setLayout(new GridLayout(0, 1)); // Layout verticale
				levels.forEach(level -> add(new JButton("Livello "+level) {
					{
						addActionListener(e -> {
							try {
								int numero = Integer.parseInt(level);
								String[][] levelData = LevelLoader.readLevelFile(numero);

                    if (levelData != null) {
                        getContentPane().remove(editorPanel); // Rimuovi il pannello esistente
                        editorPanel = new EditorPanel(EditorFrame.this, selectionPane); // Crea un nuovo EditorPanel
                        LevelManager.setLevel(levelData);
                        editorPanel.loadLevel(levelData); // Carica i dati del livello
                        actualLevelNumber = String.valueOf(numero);
                        actualLevel.setText("Livello " + actualLevelNumber); // Aggiorna l'etichetta
                        EditorFrame.this.add(editorPanel, BorderLayout.CENTER); // Aggiungi il nuovo EditorPanel al frame
                        EditorFrame.this.revalidate(); // Aggiorna il layout del frame
                        EditorFrame.this.repaint(); // Ridisegna il frame
                    } else {
                        JOptionPane.showMessageDialog(this, "Errore: livello non trovato.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Per favore, inserisci un numero valido.", "Errore", JOptionPane.ERROR_MESSAGE);
                }


							levelSelection.setVisible(false);
						});
					}
				}));
			}}){{setPreferredSize(new Dimension(500,200)); // Imposta la dimensione preferita
	}});

	// Aggiunta dei pulsanti al pannello
	topPanel.add(newGridButton);topPanel.add(openGridButton);topPanel.add(actualLevel);
	
	// Creazione del bottone con la scritta "SALVA" 
	myButton = new JButton("SALVA");
	// Aggiunta dell'ActionListener al bottone
	myButton.addActionListener(e->{

	String inputValue = JOptionPane.showInputDialog(this, "Inserisci il numero del livello da salvare:");

	if(inputValue!=null&&!inputValue.isEmpty())
	{
		try {
			int numero = Integer.parseInt(inputValue);
			LevelManager.saveLevelToFile(numero);
			actualLevelNumber = String.valueOf(numero);
			actualLevel.setText("Livello " + actualLevelNumber); // Aggiorna l'etichetta
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Per favore, inserisci un numero valido.", "Errore",
					JOptionPane.ERROR_MESSAGE);
		}
	}});

	// ActionListener per il pulsante "Apri Griglia"
	openGridButton.addActionListener(e->{levelSelection.show(openGridButton,0,openGridButton.getHeight());
	});

	// Creazione del bottone con la scritta "SALVA"
	myButton=new JButton("SALVA");

	// Aggiunta dell'ActionListener al bottone
	myButton.addActionListener(e->{
	String inputValue = JOptionPane.showInputDialog(this, "Inserisci il numero del livello da salvare:");

	if(inputValue!=null&&!inputValue.isEmpty())
	{
				try {
					int numero = Integer.parseInt(inputValue);
					LevelManager.saveLevelToFile(numero);
					actualLevelNumber = String.valueOf(numero);
					actualLevel.setText("Livello " + actualLevelNumber); // Aggiorna l'etichetta
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "Per favore, inserisci un numero valido.", "Errore",
							JOptionPane.ERROR_MESSAGE);
				}
			}
	});

	// Aggiunta dei componenti al frame
	add(topPanel,BorderLayout.NORTH); // Pannello superiore
	add(editorPanel,BorderLayout.CENTER); // Pannello centrale
	add(selectionPane,BorderLayout.EAST); // Pannello di selezione
	add(myButton,BorderLayout.SOUTH); // Bottone di salvataggio

	pack();setLocationRelativeTo(null);setResizable(false);setVisible(true);}

	public EditorPanel getEditorPanel() {
		return editorPanel;
	}
}
