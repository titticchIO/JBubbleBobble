package game.model.powerups;

import game.model.Model;

public class PinkCandy extends Powerup{
	public PinkCandy(float x, float y, float width, float height) {
		super(x, y, width, height, "!", Item.PINK_CANDY);
	}

	
	@Override
	public void startEffect() {
		Model.getInstance().getCurrentUser().addPoints(Item.PINK_CANDY.getPoints());
		
	}

	@Override
	public void stopEffect() {
		// TODO Auto-generated method stub
		
	}

}
