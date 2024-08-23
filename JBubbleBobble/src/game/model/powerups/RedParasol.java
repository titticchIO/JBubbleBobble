package game.model.powerups;

public class RedParasol extends Parasol{
	private static int spawnCondition = 5;

	public RedParasol() {
		super(0,0, 5);
	}
	public RedParasol(float x, float y) {
		super(x, y, 5);
	}

	public static int getSpawnCondition() {
		return spawnCondition;
	}
}
