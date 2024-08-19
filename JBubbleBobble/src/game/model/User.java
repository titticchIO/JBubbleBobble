package game.model;

public class User {
	
	private String nickname;
	private Integer highScore;
	private String avatarPath;
	
	public User(String nickname, Integer highScore, String avatarPath) {
		this.nickname = nickname;
		this.highScore = highScore;
		this.avatarPath = avatarPath;
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

	

	public Integer getHighScore() {
		return highScore;
	}

	public void setHighScore(Integer highScore) {
		this.highScore = highScore;
	}

}
