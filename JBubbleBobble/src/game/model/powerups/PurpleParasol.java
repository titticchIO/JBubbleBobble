package game.model.powerups;

/**
 * The {@code PurpleParasol} item allows the player to skip 4 levels
 */
public class PurpleParasol extends Parasol {
	public static final int SPAWN_CONDITION = 4;

	public PurpleParasol() {
		super(0, 0, 4);
	}

}
