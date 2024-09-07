package game.model.user;

/**
 * The class {@code User} represents a user in the game with attributes such as
 * nickname, high score, avatar path, points, and statistics for played, won,
 * and lost games. The class provides methods to update these attributes and
 * tracks the user's progress.
 */
public class User {

	private String nickname;
	private int highScore;
	private String avatarPath;
	private int points;
	private int playedGames;
	private int gamesWon;
	private int gamesLost;

	/**
	 * Constructs a new User with the given attributes.
	 * 
	 * @param nickname    the user's nickname
	 * @param highScore   the user's highest score achieved
	 * @param avatarPath  the file path to the user's avatar
	 * @param playedGames the number of games the user has played
	 * @param gamesWon    the number of games the user has won
	 * @param gamesLost   the number of games the user has lost
	 */
	public User(String nickname, int highScore, String avatarPath, int playedGames, int gamesWon, int gamesLost) {
		this.nickname = nickname;
		this.highScore = highScore;
		this.avatarPath = avatarPath;
		this.playedGames = playedGames;
		this.gamesWon = gamesWon;
		this.gamesLost = gamesLost;
	}

	/**
	 * Gets the number of games the user has played.
	 * 
	 * @return the number of played games
	 */
	public int getPlayedGames() {
		return playedGames;
	}

	/**
	 * Sets the number of games the user has played.
	 * 
	 * @param playedGames the number of played games
	 */
	public void setPlayedGames(int playedGames) {
		this.playedGames = playedGames;
	}

	/**
	 * Gets the number of games the user has won.
	 * 
	 * @return the number of won games
	 */
	public int getGamesWon() {
		return gamesWon;
	}

	/**
	 * Sets the number of games the user has won.
	 * 
	 * @param gamesWon the number of won games
	 */
	public void setGamesWon(int gamesWon) {
		this.gamesWon = gamesWon;
	}

	/**
	 * Gets the number of games the user has lost.
	 * 
	 * @return the number of lost games
	 */
	public int getGamesLost() {
		return gamesLost;
	}

	/**
	 * Sets the number of games the user has lost.
	 * 
	 * @param gamesLost the number of lost games
	 */
	public void setGamesLost(int gamesLost) {
		this.gamesLost = gamesLost;
	}

	/**
	 * Gets the file path to the user's avatar image.
	 * 
	 * @return the avatar file path
	 */
	public String getAvatarPath() {
		return avatarPath;
	}

	/**
	 * Sets the file path to the user's avatar image.
	 * 
	 * @param avatarPath the avatar file path
	 */
	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}

	/**
	 * Gets the user's nickname.
	 * 
	 * @return the user's nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Sets the user's nickname.
	 * 
	 * @param nickname the new nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * Gets the user's highest score.
	 * 
	 * @return the high score
	 */
	public int getHighScore() {
		return highScore;
	}

	/**
	 * Sets the user's highest score.
	 * 
	 * @param highScore the new high score
	 */
	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	/**
	 * Gets the user's current points.
	 * 
	 * @return the current points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Adds a specified amount of points to the user's total points. If the total
	 * points exceed the user's high score, the high score is updated and the user's
	 * data is saved.
	 * 
	 * @param pointsAmount the amount of points to add
	 */
	public void addPoints(int pointsAmount) {
		points += pointsAmount;
		if (points > highScore) {
			setHighScore(points);
			UserMethods.saveUsersData(nickname, highScore, playedGames, gamesWon, gamesLost);

		}
	}

	/**
	 * Increments the number of lost games and played games by one, and saves the
	 * user's updated data.
	 */
	public void addLostGame() {
		gamesLost += 1;
		playedGames += 1;
		UserMethods.saveUsersData(nickname, highScore, playedGames, gamesWon, gamesLost);
	}

	/**
	 * Increments the number of won games and played games by one, and saves the
	 * user's updated data.
	 */
	public void addWonGame() {
		gamesWon += 1;
		playedGames += 1;
		UserMethods.saveUsersData(nickname, highScore, playedGames, gamesWon, gamesLost);
	}

	/**
	 * Sets the user's current points.
	 * 
	 * @param points the new points total
	 */
	public void setPoints(int points) {
		this.points = points;
	}

}
