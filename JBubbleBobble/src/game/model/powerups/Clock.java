package game.model.powerups;

import game.model.Model;
import game.model.bubbles.PlayerBubble;

public class Clock extends Powerup {
	public static final char CODE = '*';
	public static final int SPAWN_CONDITION = 3;
	public static final int POINTS = 200;
	public static final long DURATION = 6000;

	public Clock() {
		super(0, 0, CODE, POINTS, DURATION);
	}

	@Override
	public void effect() {
		setX(-200);
		Model.getInstance().getCurrentLevel().getEnemyManager().getEnemies().stream().forEach(x -> x.setStopped(true));
	}

	@Override
	public void resetToNormal() {
		Model.getInstance().getCurrentLevel().getEnemyManager().getEnemies().stream().forEach(x -> x.setStopped(false));
		Model.getInstance().getCurrentLevel().getBubbleManager().getPlayerBubbles().stream()
				.filter(PlayerBubble::hasEnemy).forEach(b -> b.getEnemy().setStopped(false));
	}

}
