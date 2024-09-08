package game.view.panels;

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

/**
 * The {@code LeaderboardPanel} class is responsible for displaying a leaderboard
 * of users in a graphical user interface. It extends {@link JPanel} and contains
 * a table with information about the users' scores, games played, games won, and games lost.
 * The leaderboard data is displayed in descending order based on the users' high scores.
 *
 * <p>This panel is designed to be non-editable, with custom styling applied to the table headers
 * and the table itself. The panel uses a {@link BorderLayout} and is set to a preferred
 * size of 550x300 pixels.
 *
 * <p>The table contains the following columns:
 * <ul>
 *     <li>Player - The nickname of the user</li>
 *     <li>HighScore - The highest score achieved by the user</li>
 *     <li>Played Games - The number of games the user has played</li>
 *     <li>Games Won - The number of games the user has won</li>
 *     <li>Games Lost - The number of games the user has lost</li>
 * </ul>
 *
 * <p>The class also contains an inner static class {@link HeaderRenderer} which
 * customizes the rendering of the table header.
 */
public class LeaderboardPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	 /**
     * Constructs a new {@code LeaderboardPanel} that initializes the leaderboard table,
     * retrieves and displays user data sorted by high score in descending order,
     * and applies custom styling to the table and its header.
     */
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
        
        //Creation of the table for the leaderboard
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setBackground(Color.BLACK);
        table.setForeground(Color.YELLOW);

        // Custom table header
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer(table));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(550, 300));
        
    }

    /**
     * A custom renderer for the table header that centers the text,
     * sets the background color to black, and applies an orange border.
     */
    private static class HeaderRenderer implements TableCellRenderer {
        private final DefaultTableCellRenderer renderer;

        /**
         * Constructs a new {@code HeaderRenderer} with a reference to the table
         * and applies the custom styling for the header cells.
         *
         * @param table the {@link JTable} whose header will be customized
         */
        public HeaderRenderer(JTable table) {
            this.renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
            this.renderer.setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            component.setBackground(Color.BLACK);
            component.setForeground(Color.WHITE);
            MatteBorder border = new MatteBorder(1, 1, 1, 1, Color.RED);
            ((JLabel) component).setBorder(border);
            return component;
        }
    }
}
