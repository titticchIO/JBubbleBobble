package game.model.powerups;

import game.model.entities.Entity;

public class Powerup extends Entity{

	public enum Item {
		PINK_CANDY(100, 5000), BLUE_CANDY(100, 5000), YELLOW_CANDY(100, 5000), SHOES(100, 8000), CLOCK(200, 10000),
		ORANGE_PARASOL(200, 0), RED_PARASOL(200, 0), PURPLE_PARASOL(200, 0), CHACKN_HEART(300, 1000),
		CRYSTAL_RING(1000, 5000), AMETHYST_RING(1000, 5000), RUBY_RING(1000, 5000);

		private final int points;
		private float duration;

		private Item(int points, float duration) {
			this.points = points;
			this.duration = duration;
		}
		
		public void setDuration(float duration) {
			this.duration = duration;
		}
		
		public int getPoints() {
			return points;
		}
		
		public float getDuration() {
			return duration;
		}
	}
	
	private Item item;

	public Powerup(float x, float y, float width, float height, String code, Item item) {
		super(x, y, width, height, code);
		this.item = item;
	}

	
	public void updatePowerup () {
		if (item.getDuration() > 0) {
			item.setDuration(item.getDuration()-1);
		} else {
			//powerup.setToDelete
		}
	}
	
	
	
}
