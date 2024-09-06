package game.model.powerups;

/**
 * The {@code RedParasol} item allows the player to skip 3 levels
 */
public class RedParasol extends Parasol {
	public static final int SPAWN_CONDITION = 3;

	public RedParasol() {
		super(0, 0, 3);
	}

}
