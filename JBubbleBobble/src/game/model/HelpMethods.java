package game.model;

import java.util.HashMap;

import game.model.entities.MovingEntity;
import game.model.level.Level;
import game.model.level.LevelLoader;
import game.model.tiles.Tile;



public class HelpMethods {

	public static boolean canMoveHere(float x, float y, float width, float height) {
		if (!isSolid(x, y) && !isSolid(x + width, y) && !isSolid(x, y + height) && !isSolid(x + width, y + height))
			return true;
		return false;
	}

	public static boolean isSolid(float x, float y) {
		String[][] lvlData=Model.getInstance().getCurrentLevel().getLvlData();
		
		if (x < 0 ||  x >= Level.GAME_WIDTH) {
//			System.out.println("OUT OF BOUNDS");
			return true;
		}
		if (y < 0 || y >= Level.GAME_HEIGHT) {
//			System.out.println("OUT OF BOUNDS");
			return false;
		}
		

		float xIndex = x / Tile.TILE_SIZE;
		float yIndex = y / Tile.TILE_SIZE;

		String value = lvlData[(int) yIndex][(int) xIndex];

		return value.matches("^[0-9]");
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
	        int xTilePos = (int) ((x + width) / Tile.TILE_SIZE);
	        float xPosNextToWall = xTilePos * Tile.TILE_SIZE - width - 0.1f+Tile.TILE_SIZE;
	        return xPosNextToWall;
	    } else if (xSpeed < 0) { // Moving left
	        // Stop just before the left side of the entity intersects a solid block
	        int xTilePos = (int) (x / Tile.TILE_SIZE);
	        float xPosNextToWall = (xTilePos + 1) * Tile.TILE_SIZE + 0.1f-Tile.TILE_SIZE;
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
	        int yTilePos = (int) (y / Tile.TILE_SIZE);
	        float yPosUnderRoof = (yTilePos + 1) * Tile.TILE_SIZE + 0.1f;
	        return yPosUnderRoof;
	    } else if (airSpeed > 0) { // Moving downwards, landing on the floor
	        int yTilePos = (int) ((y + height) / Tile.TILE_SIZE);
	        float yPosAboveFloor = yTilePos * Tile.TILE_SIZE - height - 0.1f+Tile.TILE_SIZE;
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
