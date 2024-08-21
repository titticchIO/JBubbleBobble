package game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import editor.model.LevelReader;
import game.model.entities.Player;
import game.model.level.Level;
import game.model.tiles.Tile;

public class Model extends Observable {

	public enum ModelState {
		PLAY, WIN, LOSS
	}

	private List<Level> levels;
	private Iterator<Level> levelIterator;
	private Level currentLevel;
	private List<User> users;
	private User currentUser;

	private long score;
	private long highScore;

	private static Model instance;

	private ModelState state;

	// Singleton pattern for getting Model instance
	public static Model getInstance() {
		if (instance == null)
			instance = new Model();
		return instance;
	}

	private Model() {
		levels = new ArrayList<>();
		users = new ArrayList<>();
		loadUsers();
		

		state = ModelState.PLAY;
		setChanged();
		notifyObservers();
	}

	// Reset the Model to its initial state
	public void resetModel() {
		levels = new ArrayList<>(); // Clear levels list
		currentLevel = null; // Reset current level
		loadLevels(); // Reload levels
		levelIterator = levels.iterator(); // Reset iterator
		if (levelIterator.hasNext()) {
			currentLevel = levelIterator.next();
		}
		score = 0; // Reset score
		state = ModelState.PLAY; // Reset game state to play
		Player.getInstance().setLives(Player.NUMBER_OF_LIVES);
		setChanged();
		notifyObservers(currentLevel); // Notify observers of the reset
	}

	public Level getCurrentLevel() {
		return currentLevel;
	}

	// Load levels from external source
	public void loadLevels() {
		LevelReader.getLevels().forEach(s -> {
			levels.add(new Level(Integer.parseInt(s)));
		});
		currentLevel = levels.getFirst();
		currentLevel.addPlayer(Player.getInstance(currentLevel.getPlayerSpawnPoint()[0],
				currentLevel.getPlayerSpawnPoint()[1], Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1));

		levelIterator = levels.iterator();
		state = ModelState.PLAY;
		setChanged();
		notifyObservers();
	}

	public void nextLevel() {
		currentLevel = levelIterator.next();
		Player.getInstance().setPosition(currentLevel.getPlayerSpawnPoint()[0], currentLevel.getPlayerSpawnPoint()[1]);
		currentLevel.addPlayer(Player.getInstance());
		setChanged();
		notifyObservers("next");
	}

	public void updateModel() {
		currentLevel.updateLevel(); // Update the current level logic
		updatePoints(); // Update points based on the current state
		if (currentLevel.getPlayer().getLives() == 0) {
			state = ModelState.LOSS; // Set game state to LOSS if player is out of lives
		} else if (currentLevel.getEnemyManager().getEnemies().isEmpty()
				&& currentLevel.getBubbleManager().getPlayerBubbles().stream().allMatch(b -> !b.hasEnemy())) {
			if (levelIterator.hasNext()) {
				nextLevel(); // Proceed to the next level if all enemies are cleared
			} else {
				state = ModelState.WIN; // Set game state to WIN if no more levels
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
	}

	public ModelState getModelState() {
		return state;
	}

	public void setState(ModelState state) {
		this.state = state;
	}

	public void addUser(User user) {
		users.add(user);
		setChanged();
		notifyObservers();
	}

	private void loadUsers() {
		HashMap<String, Integer> userPointsMap = UserMethods.getUsersPoints();
		for (Map.Entry<String, Integer> entry : userPointsMap.entrySet()) {
			String nickname = entry.getKey();
			Integer points = entry.getValue();
			String avatarPath = "resources/users/" + nickname + ".png";
			User user = new User(nickname, points, avatarPath);
			users.add(user); // Add the user to the list
		}
	}
}
