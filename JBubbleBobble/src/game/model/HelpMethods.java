package game.model;

import game.model.entities.MovingEntity;
import utils.LevelLoader;

import static game.model.Settings.GAME_HEIGHT;
import static game.model.Settings.GAME_WIDTH;
import static game.model.Settings.TILE_SIZE;
import static game.model.Settings.SCALE;

public class HelpMethods {

	public static boolean canMoveHere(float x, float y, float width, float height) {
		if (!isSolid(x, y) && !isSolid(x + width, y) && !isSolid(x, y + height) && !isSolid(x + width, y + height))
			return true;
		return false;
	}

	public static boolean isSolid(float x, float y) {
		String[][] lvlData = LevelLoader.getLevelData();
		
		if (x < 0 ||  x >= GAME_WIDTH ) {
//			System.out.println("OUT OF BOUNDS");
			return true;
		}
		if (y < 0 || y >= GAME_HEIGHT) {
//			System.out.println("OUT OF BOUNDS");
			return false;
		}

		float xIndex = x / TILE_SIZE;
		float yIndex = y / TILE_SIZE;

		String value = lvlData[(int) yIndex][(int) xIndex];

		return "#".equals(value.substring(0, 1)); // casi in cui c'è un blocco
	}

	public static boolean isEntityInsideWall(float x, float y, float width, float height) {
		return isSolid(x, y) || isSolid(x + width, y) || isSolid(x, y + height) || isSolid(x + width, y + height);
	}

	public static float getEntityXPosNextToWall(MovingEntity movingEntity) {
	    float x = movingEntity.getX();
	    float y = movingEntity.getY();
	    float width = movingEntity.getWidth();
	    float height = movingEntity.getHeight();
	    float xSpeed = movingEntity.getxSpeed();

	    if (xSpeed > 0) { // Moving right
	        // Stop just before the right side of the entity intersects a solid block
	        int xTilePos = (int) ((x + width) / TILE_SIZE);
	        float xPosNextToWall = xTilePos * TILE_SIZE - width - 0.1f+TILE_SIZE;
	        return xPosNextToWall;
	    } else if (xSpeed < 0) { // Moving left
	        // Stop just before the left side of the entity intersects a solid block
	        int xTilePos = (int) (x / TILE_SIZE);
	        float xPosNextToWall = (xTilePos + 1) * TILE_SIZE + 0.1f-TILE_SIZE;
	        return xPosNextToWall;
	    }

	    return x; // If not moving, return current x position
	}
	

	public static float getEntityPosUnderRoofOrAboveFloor(MovingEntity movingEntity, float airSpeed) {
	    float x = movingEntity.getX();
	    float y = movingEntity.getY();
	    float width = movingEntity.getWidth();
	    float height = movingEntity.getHeight();

	    if (airSpeed < 0) { // Moving upwards, hitting the roof
	        int yTilePos = (int) (y / TILE_SIZE);
	        float yPosUnderRoof = (yTilePos + 1) * TILE_SIZE + 0.1f;
	        return yPosUnderRoof;
	    } else if (airSpeed > 0) { // Moving downwards, landing on the floor
	        int yTilePos = (int) ((y + height) / TILE_SIZE);
	        float yPosAboveFloor = yTilePos * TILE_SIZE - height - 0.1f+TILE_SIZE;
	        return yPosAboveFloor;
	    }

	    return y; // If not moving vertically, return current y position
	}


//	checks if any of the pixels under the entity are solid
	public static boolean isEntityGrounded(MovingEntity movingEntity) {
		return isSolidHorizontalLine(movingEntity.getX(), movingEntity.getX() + movingEntity.getWidth(),
				movingEntity.getY() + movingEntity.getHeight() + 1)
				&& !isSolidHorizontalLine(movingEntity.getX(), movingEntity.getX() + movingEntity.getWidth(),
						movingEntity.getY() + movingEntity.getHeight() - 1);
	}

//	checks if any pixel in the line is solid
	public static boolean isSolidHorizontalLine(float x1, float x2, float y) {
		for (float x = x1; x <= x2; x += 0.1) {
			if (isSolid(x, y))
				return true;
		}
		return false;
	}

//	checks if any pixel in the line is solid
	public static boolean isSolidVerticalLine(float x, float y1, float y2) {
		for (float y = y1; y <= y2; y += 0.1) {
			if (isSolid(x, y))
				return true;
		}
		return false;
	}

}
