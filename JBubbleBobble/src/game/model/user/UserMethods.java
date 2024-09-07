package game.model.user;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The {@code UserMethods} class provides static methods for handling user data,
 * such as reading user scores from files and saving user information. It also
 * manages the last active user.
 */
public class UserMethods {

	/**
	 * Reads all user data from the directory "resources/users/" and returns it as a
	 * {@code HashMap}.
	 * 
	 * @return a {@code HashMap} where the keys are usernames and the values are
	 *         lists of integers representing the user's scores.
	 */
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
							System.out
									.println("Formato non valido nel file: " + file.getName() + " sulla riga: " + line);
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

	/**
	 * Saves user data such as high score, games played, games won, and games lost
	 * to a file.
	 * 
	 * @param nickname    the username to save data for.
	 * @param highScore   the user's high score.
	 * @param gamesPlayed the total number of games the user has played.
	 * @param gamesWon    the total number of games the user has won.
	 * @param gamesLost   the total number of games the user has lost.
	 */
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

	/**
	 * Saves the last user that was active.
	 * 
	 * @param user the {@code User} object representing the last active user.
	 */
	public static void saveLastUser(User user) {
		String directoryPath = "resources/";
		File directory = new File(directoryPath);

		// Verifica e crea la directory se non esiste
		if (!directory.exists()) {
			directory.mkdirs();
		}

		File lastUserFile = new File(directoryPath + "last_user.txt");

		try (FileWriter writer = new FileWriter(lastUserFile)) {
			writer.write(user.getNickname());
			writer.flush();
		} catch (IOException e) {
			System.out.println("Errore nella scrittura del file: " + lastUserFile.getName());
			e.printStackTrace();
		}
	}

	/**
	 * Reads and returns the nickname of the last active user from the specified
	 * file path.
	 * 
	 * @param path the path to the file that stores the last active user.
	 * @return the nickname of the last active user, or {@code null} if no user was
	 *         found.
	 */
	public static String getLastUser(String path) {
		String lastLine = null;

		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
				lastLine = currentLine;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lastLine;
	}

}
