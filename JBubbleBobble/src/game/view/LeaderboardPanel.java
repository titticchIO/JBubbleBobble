package game.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.BorderLayout;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.JLabel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import game.model.Model;
import game.model.user.User;

public class LeaderboardPanel extends JPanel {

    public LeaderboardPanel() {
        // Usa BorderLayout per il pannello principale
        setLayout(new BorderLayout());

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

        // Creazione della tabella per la leaderboard con un DefaultTableModel non modificabile
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rende tutte le celle non modificabili
            }
        };
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setBackground(Color.BLACK);
        table.setForeground(Color.YELLOW);

        // Personalizza l'intestazione della tabella
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer(table));

        // Creazione dello scrollPane e aggiunta della tabella
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Imposta la dimensione preferita del pannello
        setPreferredSize(new Dimension(550, 300));
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Eventuale codice per disegnare uno sfondo o altri elementi grafici
    }

    // Renderer personalizzato per l'intestazione della tabella
    private static class HeaderRenderer implements TableCellRenderer {
        private final DefaultTableCellRenderer renderer;

        public HeaderRenderer(JTable table) {
            this.renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
            this.renderer.setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            component.setBackground(Color.BLACK);
            component.setForeground(Color.WHITE);
            // Imposta il bordo arancione per le celle dell'intestazione
            MatteBorder border = new MatteBorder(1, 1, 1, 1, Color.RED);
            ((JLabel) component).setBorder(border);
            return component;
        }
    }
}
