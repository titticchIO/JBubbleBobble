package game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import editor.model.LevelReader;
import game.model.entities.Player;
import game.model.level.Level;

import game.model.tiles.Tile;

import game.model.user.User;
import game.model.user.UserMethods;

/**
 * The Model class represents the core game model and follows the Singleton
 * pattern. It handles game states, level progression, user management, and
 * notifications to observers. This class is used to manage the game's state,
 * update levels, switch between levels, and store user data.
 * 
 * It extends Observable, allowing it to notify the observers of state changes.
 */
public class Model extends Observable {
	/**
	 * Enum representing the possible states of the game.
	 */
	public enum ModelState {
		PLAY, WIN, LOSS
	}

	private static Model instance;

	private Timer nextLevelTimer; // timer to delay the start of the next level
	private List<Level> levels; // the list of levels loaded in the model
	private Iterator<Level> levelIterator;
	private Level currentLevel;
	private List<User> users;
	private User currentUser;
	private boolean toUpdate = true;
	private ModelState modelState; // describes the current state of the Model

	/**
	 * Returns the singleton instance of the Model.
	 * 
	 * @return The singleton instance of the Model.
	 */
	public static Model getInstance() {
		if (instance == null)
			instance = new Model();
		return instance;
	}

	/**
	 * Private constructor for Model to implement Singleton pattern. Initializes the
	 * users and sets the game state to PLAY.
	 */
	private Model() {
		users = new ArrayList<>();
		loadUsers();
		// setCurrentUser(users.getFirst());
		modelState = ModelState.PLAY;
		setChanged();
		notifyObservers();
	}

	/**
	 * Resets the model to its initial state by clearing levels and notifying
	 * observers.
	 */
	public void resetModel() {
		levels = null;
		levelIterator = null;
		setChanged();
		notifyObservers(currentLevel); // Notify observers of the reset
	}

	/**
	 * Retrieves the current level.
	 * 
	 * @return The current level.
	 */
	public Level getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * Retrieves the list of levels.
	 * 
	 * @return The list of levels.
	 */
	public List<Level> getLevels() {
		return levels;
	}

	/**
	 * Retrieves the current state of the game.
	 * 
	 * @return The current ModelState (PLAY, WIN, LOSS).
	 */
	public ModelState getModelState() {
		return modelState;
	}

	/**
	 * Retrieves the list of users.
	 * 
	 * @return The list of users.
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * Retrieves the current user.
	 * 
	 * @return The current user.
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Retrieves a user by their nickname.
	 * 
	 * @param selectedNickname The nickname of the user to retrieve.
	 * @return The user with the specified nickname, or null if not found.
	 */
	public User getUserByNickname(String selectedNickname) {
		for (User user : users) { // 'users' è la lista di utenti
			if (user.getNickname().equals(selectedNickname)) {
				return user;
			}
		}
		return null;
	}

	public boolean isToUpdate() {
		return toUpdate;
	}

	/**
	 * Sets the game state to WIN and updates the user's won games counter.
	 */
	public void setWin() {
		modelState = ModelState.WIN;
		currentUser.addWonGame();// Set game state to WIN if no more levels
	}

	/**
	 * Sets the game state to the specified state.
	 * 
	 * @param state The state to set (PLAY, WIN, LOSS).
	 */
	public void setModelState(ModelState state) {
		this.modelState = state;
	}

	/**
	 * Sets the current user to the specified user.
	 * 
	 * @param currentUser The user to set as the current user.
	 */
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
		UserMethods.saveLastUser(currentUser);
	}

	public void setToUpdate(boolean toUpdate) {
		this.toUpdate = toUpdate;
	}

	/**
	 * Sets the current user by the user's nickname.
	 * 
	 * @param selectedNickname The nickname of the user to set as current.
	 */
	public void setCurrentUserByNickname(String selectedNickname) {
		for (User user : users) { // 'users' è la lista di utenti
			if (user.getNickname().equals(selectedNickname)) {
				currentUser = user;
				break; // Uscire dal ciclo una volta trovato l'utente
			}
		}
	}

	/**
	 * Adds a new user to the list of users and notifies observers.
	 * 
	 * @param user The user to add.
	 */
	public void addUser(User user) {
		users.add(user);
		setChanged();
		notifyObservers();
	}

	/**
	 * Loads the levels from the {@code LevelReader} and initializes the player.
	 * Resets the player's position and lives.
	 */
	public void loadLevels() {
		levels = new ArrayList<>();
		levelIterator = levels.iterator();
		currentUser.setPoints(0); // Reset score
		LevelReader.getLevels().forEach(s -> {
			levels.add(new Level(Integer.parseInt(s)));
		});
		currentLevel = levels.getFirst();
		if (Player.getInstance() == null) {
			currentLevel.addPlayer(Player.getInstance(currentLevel.getPlayerSpawnPoint()[0],
					currentLevel.getPlayerSpawnPoint()[1], Tile.TILE_SIZE - 2, Tile.TILE_SIZE - 1));
		} else {
			currentLevel.addPlayer(Player.getInstance());
			Player.getInstance().setPosition(currentLevel.getPlayerSpawnPoint()[0],
					currentLevel.getPlayerSpawnPoint()[1]);
		}
		Player.getInstance().setLives(Player.NUMBER_OF_LIVES);
		Player.getInstance().stop();
		levelIterator = levels.iterator();
		levelIterator.next();
		modelState = ModelState.PLAY;
		setChanged();
		notifyObservers();
	}

	/**
	 * Proceeds to the next level by transitioning to it and updating the player's
	 * position. Notifies observers of the transition.
	 */
	public void nextLevel() {
		nextLevelTransition();
		currentLevel = levelIterator.next();
		Player.getInstance().setPosition(currentLevel.getPlayerSpawnPoint()[0], currentLevel.getPlayerSpawnPoint()[1]);
		Player.getInstance().setStunned(false);
		Player.getInstance().setInvulnerable(false);
		currentLevel.addPlayer(Player.getInstance());
		setChanged();
		notifyObservers("next");

	}

	/**
	 * Handles the transition to the next level by pausing updates and notifying
	 * observers.
	 */
	public void nextLevelTransition() {
		toUpdate = false;
		setChanged();
		notifyObservers("transition");
	}

	/**
	 * Updates the model, advancing the current level and checking for game win/loss
	 * conditions. Notifies observers of the state changes.
	 */
	public void updateModel() {
		if (toUpdate) {
			currentLevel.updateLevel(); // Update the current level logic
			updatePoints(); // Update points based on the current state
		}
		if (currentLevel.getPlayer().getLives() == 0) {
			modelState = ModelState.LOSS; // Set game state to LOSS if player is out of lives
			currentUser.addLostGame();
		} else if (currentLevel.getEnemyManager().getEnemies().isEmpty()
				&& currentLevel.getBubbleManager().getPlayerBubbles().stream().allMatch(b -> !b.hasEnemy())) {
			if (levelIterator.hasNext()) {
				if (nextLevelTimer == null) {
					nextLevelTimer = new Timer();
					nextLevelTimer.schedule(new TimerTask() {

						@Override
						public void run() {
							nextLevel(); // Proceed to the next level if all enemies are cleared
							nextLevelTimer.cancel();
							nextLevelTimer = null;
						}
					}, 5000);

				}

			} else {
				setWin();
			}
		}
		setChanged();
		notifyObservers(); // Notify observers about the current state
	}

	private void updatePoints() {
		if (currentUser != null) {
			setChanged();
			notifyObservers("points"); // Notify observers about the points update
		}
	}

	/**
	 * Loads users from the user data file and initializes the users list.
	 */
	private void loadUsers() {
		HashMap<String, List<Integer>> mappaUtenti = UserMethods.getUsersData();

		// Itera attraverso la mappa per creare e aggiungere utenti
		for (Entry<String, List<Integer>> entry : mappaUtenti.entrySet()) {
			String nickname = entry.getKey();
			List<Integer> dataList = entry.getValue();

			// Assegna i valori della lista ai corrispondenti attributi dell'utente
			int highScore = dataList.get(0);
			int playedGames = dataList.get(1);
			int wonGames = dataList.get(2);
			int lostGames = dataList.get(3);

			String avatarPath = "resources/users/" + nickname + ".png";

			// Crea un nuovo utente con le informazioni raccolte
			User user = new User(nickname, highScore, avatarPath, playedGames, wonGames, lostGames);

			// Aggiungi l'utente alla lista degli utenti
			users.add(user);
		}
	}

	/**
	 * Sends a notification to observers with the specified argument.
	 * 
	 * @param arg The argument to pass to the observers.
	 */
	public void sendNotification(Object arg) {
		setChanged();
		notifyObservers(arg);
	}

}
