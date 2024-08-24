package game.model.bubbles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import game.model.Model;
import game.model.bubbles.special_effects.Bolt;
import game.model.bubbles.special_effects.FireBall;
import game.model.entities.Player;
import game.model.tiles.Tile;

public class BubbleManager {
	private List<Bubble> specialBubbles;
	private List<PlayerBubble> playerBubbles;
	private List<FireBall> fireBalls;
	private List<Bolt> bolts;

	

	private boolean doOnce = true;

	public BubbleManager() {
		specialBubbles = new CopyOnWriteArrayList<>();
		playerBubbles = new CopyOnWriteArrayList<>();
		fireBalls = new CopyOnWriteArrayList<>();
		bolts = new CopyOnWriteArrayList<>();
	}

	public void createBubble(float x, float y, float xSpeed) {
		Bubble newBubble = new PlayerBubble.Builder(x, y, Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1, "B1").xSpeed(xSpeed)
				.build();
		specialBubbles.add(newBubble);
	}

	public void createPlayerBubble(float x, float y, float xSpeed) {
		PlayerBubble newBubble = new PlayerBubble.Builder(x, y, Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1, "B1")
				.xSpeed(xSpeed).build();
		playerBubbles.add(newBubble);
	}

	public void createSpecialBubble() {
		Random random = new Random();

		if (doOnce) {
			doOnce = false;
			Bubble fireBubble = new ThunderBubble();
			Model.getInstance().getCurrentLevel().spawnBubble(fireBubble);
//			bubbles.add(fireBubble);
		}
	}

	public void addBubble(Bubble bubble) {
		specialBubbles.add(bubble);
	}

	public void removeBubble(Bubble bubble) {
		specialBubbles.remove(bubble);
	}

	public void removePlayerBubble(PlayerBubble playerBubble) {
		playerBubbles.remove(playerBubble);
	}

	public void addFireBall(FireBall fireBall) {
		fireBalls.add(fireBall);
	}

	public void removeFireBall(FireBall fireBall) {
		fireBalls.remove(fireBall);
	}
	
	public void addBolt(Bolt bolt) {
		bolts.add(bolt);
	}
	
	public void removeBolt(Bolt bolt) {
		bolts.remove(bolt);
	}

	public List<Bubble> getBubbles() {
		return specialBubbles;
	}

	public List<PlayerBubble> getPlayerBubbles() {
		return playerBubbles;
	}
	
	public List<FireBall> getFireBalls() {
		return fireBalls;
	}
	public List<Bolt> getBolts() {
		return bolts;
	}
	
	public void updateBubbles() {
		specialBubbles.forEach(b -> b.updateEntity());
		playerBubbles.forEach(pb -> pb.updateEntity());
		fireBalls.forEach(f->f.updateEntity());
		bolts.forEach(b -> b.updateEntity());
		createSpecialBubble();
	}
}
