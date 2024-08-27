package game.model.bubbles.special_effects;

import java.util.Optional;

import game.model.Model;
import game.model.enemies.Enemy;
import game.model.entities.Entity;
import game.model.entities.MovingEntity;
import game.model.entities.Player;
import game.model.level.Level;
import game.model.tiles.Tile;

public class Bolt extends MovingEntity {
	
	public static final float BOLT_SPEED = 2;
	public static final char CODE = '?';

	public Bolt(float x, float y) {
		super(x, y, Tile.TILE_SIZE-1, Tile.TILE_SIZE-1, CODE);
		setBoltXSpeed();
	}
	
	private void setBoltXSpeed() {
		if(Player.getInstance().getDirection() == Player.Direction.LEFT)
			setxSpeed(BOLT_SPEED);
		else
			setxSpeed(-BOLT_SPEED);
	}
	
	@Override
	public void updateXPos() {
		if (x + xSpeed > Tile.TILE_SIZE && x + xSpeed < Level.GAME_WIDTH-2*Tile.TILE_SIZE) {
			setX(x + xSpeed);
		} else {
			Model.getInstance().getCurrentLevel().getBubbleManager().removeBolt(this);
		}
	}
	
	public void killEnemy() {
		Optional<Enemy> enemy = Entity.checkCollision(this, Model.getInstance().getCurrentLevel().getEnemyManager().getEnemies());
		if (enemy.isPresent()) {
			Model.getInstance().getCurrentLevel().getEnemyManager().removeEnemy(enemy.get());
		}
	}
	
	
	@Override
	public void updateEntity() {
		updateXPos();
		killEnemy();
	}

}
