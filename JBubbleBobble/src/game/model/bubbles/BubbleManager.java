package game.model.bubbles;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import game.model.Model;
import game.model.bubbles.special_effects.Bolt;
import game.model.bubbles.special_effects.FireBall;
import game.model.bubbles.special_effects.Water;
import game.model.level.Level;

/**
 * The {@code BubbleManager} class manages the creation, updating, and removal
 * of various types of bubbles in the game, including player bubbles, fireballs,
 * bolts, and water bubbles.
 */
public class BubbleManager {

	// Instance Fields
	private BubbleFactory bubbleFactory; // Factory to create different types of bubbles.
	private List<Bubble> specialBubbles; // List of special bubbles in the game.
	private List<PlayerBubble> playerBubbles; // List of player bubbles in the game.
	private List<FireBall> fireBalls; // List of fireballs in the game.
	private List<Bolt> bolts; // List of bolts in the game.
	private List<Water> waters; // List of water bubbles in the game.
	private Timer spawnSpecialBubbleTimer; // Timer for spawning special bubbles.
	private Timer waterUpdateTimer; // Timer for updating waters.

	// Constructor

	/**
	 * Constructs a new {@code BubbleManager}, initializing the lists for different
	 * types of bubbles and the bubble factory.
	 *
	 * @param bubbleSpawnPoints List of spawn points where bubbles can be created.
	 */
	public BubbleManager(List<Float> bubbleSpawnPoints) {
		bubbleFactory = new BubbleFactory(bubbleSpawnPoints);
		specialBubbles = new CopyOnWriteArrayList<>();
		playerBubbles = new CopyOnWriteArrayList<>();
		fireBalls = new CopyOnWriteArrayList<>();
		waters = new CopyOnWriteArrayList<>();
		bolts = new CopyOnWriteArrayList<>();
	}

	// Methods for Creating Bubbles

	/**
	 * Creates a new player bubble and adds it to the list of player bubbles.
	 *
	 * @param x      the x-coordinate of the bubble.
	 * @param y      the y-coordinate of the bubble.
	 * @param xSpeed the speed of the bubble in the x-direction.
	 */
	public void createPlayerBubble(float x, float y, float xSpeed) {
		PlayerBubble newBubble = bubbleFactory.createPlayerBubble(x, y, xSpeed);
		playerBubbles.add(newBubble);
	}

	/**
	 * Creates a new special bubble using the bubble factory and adds it to the list
	 * of special bubbles.
	 */
	public void createSpecialBubble() {
		Bubble newBubble = bubbleFactory.createSpecialBubble();
		if (newBubble != null)
			specialBubbles.add(newBubble);

	}

	/**
	 * Creates a special bubble from an existing bubble and adds it to the list of
	 * special bubbles.
	 *
	 * @param specialBubble The bubble to be turned into a special bubble.
	 */
	public void createSpecialBubble(Bubble specialBubble) {
		Bubble newBubble = bubbleFactory.createSpecialBubble(specialBubble);
		if (newBubble != null)
			specialBubbles.add(newBubble);
	}

	/**
	 * Creates and spawns an extend bubble when multiple enemies are killed
	 * simultaneously.
	 */
	public void createExtendBubble() {
		if (Level.getSimultaneousKills() > 0) {
			ExtendBubble newExtendBubble = bubbleFactory.createExtendBubble();
			if (newExtendBubble != null)
				specialBubbles.add(newExtendBubble);
			Level.setSimultaneousKills(0);
		}
	}

	// Methods for Managing Bubbles

	/**
	 * Adds a bubble to the list of special bubbles.
	 *
	 * @param bubble the bubble to be added.
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
	 * Updates all bubbles managed by this {@code BubbleManager}, including special
	 * bubbles, player bubbles, fireballs, bolts, and water bubbles. This method
	 * also handles the scheduling of special bubble spawns and water bubble updates
	 * using timers.
	 */
	public void updateBubbles() {
		createExtendBubble();
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
			long nextBubbleInterval = Model.getInstance().getCurrentLevel().getEnemyManager().isBoss() ? 2000 : 10000;
			spawnSpecialBubbleTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					if (Model.getInstance().getCurrentLevel().getEnemyManager().isBoss()) {
						switch (new Random().nextInt(2)) {
						case 0 -> createSpecialBubble(new ThunderBubble());
						case 1 -> createSpecialBubble(new FireBubble());
						}
					} else
						createSpecialBubble();
					spawnSpecialBubbleTimer.cancel();
					spawnSpecialBubbleTimer = null;
				}
			}, nextBubbleInterval);
		}
	}
}