package game.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import game.model.Model;
import game.model.user.User;

public class LeaderboardPanel extends JPanel {

    public LeaderboardPanel() {
        setLayout(new GridBagLayout());

        // Nome delle colonne per la leaderboard
        String[] columnNames = {"Player", "HighScore", "Played Games", "Games Won", "Games Lost"};
        
        // Recupera e ordina la lista degli utenti in base al punteggio in ordine decrescente
        List<User> users = Model.getInstance().getUsers().stream()
            .sorted((u1, u2) -> Integer.compare(u2.getHighScore(), u1.getHighScore()))
            .collect(Collectors.toList());
        
        // Crea un array di oggetti per i dati della tabella
        Object[][] data = users.stream()
            .map(user -> new Object[]{
                user.getNickname(),
                user.getHighScore(),
                user.getPlayedGames(),
                user.getGamesWon(),
                user.getGamesLost()
            })
            .toArray(Object[][]::new);

        // Creazione della tabella per la leaderboard
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        scrollPane.setPreferredSize(new Dimension(400, 200)); // Aggiornata la dimensione per accomodare più colonne

        // Aggiungi la tabella al pannello
        add(scrollPane);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Eventuale codice per disegnare uno sfondo o altri elementi grafici
    }
}
