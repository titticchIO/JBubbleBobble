package game.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.*;
import game.controller.gamestates.Menu;
import game.model.Model;
import game.model.level.Level;
import game.model.user.User;
import game.model.user.UserMethods;

public class MenuPanel extends JPanel {

    private Menu menu;
    private BufferedImage menuImage;
    private UserPanel currentUserPanel;
    private JPopupMenu userSelectionPopUp;

    public MenuPanel(Menu menu) {
        this.menu = menu;
        setSize(new Dimension((int) (Level.GAME_WIDTH * LevelPanel.SCALE),
                (int) (Level.GAME_HEIGHT * LevelPanel.SCALE)));
        loadImage(); // Load the image once
        initMenu();
    }

    private void loadImage() {
        menuImage = ImageLoader.importImg("/MenuScreen.png");
    }

    private void initMenu() {
        setLayout(null);

        JButton playButton = new JButton("Play") {
            {
                setBounds(300, 400, 100, 50);
                addActionListener(e -> menu.startGame());
            }
        };

        JButton editorButton = new JButton() {
            {
                setBounds(610, 20, 100, 50);
                addActionListener(e -> editor.controller.Main.main(null));
                ImageIcon editorButtonImageIcon = new ImageIcon(
                        ImageLoader.importImg("/editor.png").getScaledInstance(100, 50, Image.SCALE_SMOOTH));
                setIcon(editorButtonImageIcon);
                setContentAreaFilled(false);
                setBorderPainted(false);
                setFocusPainted(false);
            }
        };

        JButton leaderboardButton = new JButton() {
            {
                setBounds(610, 70, 100, 50);
                addActionListener(e -> showLeaderboard());
                ImageIcon editorButtonImageIcon = new ImageIcon(
                        ImageLoader.importImg("/leaderboard.png").getScaledInstance(100, 50, Image.SCALE_SMOOTH));
                setIcon(editorButtonImageIcon);
                setContentAreaFilled(false);
                setBorderPainted(false);
                setFocusPainted(false);
            }
        };

        String lastUser = UserMethods.getLastUser("resources/last_user.txt");
        User firstUser = Model.getInstance().getUserByNickname(lastUser);
        Model.getInstance().setCurrentUser(firstUser);
        if (firstUser != null) {
            currentUserPanel = new UserPanel(firstUser) {
                {
                    setBounds(10, 20, 100, 120);
                    getUserButton().setBorderPainted(false); // Remove the button border
                    getUserButton().setFocusPainted(false); // Remove the focus border
                    getUserButton().addActionListener((e -> {}));	
                }
            };
            add(currentUserPanel);
        }

        userSelectionPopUp = new JPopupMenu();
        updateUserSelectionPopUp();

        JButton userSelectionButton = new JButton() {
            {
                setBounds(10, 140, 100, 50);
                addActionListener(e -> userSelectionPopUp.show(this, 0, this.getHeight()));
                ImageIcon editorButtonImageIcon = new ImageIcon(
                        ImageLoader.importImg("/userSelection.png").getScaledInstance(100, 50, Image.SCALE_SMOOTH));
                setIcon(editorButtonImageIcon);
                setContentAreaFilled(false);
                setBorderPainted(false);
                setFocusPainted(false);
            }
        };

        add(playButton);
        add(editorButton);
        add(leaderboardButton);
        add(userSelectionButton);
    }

    private void updateUserSelectionPopUp() {
        userSelectionPopUp.removeAll(); // Rimuovi gli elementi esistenti
        JPanel userPanelContainer = new JPanel();
        userPanelContainer.setLayout(new GridLayout(0, 1));
        Model.getInstance().getUsers().forEach(user -> {
            UserPanel userPanel = new UserPanel(user);
            userPanelContainer.add(userPanel);

            userPanel.getUserButton().addActionListener(e -> {
                updateCurrentUserPanel(user);
                userSelectionPopUp.setVisible(false);
            });
        });

        JButton newUserButton = new JButton() {
            {
                setPreferredSize(new Dimension(100, 20));
                setIcon(new ImageIcon(ImageLoader.importImg("/newUser.png")
                        .getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                setBackground(Color.YELLOW);
                setForeground(Color.MAGENTA);
                setFocusPainted(false);
                setContentAreaFilled(true);
                addActionListener(e -> showNewUserDialog());
            }
        };

        userPanelContainer.add(newUserButton);
        userSelectionPopUp.add(new JScrollPane(userPanelContainer));
        userSelectionPopUp.setPreferredSize(new Dimension(120, 300));
    }

    private void updateCurrentUserPanel(User user) {
        if (currentUserPanel != null) {
            remove(currentUserPanel);
        }
        currentUserPanel = new UserPanel(user) {
            {
                setBounds(10, 20, 100, 120);
                getUserButton().setBorderPainted(false); // Remove the button border
                getUserButton().setFocusPainted(false); // Remove the focus border
                getUserButton().addActionListener((e -> {}));		
            }
        };
        add(currentUserPanel);
        revalidate();
        repaint();
    }

    private void showLeaderboard() {
        JFrame leaderboardFrame = new JFrame("Leaderboard");
        LeaderboardPanel leaderboardPanel = new LeaderboardPanel();
        leaderboardFrame.add(leaderboardPanel);
        leaderboardFrame.setSize(450, 300);
        leaderboardFrame.setLocationRelativeTo(null);
        leaderboardFrame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (menuImage != null) {
            g.drawImage(menuImage, 0, 0, getWidth(), getHeight(), null);
        }
    }
    
    private void showNewUserDialog() {
        JTextField nicknameField = new JTextField(10);
        JButton chooseAvatarButton = new JButton("Scegli Avatar");
        JLabel avatarPreviewLabel = new JLabel();
        String defaultAvatarPath = "resources/usersicons/default.png";
        
        // Selezione dell'avatar
        chooseAvatarButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleziona un Avatar");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Immagini", "png", "jpg", "jpeg"));
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String selectedAvatarPath = fileChooser.getSelectedFile().getPath();
                try {
                    BufferedImage avatar = ImageIO.read(Paths.get(selectedAvatarPath).toFile());
                    ImageIcon avatarIcon = new ImageIcon(avatar.getScaledInstance(40, 40, Image.SCALE_SMOOTH)); 
                    avatarPreviewLabel.setIcon(avatarIcon);
                    avatarPreviewLabel.putClientProperty("avatarPath", selectedAvatarPath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Errore durante il caricamento dell'avatar!", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel dialogPanel = new JPanel(new GridLayout(0, 5));
        dialogPanel.add(new JLabel("Nickname:"));
        dialogPanel.add(nicknameField);
        dialogPanel.add(chooseAvatarButton);
        dialogPanel.add(avatarPreviewLabel);

        int result = JOptionPane.showConfirmDialog(null, dialogPanel, "Crea Nuovo Utente", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String nickname = nicknameField.getText();
            String selectedAvatarPath = (String) avatarPreviewLabel.getClientProperty("avatarPath");

            if (!nickname.isEmpty()) {
                if (selectedAvatarPath == null) selectedAvatarPath = defaultAvatarPath;
                String avatarPath = "resources/users/" + nickname + ".png";
                try {
                    Files.copy(Paths.get(selectedAvatarPath), Paths.get(avatarPath));
                } catch (IOException e) {
                    System.err.println("Errore durante la copia del file: " + e.getMessage());
                }
                User newUser = new User(nickname, 0, avatarPath,0,0,0);
                Model.getInstance().addUser(newUser);
                Model.getInstance().setCurrentUser(newUser);
                updateCurrentUserPanel(newUser);
                UserMethods.saveUsersData(nickname, 0, 0, 0, 0);
                
                // Aggiorna il pop-up dopo aver aggiunto un nuovo utente
                updateUserSelectionPopUp();
            } else {
                JOptionPane.showMessageDialog(this, "Nickname e Avatar sono obbligatori!", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
