package game.model;

public class User {

	private String nickname;
	private int highScore;
	private String avatarPath;
	private int points;
	private int playedGames;
	private int gamesWon;
	private int gamesLost;

	public User(String nickname, int highScore, String avatarPath, int playedGames, int gamesWon, int gamesLost) {
		this.nickname = nickname;
		this.highScore = highScore;
		this.avatarPath = avatarPath;
		this.playedGames = playedGames;
		this.gamesWon = gamesWon;
		this.gamesLost = gamesLost;
	}

	public int getPlayedGames() {
		return playedGames;
	}

	public void setPlayedGames(int playedGames) {
		this.playedGames = playedGames;
	}

	public int getGamesWon() {
		return gamesWon;
	}

	public void setGamesWon(int gamesWon) {
		this.gamesWon = gamesWon;
	}

	public int getGamesLost() {
		return gamesLost;
	}

	public void setGamesLost(int gamesLost) {
		this.gamesLost = gamesLost;
	}

	public String getAvatarPath() {
		return avatarPath;
	}

	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	public int getPoints() {
		return points;
	}

	public void addPoints(int pointsAmount) {
		points += pointsAmount;
		if (points > highScore) {
			setHighScore(points);
			UserMethods.saveUsersData(nickname, highScore, playedGames, gamesWon, gamesLost);

		}
	}

	public void addLostGame() {
		gamesLost += 1;
		playedGames += 1;
		UserMethods.saveUsersData(nickname, highScore, playedGames, gamesWon, gamesLost);
	}

	public void addWonGame() {
		gamesWon += 1;
		playedGames += 1;
		UserMethods.saveUsersData(nickname, highScore, playedGames, gamesWon, gamesLost);
	}

	public void setPoints(int points) {
		this.points = points;
	}

}
