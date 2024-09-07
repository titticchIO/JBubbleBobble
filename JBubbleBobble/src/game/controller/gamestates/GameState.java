package game.controller.gamestates;

/**
 * The {@code GameState} class is used to set the current state of the game and
 * communicate it to the controller.
 */
public enum GameState {
	PLAYING, MENU, WIN, LOSS, PAUSE;
	
	public static GameState state = MENU; //the current state of the game
}
