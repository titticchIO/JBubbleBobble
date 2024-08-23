package game.model.powerups;

public class OrangeParasol extends Parasol {

	private static int spawnCondition = 2;

	public OrangeParasol() {
		super(0, 0, 3);
	}

	public OrangeParasol(float x, float y) {
		super(x, y, 3);
	}

	public static int getSpawnCondition() {
		return spawnCondition;
	}

}
