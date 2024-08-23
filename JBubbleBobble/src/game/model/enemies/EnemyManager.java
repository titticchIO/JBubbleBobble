package game.model.enemies;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import game.model.entities.MovingEntity;

/**
 * Manages the collection of enemies and lasers in the game. The
 * {@code EnemyManager} class is responsible for adding, removing, and updating
 * enemies and lasers, as well as providing access to these entities.
 * 
 * <p>
 * This class uses {@link CopyOnWriteArrayList} to store the enemies and lasers,
 * which ensures thread-safe operations when modifying the lists.
 */
public class EnemyManager {
	private List<Enemy> enemies;
	private List<Laser> lasers;

	/**
	 * Constructs an {@code EnemyManager} with empty lists of enemies and lasers.
	 */
	public EnemyManager() {
		enemies = new CopyOnWriteArrayList<>();
		lasers = new CopyOnWriteArrayList<>();
	}

	/**
	 * Adds an enemy to the list of managed enemies.
	 * 
	 * @param enemy the {@link Enemy} to add to the list
	 */
	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}

	/**
	 * Adds a laser to the list of managed lasers.
	 * 
	 * @param laser the {@link Laser} to add to the list
	 */
	public void addLaser(Laser laser) {
		lasers.add(laser);
	}

	/**
	 * Removes an enemy from the list of managed enemies.
	 * 
	 * @param enemy the {@link Enemy} to remove from the list
	 */
	public void removeEnemy(Enemy enemy) {
		enemies.remove(enemy);
	}
	
	public void removeAllEnemies() {
		enemies = new CopyOnWriteArrayList<>();
	}

	/**
	 * Removes a laser from the list of managed lasers.
	 * 
	 * @param laser the {@link Laser} to remove from the list
	 */
	public void removeLaser(Laser laser) {
		lasers.remove(laser);
	}

	/**
	 * Returns the list of currently managed enemies.
	 * 
	 * @return a {@link List} of {@link Enemy} objects
	 */
	public List<Enemy> getEnemies() {
		return enemies;
	}

	/**
	 * Returns the list of currently managed lasers.
	 * 
	 * @return a {@link List} of {@link Laser} objects
	 */
	public List<Laser> getLasers() {
		return lasers;
	}

	/**
	 * Returns a combined list of all enemies and lasers, treating them as hazards.
	 * 
	 * <p>
	 * The returned list contains all entities that pose a danger to the player,
	 * combining both enemies and lasers into a single list.
	 * 
	 * @return a {@link List} of {@link MovingEntity} objects representing all
	 *         hazards
	 */
	public List<MovingEntity> getHazards() {
		List<MovingEntity> hazards = new ArrayList<MovingEntity>();
		hazards.addAll(enemies);
		hazards.addAll(lasers);
		return hazards;
	}

	/**
	 * Updates all managed enemies and lasers by invoking their respective update
	 * methods.
	 * 
	 * <p>
	 * This method should be called regularly, such as once per game frame, to
	 * ensure that the state of all enemies and lasers is kept current.
	 */
	public void updateEnemies() {
		enemies.stream().forEach(Enemy::updateEntity);
		lasers.stream().forEach(Laser::updateEntity);
	}
}
