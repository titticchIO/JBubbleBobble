package game.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class UserMethods {
	
    public static HashMap<String, Integer> getUsersPoints() {
    	String directoryPath = "resources/users/";
        HashMap<String, Integer> usersPoints = new HashMap<>();
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Directory non valida: " + directoryPath);
            return usersPoints;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("Errore nel leggere i file della directory: " + directoryPath);
            return usersPoints;
        }

        for (File file : files) {
            if (file.isFile() && file.getName().split("\\.")[1].equals("txt")) {
                try {
                    // Legge tutto il contenuto del file come una lista di stringhe
                    List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()));
                    
                    // Supponiamo che il file contenga solo un singolo numero intero
                    if (!lines.isEmpty()) {
                        int points = Integer.parseInt(lines.get(0).trim());
                        String user = file.getName().split("\\.")[0];                        
                        usersPoints.put(user, points);
                    }
                } catch (IOException e) {
                    System.out.println("Errore nella lettura del file: " + file.getName());
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    System.out.println("Formato non valido nel file: " + file.getName());
                    e.printStackTrace();
                }
            }
        }
        return usersPoints;
    }
    
    public static void saveUsersPoints(String nickname, Integer highScore) {
    	 String directoryPath = "resources/users/";
         File directory = new File(directoryPath);

         // Verifica e crea la directory se non esiste
         if (!directory.exists()) {
             directory.mkdirs();
         }

         File userFile = new File(directoryPath + nickname + ".txt");

         try {
             if (userFile.exists()) {
                 // Leggi il punteggio esistente e confronta
                 List<String> lines = Files.readAllLines(Paths.get(userFile.getAbsolutePath()));
                 if (!lines.isEmpty()) {
                     int existingScore = Integer.parseInt(lines.get(0).trim());
                     // Aggiorna solo se il nuovo punteggio è maggiore
                     if (highScore > existingScore) {
                         try (FileWriter writer = new FileWriter(userFile)) {
                             writer.write(highScore.toString());
                         }
                     }
                 } else {
                     // Se il file è vuoto, scrivi direttamente il nuovo punteggio
                     try (FileWriter writer = new FileWriter(userFile)) {
                         writer.write(highScore.toString());
                     }
                 }
             } else {
                 // Se il file non esiste, crealo e scrivi il punteggio
                 try (FileWriter writer = new FileWriter(userFile)) {
                     writer.write(highScore.toString());
                 }
             }
         } catch (IOException e) {
             System.out.println("Errore nella scrittura del file: " + userFile.getName());
             e.printStackTrace();
         } catch (NumberFormatException e) {
             System.out.println("Formato non valido nel file: " + userFile.getName());
             e.printStackTrace();
         }
     }
    

}
