package game.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import game.model.Model;
import game.model.user.User;
import game.model.user.UserMethods;

public class UserSelectionPanel extends JPanel {

    private ActionListener actionListener;
    private BufferedImage backgroundImage;

    public UserSelectionPanel(ActionListener actionListener) {
        this.actionListener = actionListener;
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Layout orizzontale con spaziatura tra i componenti
        
        // Carica l'immagine di sfondo
        try {
            backgroundImage = ImageIO.read(Paths.get("resources/MenuScreen.png").toFile());
        } catch (IOException e) {
            e.printStackTrace();
            backgroundImage = null;
        }

        // Recupera la lista degli utenti dal modello
        Model model = Model.getInstance();
        for (User user : model.getUsers()) {
            JPanel userPanel = new JPanel();
            userPanel.setOpaque(false); // Rendi il pannello trasparente
            userPanel.setLayout(new BorderLayout());
            
            // Carica l'avatar
            JLabel avatarLabel = new JLabel();
            try {
                BufferedImage avatar = ImageIO.read(Paths.get(user.getAvatarPath()).toFile());
                ImageIcon avatarIcon = new ImageIcon(avatar.getScaledInstance(80, 80, Image.SCALE_SMOOTH)); // Scala l'avatar
                avatarLabel.setIcon(avatarIcon);
            } catch (IOException e) {
                e.printStackTrace();
                avatarLabel.setText("No Avatar");
            }

            JButton userButton = new JButton(user.getNickname());
            userButton.addActionListener(e -> {
                // Imposta l'utente corrente nel Model
                Model.getInstance().setCurrentUser(user);
            });
            userButton.addActionListener(actionListener);
            userButton.setHorizontalTextPosition(JButton.CENTER);
            userButton.setVerticalTextPosition(JButton.BOTTOM);

            userPanel.add(avatarLabel, BorderLayout.CENTER);
            userPanel.add(userButton, BorderLayout.SOUTH);

            add(userPanel);
        }

        // Aggiungi un pulsante per creare un nuovo utente
        JButton newUserButton = new JButton("Crea Nuovo Utente");
        newUserButton.addActionListener(e -> showNewUserDialog());
        add(newUserButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
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
            	if(selectedAvatarPath == null) selectedAvatarPath = defaultAvatarPath;
                String avatarPath = "resources/users/" + nickname + ".png";
                try {
                    Files.copy(Paths.get(selectedAvatarPath), Paths.get(avatarPath));
                } catch (IOException e) {
                    System.err.println("Errore durante la copia del file: " + e.getMessage());
                }
                User newUser = new User(nickname, 0, avatarPath, 0, 0, 0);
                Model.getInstance().addUser(newUser);
                UserMethods.saveUsersData(nickname, 0, 0, 0, 0);
                refreshUserButtons();
            } else {
                JOptionPane.showMessageDialog(this, "Il nickname è obbligatorio!", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void refreshUserButtons() {
        removeAll();
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Disposizione orizzontale

        // Recupera la lista degli utenti dal modello
        Model model = Model.getInstance();
        for (User user : model.getUsers()) {
            JPanel userPanel = new JPanel();
            userPanel.setOpaque(false); // Rendi il pannello trasparente
            userPanel.setLayout(new BorderLayout());
            
            // Carica l'avatar
            JLabel avatarLabel = new JLabel();
            try {
                BufferedImage avatar = ImageIO.read(Paths.get(user.getAvatarPath()).toFile());
                ImageIcon avatarIcon = new ImageIcon(avatar.getScaledInstance(80, 80, Image.SCALE_SMOOTH)); // Scala l'avatar
                avatarLabel.setIcon(avatarIcon);
            } catch (IOException e) {
                e.printStackTrace();
                avatarLabel.setText("No Avatar");
            }

            JButton userButton = new JButton(user.getNickname());
            userButton.addActionListener(actionListener);
            userButton.setHorizontalTextPosition(JButton.CENTER);
            userButton.setVerticalTextPosition(JButton.BOTTOM);

            userPanel.add(avatarLabel, BorderLayout.CENTER);
            userPanel.add(userButton, BorderLayout.SOUTH);

            add(userPanel);
        }

        // Aggiungi un pulsante per creare un nuovo utente
        JButton newUserButton = new JButton("Crea Nuovo Utente");
        newUserButton.addActionListener(e -> showNewUserDialog());
        add(newUserButton);

        revalidate();
        repaint();
    }
}
