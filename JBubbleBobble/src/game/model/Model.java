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

public class Model extends Observable {

	public enum ModelState {
		PLAY, WIN, LOSS
	}

	private Timer nextLevelTimer;

	private List<Level> levels;
	private Iterator<Level> levelIterator;
	private Level currentLevel;
	private List<User> users;
	private User currentUser;
	private boolean toUpdate = true;

	/*
	 * private long score; private long highScore;
	 */

	public boolean isToUpdate() {
		return toUpdate;
	}

	public void setToUpdate(boolean toUpdate) {
		this.toUpdate = toUpdate;
	}

	private static Model instance;

	private ModelState modelState;

	// Singleton pattern for getting Model instance
	public static Model getInstance() {
		if (instance == null)
			instance = new Model();
		return instance;
	}

	private Model() {

		users = new ArrayList<>();
		loadUsers();
		// setCurrentUser(users.getFirst());
		modelState = ModelState.PLAY;
		setChanged();
		notifyObservers();
	}

	// Reset the Model to its initial state
	public void resetModel() {
		levels = null;
		levelIterator = null;
		setChanged();
		notifyObservers(currentLevel); // Notify observers of the reset
	}

	public Level getCurrentLevel() {
		return currentLevel;
	}

	public List<Level> getLevels() {
		return levels;
	}

	// Load levels from external source
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

	public void nextLevelTransition() {
		toUpdate = false;
		setChanged();
		notifyObservers("transition");
	}

	public void setWin() {
		modelState = ModelState.WIN;
		currentUser.addWonGame();// Set game state to WIN if no more levels
	}

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
							nextLevelTimer=null;
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

	public List<User> getUsers() {
		return users;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
		UserMethods.saveLastUser(currentUser);

	}

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

	public void setCurrentUserByNickname(String selectedNickname) {
		for (User user : users) { // 'users' è la lista di utenti
			if (user.getNickname().equals(selectedNickname)) {
				currentUser = user;
				break; // Uscire dal ciclo una volta trovato l'utente
			}
		}
	}

	public User getUserByNickname(String selectedNickname) {
		for (User user : users) { // 'users' è la lista di utenti
			if (user.getNickname().equals(selectedNickname)) {
				return user;
			}
		}
		return null;
	}

	public ModelState getModelState() {
		return modelState;
	}

	public void setModelState(ModelState state) {
		this.modelState = state;
	}

	public void addUser(User user) {
		users.add(user);
		setChanged();
		notifyObservers();
	}

	public void sendNotification(Object arg) {
		setChanged();
		notifyObservers(arg);
	}

	public void sendNotification() {
		setChanged();
		notifyObservers();
	}
}
