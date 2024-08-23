package game.model.powerups;

public class PurpleParasol extends Parasol {
	private static int spawnCondition = 10;

	public PurpleParasol() {
		super(0, 0, 7);
	}

	public PurpleParasol(float x, float y) {
		super(x, y, 7);
	}

	public static int getSpawnCondition() {
		return spawnCondition;
	}

}
