package game.model.bubbles;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import game.model.HelpMethods;
import game.model.Model;
import game.model.bubbles.special_effects.Bolt;
import game.model.bubbles.special_effects.FireBall;
import game.model.bubbles.special_effects.Water;
import game.model.entities.Entity;
import game.model.entities.Player;
import game.model.tiles.Tile;

/**
 * The {@code BubbleManager} class manages the creation, updating, and removal
 * of various types of bubbles in the game, including player bubbles, fireballs,
 * bolts, and water bubbles.
 */
public class BubbleManager {

	// Instance Fields
	private List<Bubble> specialBubbles;
	private List<PlayerBubble> playerBubbles;
	private List<FireBall> fireBalls;
	private List<Bolt> bolts;
	private List<Water> waters;
	private Timer spawnSpecialBubbleTimer;
	private Timer waterUpdateTimer;

	// Constructor

	/**
	 * Constructs a new BubbleManager, initializing the lists for different types of
	 * bubbles.
	 */
	public BubbleManager() {
		specialBubbles = new CopyOnWriteArrayList<>();
		playerBubbles = new CopyOnWriteArrayList<>();
		fireBalls = new CopyOnWriteArrayList<>();
		waters = new CopyOnWriteArrayList<>();
		bolts = new CopyOnWriteArrayList<>();
	}

	// Methods for Creating Bubbles

	/**
	 * Creates a new player bubble and adds it to the player bubbles list.
	 *
	 * @param x      the x-coordinate of the bubble.
	 * @param y      the y-coordinate of the bubble.
	 * @param xSpeed the speed of the bubble in the x-direction.
	 */
	public void createPlayerBubble(float x, float y, float xSpeed) {
		PlayerBubble newBubble = new PlayerBubble.Builder(x, y, Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1).xSpeed(xSpeed)
				.build();
		playerBubbles.add(newBubble);
	}

	/**
	 * Creates a new special bubble and spawns it in the game.
	 */
	public void createSpecialBubble() {

		Bubble specialBubble = null;
		switch (new Random().nextInt(4)) {

		case 0 -> specialBubble = new FireBubble();
		case 1 -> specialBubble = new WaterBubble();
		case 2 -> specialBubble = new SpecialBubble();
		case 3 -> specialBubble = new ThunderBubble();
		}
		if (specialBubble != null)
			Model.getInstance().getCurrentLevel().spawnBubble(specialBubble);
	}

	public void createSpecialBubble(Bubble specialBubble) {
		if (specialBubble != null)
			Model.getInstance().getCurrentLevel().spawnBubble(specialBubble);
	}

	/**
	 * Creates and spawns an extend bubble in the game if multiple enemies are
	 * killed simultaneously.
	 */
	public void createExtendBubble() {
		if (Model.getInstance().getCurrentLevel().getSimultaneousKills() > 1) {
			Model.getInstance().getCurrentLevel().spawnBubble(new ExtendBubble());
			Model.getInstance().getCurrentLevel().setSimultaneousKills(0);
		}
	}

	// Methods for Managing Bubbles

	/**
	 * Adds a bubble to the list of special bubbles.
	 *
	 * @param bubble the bubble to add.
	 */
	public void addBubble(Bubble bubble) {
		specialBubbles.add(bubble);
	}

	/**
	 * Removes a bubble from the list of special bubbles.
	 *
	 * @param bubble the bubble to remove.
	 */
	public void removeBubble(Bubble bubble) {
		specialBubbles.remove(bubble);
	}

	/**
	 * Removes a player bubble from the list of player bubbles.
	 *
	 * @param playerBubble the player bubble to remove.
	 */
	public void removePlayerBubble(PlayerBubble playerBubble) {
		playerBubbles.remove(playerBubble);
	}

	/**
	 * Adds a fireball to the list of fireballs.
	 *
	 * @param fireBall the fireball to add.
	 */
	public void addFireBall(FireBall fireBall) {
		fireBalls.add(fireBall);
	}

	/**
	 * Removes a fireball from the list of fireballs.
	 *
	 * @param fireBall the fireball to remove.
	 */
	public void removeFireBall(FireBall fireBall) {
		fireBalls.remove(fireBall);
	}

	/**
	 * Adds a bolt to the list of bolts.
	 *
	 * @param bolt the bolt to add.
	 */
	public void addBolt(Bolt bolt) {
		bolts.add(bolt);
	}

	/**
	 * Removes a bolt from the list of bolts.
	 *
	 * @param bolt the bolt to remove.
	 */
	public void removeBolt(Bolt bolt) {
		bolts.remove(bolt);
	}

	/**
	 * Adds water to the list of waters.
	 *
	 * @param water the water to add.
	 */
	public void addWater(Water water) {
		waters.add(water);
	}

	/**
	 * Removes water from the list of waters.
	 *
	 * @param water the water to remove.
	 */
	public void removeWater(Water water) {
		waters.remove(water);
	}

	// Getters

	/**
	 * Returns the list of special bubbles.
	 *
	 * @return the list of special bubbles.
	 */
	public List<Bubble> getBubbles() {
		return specialBubbles;
	}

	/**
	 * Returns the list of player bubbles.
	 *
	 * @return the list of player bubbles.
	 */
	public List<PlayerBubble> getPlayerBubbles() {
		return playerBubbles;
	}

	/**
	 * Returns the list of fireballs.
	 *
	 * @return the list of fireballs.
	 */
	public List<FireBall> getFireBalls() {
		return fireBalls;
	}

	/**
	 * Returns the list of bolts.
	 *
	 * @return the list of bolts.
	 */
	public List<Bolt> getBolts() {
		return bolts;
	}

	/**
	 * Returns the list of water bubbles.
	 *
	 * @return the list of water bubbles.
	 */
	public List<Water> getWaters() {
		return waters;
	}

	// Other Methods

	/**
	 * Updates all bubbles managed by this BubbleManager, including special bubbles,
	 * player bubbles, fireballs, bolts, and water bubbles.
	 */
	  public void updateBubbles() {
	        specialBubbles.forEach(Bubble::updateEntity);
	        playerBubbles.forEach(PlayerBubble::updateEntity);
	        fireBalls.forEach(FireBall::updateEntity);
	        bolts.forEach(Bolt::updateEntity);

	        if (waterUpdateTimer == null && !waters.isEmpty()) {
	            waterUpdateTimer = new Timer("Water Update");
	            waterUpdateTimer.schedule(new TimerTask() {
	                @Override
	                public void run() {
	                    waters.forEach(w -> w.updateEntity());
	                    waterUpdateTimer.cancel();
	                    waterUpdateTimer = null;
	                }
	            }, 50);
	        }

	        if (spawnSpecialBubbleTimer == null) {
	            spawnSpecialBubbleTimer = new Timer("Spawn Special Bubble");
	            long nextBubbleInterval = Model.getInstance().getCurrentLevel().getEnemyManager().isBoss() ? 3000 : 20000;
	            spawnSpecialBubbleTimer.schedule(new TimerTask() {
					
					@Override
					public void run() {
						createSpecialBubble();
						spawnSpecialBubbleTimer.cancel();
	                    spawnSpecialBubbleTimer = null;
					}
				}, nextBubbleInterval);
	        }
	    }
	}