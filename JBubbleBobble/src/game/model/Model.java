package game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;

import editor.model.LevelReader;
import game.model.level.Level;
import game.model.user.User;
import game.model.user.UserMethods;

public class Model extends Observable {
	
	
	public enum State {
		PLAY,
		GAME_OVER,
		WIN
	}
	

    private List<Level> levels;
    private Iterator<Level> levelIterator;
    private Level currentLevel;
    private List<User> users;
    private User currentUser;


    private long score;
    private long highScore;

	private static Model instance;
	
	private State state;

    public static Model getInstance() {
        if (instance == null)
            instance = new Model();
        return instance;
    }

	private Model() {
		levels = new ArrayList<Level>();
		users = new ArrayList<>();
		loadUsers();
		setChanged();
		notifyObservers(currentLevel);
		loadLevels();
		levelIterator = levels.iterator();
	}

	public Level getCurrentLevel() {
		return currentLevel;

	}

	public void loadLevels() {

		LevelReader.getLevels().forEach(s -> {
			levels.add(new Level(Integer.parseInt(s)));
		});
		currentLevel = levels.getFirst();

		//AGGIUNGI QUA IL POWERUP
//		currentLevel.addPowerup(null);

		
		state = State.PLAY;
		
		setChanged();
		notifyObservers(currentLevel);
	}

	public void nextLevel() {
		currentLevel = levelIterator.next();
		setChanged();
		notifyObservers("next");
	}

	public void resetLevels() {
		levels = new ArrayList<Level>();
		loadLevels();
		levelIterator = levels.iterator();
	}

	public void updateModel() {
		currentLevel.updateLevel();
		updatePoints();
		
		if (currentLevel.getEnemyManager().getEnemies().size() == 0
				&& currentLevel.getBubbleManager().getPlayerBubbles().stream().allMatch(b -> !b.hasEnemy()))
			
			try {
				nextLevel();
			} catch (Exception e) {
				state = State.WIN;
			}

		setChanged();
		notifyObservers(currentLevel);
		
	}
	
	private void updatePoints() {
		if (currentUser != null) {
			setChanged();
			notifyObservers("points");
		}
	}
   

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
        setChanged();
        notifyObservers();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        //setChanged();
        //notifyObservers(currentUser);
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}
