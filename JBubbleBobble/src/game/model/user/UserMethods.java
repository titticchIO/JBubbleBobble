package game.model.user;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserMethods {
	
	public static HashMap<String, List<Integer>> getUsersData() {
	    String directoryPath = "resources/users/";
	    HashMap<String, List<Integer>> usersPoints = new HashMap<>();
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
	        if (file.isFile() && file.getName().endsWith(".txt")) {
	            try {
	                // Legge tutto il contenuto del file come una lista di stringhe
	                List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()));
	                
	                // Converti ogni riga in un numero intero e aggiungila alla lista
	                List<Integer> pointsList = new ArrayList<>();
	                for (String line : lines) {
	                    try {
	                        int points = Integer.parseInt(line.trim());
	                        pointsList.add(points);
	                    } catch (NumberFormatException e) {
	                        System.out.println("Formato non valido nel file: " + file.getName() + " sulla riga: " + line);
	                        e.printStackTrace();
	                    }
	                }

	                if (!pointsList.isEmpty()) {
	                    String user = file.getName().split("\\.")[0];
	                    usersPoints.put(user, pointsList);
	                }

	            } catch (IOException e) {
	                System.out.println("Errore nella lettura del file: " + file.getName());
	                e.printStackTrace();
	            }
	        }
	    }
	    return usersPoints;
	}

    
	public static void saveUsersData(String nickname, int highScore, int gamesPlayed, int gamesWon, int gamesLost) {
	    String directoryPath = "resources/users/";
	    File directory = new File(directoryPath);

	    // Verifica e crea la directory se non esiste
	    if (!directory.exists()) {
	        directory.mkdirs();
	    }

	    File userFile = new File(directoryPath + nickname + ".txt");

	    try {
	        // Scrivi sempre tutti i dati (highScore, gamesWon, gamesLost, gamesPlayed)
	        try (FileWriter writer = new FileWriter(userFile)) {
	            writer.write(highScore + "\n");
	            writer.write(gamesPlayed + "\n");
	            writer.write(gamesWon + "\n");
	            writer.write(gamesLost + "\n");
	            
	        }
	    } catch (IOException e) {
	        System.out.println("Errore nella scrittura del file: " + userFile.getName());
	        e.printStackTrace();
	    }
	}

    

}
