package model;

public class User {
	
	/**
	 * nickname utente
	 */
	private String nickname;
		
	/**
	 * password utente
	 */
	private String password;
	
	/**
	 * path assoluto avatar utente
	 */
	private String avatar;
	
	/**
	 * numero di partite giocate
	 */
	private int totGames;
	
	/**
	 * numero di partite vinte
	 */
	private int wonGames;
	
	/**
	 * numero di partite perse
	 */
	private int lostGames;
	
	/**
	 * livello corrente
	 */
	private int level;
	
	
	
	/**
	 * Costruttore privato richiamato dal metodo build del Builder,
	 * crea un nuovo User.
	 * 
	 * @param nickname 
	 * @param password
	 * @param avatar
	 * @param totGames
	 * @param wonGames
	 * @param lostGames
	 * @param level
	 */
	private User(String nickname, String password, String avatar, int totGames, int wonGames, int lostGames,
			int level) {
		this.nickname = nickname;
		this.password = password;
		this.avatar = avatar;
		this.totGames = totGames;
		this.wonGames = wonGames;
		this.lostGames = lostGames;
		this.level = level;
	}

	
	/**
	 * Metodi getter (eccetto campo password).
	 * 
	 * Non è previsto un getter della password in quanto 
	 * questa non deve essere visibile dalle altre classi.
	 */
	public String getNickname() {
		return nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public int getTotGames() {
		return totGames;
	}

	public int getWonGames() {
		return wonGames;
	}

	public int getLostGames() {
		return lostGames;
	}

	public int getLevel() {
		return level;
	}

	
	/**
	 * Controlla se i valori di nickname e password inseriti sonon coerenti con quelli dell'utente.
	 * 
	 * @param nickname 		il nickname inserito dall'utente
	 * @param password		la password inserita dall'utente
	 * @return 				un booleano che è true se la password e l'username inseriti conincidono con quelli dell User
	 */
	public boolean login(String nickname, String password) {
		return nickname.equals(this.nickname) && password.equals(this.password);
	}
	
	
	




	/**
	 * Classe Builder utilizzata per contruire oggetti di tipo User.
	 */
	
	public static class Builder {
		/**
		 * nickname utente
		 */
		private String nickname;
		
		/**
		 * password utente
		 */
		private String password;
		
		/**
		 * path assoluto avatar utente
		 */
		private String avatar;
		
		/**
		 * numero di partite giocate
		 */
		private int totGames;
		
		/**
		 * numero di partite vinte
		 */
		private int wonGames;
		
		/**
		 * numero di partite perse
		 */
		private int lostGames;
		
		/**
		 * livello corrente
		 */
		private int level;

		/**
		 * Costruttore del Builder. 
		 * E' obbligatoria solo la valorizzazione dei campi nickname e password.
		 * 
		 * @param nickname
		 * @param password
		 */
		public Builder(String nickname) {
			this.nickname = nickname;
		}
		
		
		
		/**
		 * Valorizza il campo password.
		 * 
		 * @param password
		 * @return 			l'oggetto di tipo Builder
		 */
		public Builder password(String password) {
			this.password = password;
			return this;
		}
		
		/**
		 * Valorizza il campo avatar.
		 * 
		 * @param avatar
		 * @return 			l'oggetto di tipo Builder
		 */
		public Builder avatar(String avatar) {
			this.avatar = avatar;
			return this;
		}
		
		/**
		 * Valorizza il campo totGames.
		 * 
		 * @param totGames
		 * @return 			l'oggetto di tipo Builder
		 */
		public Builder totGames(int totGames) {
			this.totGames = totGames;
			return this;
		}
		
		/**
		 * Valorizza il campo wonGames.
		 * 
		 * @param wonGames
		 * @return 			l'oggetto di tipo Builder
		 */
		public Builder wonGames(int wonGames) {
			this.wonGames = wonGames;
			return this;
		}
		
		/**
		 * Valorizza il campo lostGames.
		 * 
		 * @param lostGames
		 * @return 			l'oggetto di tipo Builder
		 */
		public Builder lostGames(int lostGames) {
			this.lostGames = lostGames;
			return this;
		}
		
		/**
		 * Valorizza il campo level.
		 * 
		 * @param level
		 * @return 			l'oggetto di tipo Builder
		 */
		public Builder level(int level) {
			this.level = level;
			return this;
		}
		
		/**
		 * Metodo che costruisce un'istanza di User.
		 * 
		 * @return		oggetto di tipo User
		 */
		
		public User build() {
			return new User(nickname, password, avatar, totGames, wonGames, lostGames, level);
		}
		
	}
}
