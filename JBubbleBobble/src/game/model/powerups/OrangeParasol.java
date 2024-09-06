package game.model.powerups;

/**
 * The {@code OrangeParasol} item allows the player to skip 1 level.
 */
public class OrangeParasol extends Parasol {

	public static final int SPAWN_CONDITION = 2;

	public OrangeParasol() {
		super(0, 0, 1);
	}
}
