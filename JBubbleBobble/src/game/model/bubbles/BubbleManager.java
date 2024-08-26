package game.model.bubbles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import game.model.Model;
import game.model.bubbles.special_effects.Bolt;
import game.model.bubbles.special_effects.FireBall;
import game.model.bubbles.special_effects.Water;
import game.model.entities.Player;
import game.model.tiles.Tile;

public class BubbleManager {
	private List<Bubble> specialBubbles;
	private List<PlayerBubble> playerBubbles;
	private List<FireBall> fireBalls;
	private List<Bolt> bolts;
	private List<Water> waters;
	private Timer waterUpdateTimer;

	private boolean doOnce = true;

	public BubbleManager() {
		specialBubbles = new CopyOnWriteArrayList<>();
		playerBubbles = new CopyOnWriteArrayList<>();
		fireBalls = new CopyOnWriteArrayList<>();
		waters = new CopyOnWriteArrayList<>();
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
		if (doOnce) {
			doOnce = false;
			Bubble waterBubble = new SpecialBubble();
			Model.getInstance().getCurrentLevel().spawnBubble(waterBubble);
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

	public void addWater(Water water) {
		waters.add(water);
	}

	public void removeWater(Water water) {
		waters.remove(water);
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

	public List<Water> getWaters() {
		return waters;
	}

	public void updateBubbles() {
		specialBubbles.forEach(b -> b.updateEntity());
		playerBubbles.forEach(pb -> pb.updateEntity());
		fireBalls.forEach(f -> f.updateEntity());
		bolts.forEach(b -> b.updateEntity());
		if (waterUpdateTimer == null && waters.size() != 0) {
			waterUpdateTimer = new Timer("Water Update");
			waterUpdateTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					waters.forEach(w -> w.updateEntity());
					waterUpdateTimer = null;
				}
			}, 50);
		}
		createSpecialBubble();
	}
}
